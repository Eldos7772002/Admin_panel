package com.websocket.app.service;

import com.websocket.app.entity.Mall;
import com.websocket.app.entity.Outlet;
import com.websocket.app.repository.MallRepository;
import com.websocket.app.repository.OutletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DataService {

    private final MallRepository mallRepository;
    private final OutletRepository outletRepository;

    public DataService(MallRepository mallRepository, OutletRepository outletRepository) {
        this.mallRepository = mallRepository;
        this.outletRepository = outletRepository;
    }

    @Scheduled(fixedRate = 1000000)
    public void updateData() {
        int redisPort = 6379;
        String redisHost = "redis";
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            List<Mall> mallResults = mallRepository.findAll();
            List<Outlet> outletResults = outletRepository.findAll();

            log.info("Starting data update operation...");

            // Обновление данных торговых центров
            for (Mall mall : mallResults) {
                String key = "mall:" + mall.getId();
                Map<String, String> mallData = new HashMap<>();
                mallData.put("name", mall.getName());
                mallData.put("latitude1", String.valueOf(mall.getLatitude1()));
                mallData.put("longitude1", String.valueOf(mall.getLongitude1()));
                mallData.put("latitude2", String.valueOf(mall.getLatitude2()));
                mallData.put("longitude2", String.valueOf(mall.getLongitude2()));
                mallData.put("defaultNotification", String.valueOf(mall.getDefaultNotification()));
                mallData.put("imageUrl", String.valueOf(mall.getImageUrl()));
                jedis.hset(key, mallData);
                int ttl = 3600; // Время жизни в секундах (1 час)
                jedis.expire(key, ttl); // Установить TTL

                log.info("Saved mall data with key: {}", key);
            }

            // Обновление данных бутиков
            for (Outlet outlet : outletResults) {
                String key = "boutique:" + outlet.getId();
                Map<String, String> outletData = new HashMap<>();
                outletData.put("name", outlet.getName());
                outletData.put("latitude1", String.valueOf(outlet.getLatitude1()));
                outletData.put("longitude1", String.valueOf(outlet.getLongitude1()));
                outletData.put("latitude2", String.valueOf(outlet.getLatitude2()));
                outletData.put("longitude2", String.valueOf(outlet.getLongitude2()));
                outletData.put("defaultNotification", String.valueOf(outlet.getDefaultNotification()));
                outletData.put("imageUrl", String.valueOf(outlet.getImageUrl()));
                jedis.hset(key, outletData);
                int ttl = 3600; // Время жизни в секундах (1 час)
                jedis.expire(key, ttl); // Установить TTL

                log.info("Saved outlet data with key: {}", key);
            }

            log.info("Data update operation completed.");
        } catch (Exception e) {
            log.error("Error during data update operation", e);
        }
    }
}
