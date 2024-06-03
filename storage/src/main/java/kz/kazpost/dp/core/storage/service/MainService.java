package kz.kazpost.dp.core.storage.service;

import kz.kazpost.dp.core.storage.dto.FileDownloadResponse;
import kz.kazpost.dp.core.storage.dto.FileMetadataDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MainService {
    FileMetadataDTO fileInfo(String fileId);
    FileMetadataDTO downloadBase64File(String fileId);
    FileDownloadResponse downloadFile(String fileId);
    FileMetadataDTO uploadFile(MultipartFile file, String code);
}
