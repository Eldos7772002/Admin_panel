package kz.kazpost.dp.core.storage.exceptions;


import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;


@Getter
public class FileMetadataException extends RuntimeException {
    private final FileMetadataErrorCode code;

    public FileMetadataException(FileMetadataErrorCode code) {
        this.code = code;
    }
}

