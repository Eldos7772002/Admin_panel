package kz.kazpost.dp.core.storage.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FileMetadataErrorCode {
    METADATA_RETRIEVE_FAILED("metadata.retrieve.failed"),
    METADATA_NOT_FOUND("error.file.metadataNotFound"),
    OBJECT_NOT_FOUND("error.minio.objectNotFound"),
    INVALID_EXTENSION("error.file.invalidExtension"),
    SIGNATURE_ERROR("error.signature.invalid"),
    UPLOAD_SIZE_EXCEEDED("error.file.sizeExceeded"),
    CONTENT_TYPE_MISSING("error.contentType.missing"),
    ILLEGAL_ARGUMENT("error.illegal.argument"),
    DOWNLOAD_ERROR("error.downloading.file"),
    BASE64_DOWNLOAD_ERROR("error.file.base64DownloadError");

    private final String description;
}
