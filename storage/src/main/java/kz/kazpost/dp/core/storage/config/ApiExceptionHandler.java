package kz.kazpost.dp.core.storage.config;

import io.minio.errors.ErrorResponseException;
import kz.kazpost.dp.core.storage.constanta.ErrorMessages;
import kz.kazpost.dp.core.storage.domain.enums.FileMetadataErrorCode;
import kz.kazpost.dp.core.storage.dto.ErrorConfiguration;
import kz.kazpost.dp.core.storage.dto.ErrorResponse;
import kz.kazpost.dp.core.storage.exceptions.ContentTypeException;
import kz.kazpost.dp.core.storage.exceptions.FileDownloadBase64Exception;
import kz.kazpost.dp.core.storage.exceptions.FileDownloadException;
import kz.kazpost.dp.core.storage.exceptions.InvalidFileFormatException;
import kz.kazpost.dp.core.storage.service.TranslatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler {

    private final TranslatorService trService;


    private static final Map<String, ErrorConfiguration> errorConfigurations = new HashMap<>();

    static {
        errorConfigurations.put(ErrorMessages.OBJECT_NOT_EXIST, new ErrorConfiguration(
                FileMetadataErrorCode.OBJECT_NOT_FOUND.getDescription(),
                FileMetadataErrorCode.OBJECT_NOT_FOUND.name(),
                HttpStatus.NOT_FOUND));

        errorConfigurations.put(ErrorMessages.SIGNATURE_MISMATCH, new ErrorConfiguration(
                ErrorMessages.SIGNATURE_MISMATCH,
                FileMetadataErrorCode.SIGNATURE_ERROR.name(),
                HttpStatus.UNAUTHORIZED));

        errorConfigurations.put(ErrorMessages.MAX_UPLOAD_SIZE_EXCEEDED, new ErrorConfiguration(
                ErrorMessages.MAX_UPLOAD_SIZE_EXCEEDED,
                FileMetadataErrorCode.UPLOAD_SIZE_EXCEEDED.name(),
                HttpStatus.PAYLOAD_TOO_LARGE));

        errorConfigurations.put(ErrorMessages.CONTENT_TYPE_NULL, new ErrorConfiguration(
                ErrorMessages.CONTENT_TYPE_NULL,
                FileMetadataErrorCode.CONTENT_TYPE_MISSING.name(),
                HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<?> handleErrorResponseException(ErrorResponseException ex) {
        log.error("ErrorResponseException caught", ex);
        for (Map.Entry<String, ErrorConfiguration> entry : errorConfigurations.entrySet()) {
            if (ex.getMessage().contains(entry.getKey())) {
                ErrorConfiguration config = entry.getValue();
                return renderError(config.getErrorMessage(), config.getErrorCode(), config.getHttpStatus());
            }
        }
        return renderError(ErrorMessages.UNEXPECTED_ERROR, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        log.error("Exception caught", ex);
        return renderError(ex.getMessage(), "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ContentTypeException.class)
    public ResponseEntity<?> handleContentTypeException(ContentTypeException ex) {
        log.error("Content type exception caught", ex);
        return renderError(
                ex.getMessage(),
                ex.getCode().name(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<?> handleInvalidFileFormatException(InvalidFileFormatException ex) {
        log.error("Invalid file format exception caught", ex);
        return renderError(
                ex.getCode().getDescription(),
                ex.getCode().name(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<?> handleFileDownloadException(FileDownloadException ex) {
        log.error("FileDownloadException caught", ex);
        return renderError(
                ex.getCode().getDescription(),
                ex.getCode().name(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(FileDownloadBase64Exception.class)
    public ResponseEntity<?> handleFileDownloadBase64Exception(FileDownloadBase64Exception ex) {
        log.error("FileDownloadBase64Exception caught", ex);
        return renderError(
                ex.getCode().getDescription(),
                ex.getCode().name(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<ErrorResponse> renderError(String msg, String errorCode, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode);
        errorResponse.setMessage(trService.getMessage(msg));
        return new ResponseEntity<>(errorResponse, status);
    }
}
