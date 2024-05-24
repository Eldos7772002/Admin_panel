package kz.postkz.AdminSecurity.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String deviceToken;
    private String role;
}
