package kazpost.notification.service.impl;

import feign.FeignException;
import kazpost.notification.Feign.FirebaseClient;
import kazpost.notification.dto.NotificationDTO;
import kazpost.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final FirebaseClient firebaseClient;

    @Override
    public Map<String, Object> processUserData(Map<String, String> userData) {
        try {
            String mobileNumber = userData.get("mobileNumber") != null ? userData.get("mobileNumber") : "";

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setTo(userData.get("to"));
            notificationDTO.setTitle(userData.get("title"));
            notificationDTO.setBody(userData.get("body"));
            // Установка данных в соответствии с вашим требованием
            Map<String, String> data = new HashMap<>();
            data.put("info", userData.get("info"));
            notificationDTO.setData(data);

            boolean rez = sendNotification(notificationDTO);
        } catch (Exception _) {
            // Обработка исключения
        }
        return null;
    }

    private boolean sendNotification(NotificationDTO notificationDTO) {
        try {
            ResponseEntity<String> response = firebaseClient.sendSms(notificationDTO);
            HttpStatus statusCode = (HttpStatus) response.getStatusCode();
            log.info(response.getBody()); // Записать ответ в журнал

            if (statusCode.is2xxSuccessful()) {
                log.info(response.getBody()); // Записать ответ в журнал
            } else {
                log.error("Failed to send SMS. Status code: {}", statusCode);
            }

            return statusCode.is2xxSuccessful();
        }catch (FeignException e){
            log.error("Failed to send SMS. Status code: {}", e.getMessage());
            return false;
        }
    }
}
