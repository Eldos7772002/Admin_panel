package com.websocket.app.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "telegramClient", url = "http://172.30.110.8:1991/newbot/notifier/api")
public interface TelegramClient {

    @PostMapping(value = "/send",consumes = APPLICATION_FORM_URLENCODED_VALUE)
    String sendMessage(Map<String, ?> form
    );

}
