package kz.kazpost.dp.core.storage.service.impl;

import io.minio.*;
import kz.kazpost.dp.core.storage.domain.model.BucketConfig;
import kz.kazpost.dp.core.storage.domain.repository.BucketConfigRepository;
import kz.kazpost.dp.core.storage.dto.FileDownloadResponse;
import kz.kazpost.dp.core.storage.dto.FileMetadataDTO;
import kz.kazpost.dp.core.storage.exceptions.FileDownloadBase64Exception;
import kz.kazpost.dp.core.storage.exceptions.FileDownloadException;
import kz.kazpost.dp.core.storage.exceptions.InvalidFileFormatException;
import kz.kazpost.dp.core.storage.service.MainService;
import kz.kazpost.dp.core.storage.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static kz.kazpost.dp.core.storage.utils.Utils.SUCCESS;
import static kz.kazpost.dp.core.storage.utils.Utils.UPLOADED;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {

    private MinioClient minioClient;

    private final BucketConfigRepository bucketConfigRepository;

    @Value("${minio.bucket}")
    private String BUCKET;
    @Value("${api.download}")
    private String download;
    @Value("${api.download-base64}")
    private String downloadBase;

    /**
     * Configures the MinioClient with credentials and endpoint from application properties.
     */
    @Autowired
    public void StorageService(@Value("${minio.link}") String minio,
                               @Value("${minio.accessKey}") String accessKey,
                               @Value("${minio.secretKey}") String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(minio)
                .credentials(accessKey, secretKey)
                .httpClient(Utils.getUnsafeOkHttpClient())
                .build();
    }

    /**
     * Retrieves metadata for a file stored in MinIO.
     *
     * @param fileName Name of the file whose metadata is being retrieved.
     * @return FileMetadataDTO containing the metadata of the requested file.
     */
    @SneakyThrows
    public FileMetadataDTO fileInfo(String fileName) {
        log.info("Retrieving file metadata for fileName: {}", fileName);
        var stat = minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET).object(fileName).build());
        var userMetadata = stat.userMetadata();
        var originalFilename = userMetadata.get("original-filename");
        var code = userMetadata.get("code");

        var metadata = new FileMetadataDTO();
        metadata.setCode(code);
        metadata.setFileId(fileName);
        metadata.setFilename(originalFilename);
        metadata.setSize(stat.size());
        metadata.setContentType(stat.contentType());
        metadata.setDownloadUrl(download + fileName);
        metadata.setDownloadBase64Url(downloadBase + fileName);
        var timeZone = ZoneId.of("Asia/Almaty");
        metadata.setLastModified(stat.lastModified().toInstant().atZone(timeZone).toLocalDateTime());

        log.info("File metadata retrieved successfully: {}", metadata);
        return metadata;
    }

    /**
     * Downloads a file as a Base64 encoded string.
     *
     * @param fileId The unique identifier for the file to download.
     * @return FileMetadataDTO containing the Base64 encoded file.
     */
    @SneakyThrows
    public FileMetadataDTO downloadBase64File(String fileId) {
        log.info("downloadFile : " + fileId);
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET).object(fileId).build())) {
            byte[] fileData = readStreamToByteArray(stream);
            String base64Data = Base64.getEncoder().encodeToString(fileData);

            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET).object(fileId).build());
            Map<String, String> userMetadata = stat.userMetadata();

            FileMetadataDTO fileMetadataDTO = new FileMetadataDTO();
            fileMetadataDTO.setResult("SUCCESS");
            fileMetadataDTO.setMessage("DOWNLOADED");
            fileMetadataDTO.setFilename(userMetadata.get("original-filename"));
            fileMetadataDTO.setSize(fileData.length);
            fileMetadataDTO.setData(base64Data);
            log.info("File downloaded successfully");

            return fileMetadataDTO;
        } catch (Exception e) {
            log.error("Failed to download file as base64: {}", fileId, e);
            throw new FileDownloadBase64Exception();
        }
    }

    /**
     * Downloads a file from MinIO.
     *
     * @param fileId The unique identifier for the file to download.
     * @return FileDownloadResponse containing the file's data, content type, and filename.
     */
    @SneakyThrows
    public FileDownloadResponse downloadFile(String fileId) {
        log.info("Attempting to download file: {}", fileId);
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(BUCKET).object(fileId).build())) {
            byte[] fileData = readStreamToByteArray(stream);
            String mimeType = minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET).object(fileId).build()).contentType();

            return new FileDownloadResponse(fileData, mimeType, fileId);
        } catch (Exception e) {
            log.error("Failed to download file: {}", fileId, e);
            throw new FileDownloadException();
        }
    }

    /**
     * Uploads a file to MinIO.
     *
     * @param file The MultipartFile to upload.
     * @param code A code indicating some categorization or property for the file.
     * @return FileMetadataDTO containing metadata about the uploaded file.
     */
    @SneakyThrows
    public FileMetadataDTO uploadFile(MultipartFile file, String code) {
        String originalFilename = file.getOriginalFilename();
        log.info("Starting file upload: {}", originalFilename);

        if (!isValidExtension(originalFilename)) {
            throw new InvalidFileFormatException();
        }

        try (InputStream inputStream = file.getInputStream()) {
            BucketConfig bucketConfig = bucketConfigRepository.findByCode(code);
            String bucket = bucketConfig.getValue();
            String fileName = String.valueOf(generateUniqueFileName());
            log.info("File passed validation. Filename for upload: {}", fileName);
            minioClient.putObject(buildPutObjectArgs(fileName, inputStream, file, code, bucket));
            log.info("File {} successfully uploaded to Minio", fileName);

            return getFileMetadata(file, code, fileName, originalFilename);
        }
    }

    // Additional private helper methods (e.g., readStreamToByteArray, isValidExtension, generateUniqueFileName, buildPutObjectArgs, getFileMetadata)
    // would be defined here to support the above functionalities.

    private boolean isValidExtension(String filename) {
        if (filename == null) return false;
        String extension = FilenameUtils.getExtension(filename).toLowerCase();
        return Arrays.asList("pdf", "jpg", "jpeg", "png").contains(extension);
    }

    @NotNull
    private FileMetadataDTO getFileMetadata(MultipartFile file, String code, String fileName, String originalFilename) {
        FileMetadataDTO metadata = new FileMetadataDTO();
        metadata.setResult(SUCCESS);
        metadata.setMessage(UPLOADED);
        metadata.setCode(code);
        metadata.setFileId(fileName);
        metadata.setFilename(originalFilename);
        metadata.setSize(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setDownloadUrl(download + fileName);
        metadata.setDownloadBase64Url(downloadBase + fileName);
        metadata.setLastModified(LocalDateTime.now());
        return metadata;
    }


    private UUID generateUniqueFileName() {
        return UUID.randomUUID();
    }

    private PutObjectArgs buildPutObjectArgs(String fileName, InputStream inputStream, MultipartFile file, String code, String Bucket) {
        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("original-filename", file.getOriginalFilename());
        userMetadata.put("code", code);

        return PutObjectArgs.builder()
                .bucket(Bucket)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .userMetadata(userMetadata)
                .build();
    }

    @SneakyThrows
    private byte[] readStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

}
