package kazpost.notification.service;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    Map<String, Object> processUserData(Map<String, String> data);
}
