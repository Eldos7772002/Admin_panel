package kz.postkz.AdminSecurity.dto;

import lombok.Data;

@Data
public class SigninResponse {
    private int statusCode;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String expirationTime;
    private String error;
}
