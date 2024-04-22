package kz.postkz.AdminSecurity.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String accessToken;
}
