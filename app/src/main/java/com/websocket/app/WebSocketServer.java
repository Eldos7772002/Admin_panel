package com.websocket.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.app.Feign.FirebaseClient;
import com.websocket.app.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketServer extends TextWebSocketHandler {
    private final RedisService redisService;
    private final FirebaseClient firebaseClient;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Соединение установлено: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message from client: {}", message.getPayload());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataMap = objectMapper.readValue(message.getPayload(), Map.class);

        log.info("Parsed data: {}", dataMap);

        String expoid = (String) dataMap.get("expoid");
        double latitude = (Double) dataMap.get("latitude");
        double longitude = (Double) dataMap.get("longitude");

        log.info("Expo ID: {}", expoid);
        log.info("Latitude: {}", latitude);
        log.info("Longitude: {}", longitude);

        // Проверяем, находится ли пользователь в торговом центре
        Map<String, String> isInMall = redisService.findMallByCoordinates(latitude, longitude);
        log.info("Is user in mall: {}", isInMall);
        if (isInMall != null && !redisService.isNotificationSent(expoid, "mall")) {
            String mallName = isInMall.get("name");
            String defaultNotification = isInMall.get("defaultNotification");

            Map<String, String> notificationData = new HashMap<>();
            notificationData.put("to", expoid);
            notificationData.put("title", "Добро пожаловать в " + mallName);
            notificationData.put("body", defaultNotification);
            notificationData.put("info", "Дополнительная информация");

            firebaseClient.sendNotification(notificationData);
            redisService.setNotificationSent(expoid, "mall", 14400); // Устанавливаем таймер на 4 часа (14400 секунд)
            log.info("Notification sent successfully for mall");
        } else {
            log.info("Notification for mall not sent, user already notified within the last 4 hours or not in a mall");
        }

        // Проверяем, находится ли пользователь в бутике
        Map<String, String> isInBoutique = redisService.findBoutiqueByCoordinates(latitude, longitude);
        log.info("Is user in boutique: {}", isInBoutique);
        if (isInBoutique != null && !redisService.isNotificationSent(expoid, "boutique")) {
            String boutiqueName = isInBoutique.get("name");
            String defaultNotification = isInBoutique.get("defaultNotification");

            Map<String, String> notificationData = new HashMap<>();
            notificationData.put("to", expoid);
            notificationData.put("title", "Добро пожаловать в бутик " + boutiqueName);
            notificationData.put("body", defaultNotification);
            notificationData.put("info", "Дополнительная информация");

            firebaseClient.sendNotification(notificationData);
            redisService.setNotificationSent(expoid, "boutique", 3600); // Устанавливаем таймер на 1 час (3600 секунд)
            log.info("Notification sent successfully for boutique");
        } else {
            log.info("Notification for boutique not sent, user already notified within the last hour or not in a boutique");
        }
    }




    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Соединение закрыто: {}", session.getId());
    }
}
