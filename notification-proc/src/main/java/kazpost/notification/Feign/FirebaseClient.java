package kazpost.notification.Feign;

import kazpost.notification.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "firebase-client", url = "${feign.url}")
public interface FirebaseClient {
    @PostMapping(value = "/send")
    ResponseEntity<String> sendSms(@RequestBody NotificationDTO notificationDTO);

}
