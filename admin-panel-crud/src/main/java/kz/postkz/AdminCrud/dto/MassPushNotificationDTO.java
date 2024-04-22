package kz.postkz.AdminCrud.dto;

import lombok.Data;

@Data
public class MassPushNotificationDTO {
    private String description;
    private String message;
    private String title;
    private String rule_code;
}
