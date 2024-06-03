package kz.kazpost.dp.core.storage.exceptions;

import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import lombok.Getter;

@Getter
public class ObjectNotFoundException extends FileMetadataException {
    public ObjectNotFoundException(FileMetadataErrorCode code) {
        super(code);
    }
}
