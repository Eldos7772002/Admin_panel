package kz.kazpost.dp.core.storage.dto;


import kz.kazpost.dp.core.storage.domain.enums.Result;
import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private Result result = Result.ERROR;
    private String message;

}

