package kz.kazpost.dp.core.storage.exceptions;

import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;

@Getter
public class ContentTypeException extends RuntimeException {
    private final FileMetadataErrorCode code = FileMetadataErrorCode.CONTENT_TYPE_MISSING;
}