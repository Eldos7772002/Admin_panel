package kz.postkz.AdminSecurity.dto;

import kz.postkz.AdminSecurity.entity.OurUsers;
import lombok.Data;

@Data
public class SignupResponse {
    private Integer statusCode;
    private String message;
    private OurUsers ourUsers;
    private String error;

}
