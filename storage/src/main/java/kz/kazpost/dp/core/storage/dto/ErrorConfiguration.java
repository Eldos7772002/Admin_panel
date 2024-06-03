package kz.kazpost.dp.core.storage.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorConfiguration {
    private final String errorMessage;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
