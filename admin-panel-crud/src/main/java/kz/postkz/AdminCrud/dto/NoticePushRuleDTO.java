package kz.postkz.AdminCrud.dto;

import lombok.Data;

@Data
public class NoticePushRuleDTO {

    private String code;
    private int priority;
    private String name;
    private String description;
    private boolean smsOnly;
    private boolean smsOn;
    private Integer count;
    private int periodic;
}



