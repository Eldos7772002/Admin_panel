package kz.kazpost.dp.core.storage.adapters.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kz.kazpost.dp.core.storage.dto.FileDownloadResponse;
import kz.kazpost.dp.core.storage.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class MainController {

    private MainService mainService;

    /**
     * Uploads a file with additional code information.
     *
     * @param file The file to be uploaded, expected to be in specific formats (.pdf, .jpg, .jpeg, .png).
     * @param code An additional string parameter that may be used for categorization or identification.
     * @return ResponseEntity indicating the outcome of the upload operation.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("code") String code) {

        return ResponseEntity.ok(mainService.uploadFile(file, code));
    }
    /**
     * Downloads a file based on a provided fileId.
     *
     * @param fileId The unique identifier of the file to be downloaded.
     * @return ResponseEntity with the file content, content type, and a suggested file name for the download.
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
        FileDownloadResponse response = mainService.downloadFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + response.getFileName() + "\"")
                .body(response.getFileContent());
    }

    /**
     * Downloads a file in Base64 encoding based on a provided fileId.
     *
     * @param fileId The unique identifier of the file to be downloaded.
     * @return ResponseEntity with the Base64 encoded file content.
     */
    @GetMapping("/download-base64/{fileId}")
    public ResponseEntity<?> downloadFileBase64(@PathVariable String fileId) {
        return ResponseEntity.ok(mainService.downloadBase64File(fileId));
    }

    /**
     * Retrieves metadata of a file based on a provided fileId.
     *
     * @param fileId The unique identifier of the file whose metadata is to be retrieved.
     * @return ResponseEntity with the file's metadata.
     */
    @GetMapping(value = "/metadata/{fileId}")
    public ResponseEntity<?> fileInfo(@Parameter(description = "Наименование файла для скачивания")
                                      @PathVariable String fileId) {
        return ResponseEntity.ok(mainService.fileInfo(fileId));
    }
}

