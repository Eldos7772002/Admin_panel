package kz.kazpost.dp.core.storage.exceptions;

import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;

@Getter
public class FileDownloadException extends RuntimeException {
    private final FileMetadataErrorCode code = FileMetadataErrorCode.DOWNLOAD_ERROR;

}
