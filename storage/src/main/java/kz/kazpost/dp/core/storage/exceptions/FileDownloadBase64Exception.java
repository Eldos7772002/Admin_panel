package kz.kazpost.dp.core.storage.exceptions;

import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;

@Getter
public class FileDownloadBase64Exception extends RuntimeException {
    private final FileMetadataErrorCode code = FileMetadataErrorCode.BASE64_DOWNLOAD_ERROR;

}

