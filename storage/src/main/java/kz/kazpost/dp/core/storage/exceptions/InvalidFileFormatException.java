package kz.kazpost.dp.core.storage.exceptions;

import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;

@Getter
public class InvalidFileFormatException extends RuntimeException {
    private final FileMetadataErrorCode code = FileMetadataErrorCode.INVALID_EXTENSION;
}
