package kazpost.notification.controller;

import kazpost.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3")
public class MainController {
    private final NotificationService notificationService;
    @PostMapping("/sendNotification")
    public ResponseEntity<?> getNotificationData(@RequestBody Map<String,String> data){
        Map<String,Object> rez=notificationService.processUserData(data);
        return (ResponseEntity<?>) rez;
    }

}
