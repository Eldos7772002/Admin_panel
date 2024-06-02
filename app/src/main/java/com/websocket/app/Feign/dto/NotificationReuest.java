package com.websocket.app.Feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReuest {
    private String to;
    private String title;
    private String body;
    private Map<String, String> data;
}
