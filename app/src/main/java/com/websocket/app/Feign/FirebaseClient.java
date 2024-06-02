package com.websocket.app.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "firebase-client", url = "${feign.url}")
public interface FirebaseClient {
    @PostMapping(value = "/sendNotification")
    ResponseEntity<String> sendNotification(@RequestBody Map<String,String> notificationDTO);

}
