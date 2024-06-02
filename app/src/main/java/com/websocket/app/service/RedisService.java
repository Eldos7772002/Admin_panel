package com.websocket.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RedisService {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    public Jedis getJedis() {
        if (redisHost == null || redisHost.isEmpty() || redisPort == 0) {
            log.error("Redis host or port is not set correctly: {}:{}", redisHost, redisPort);
            throw new JedisConnectionException("Redis host or port is not set correctly");
        }
        try {
            Jedis jedis = new Jedis(redisHost, redisPort);
            log.info("Successfully connected to Redis at {}:{}", redisHost, redisPort);
            return jedis;
        } catch (JedisConnectionException e) {
            log.error("Failed to connect to Redis at {}:{}", redisHost, redisPort, e);
            throw e;
        }
    }

    public Map<String, String> getData(String key) {
        try (Jedis jedis = getJedis()) {
            log.info("Fetching data for key: {}", key);
            Map<String, String> data = jedis.hgetAll(key);
            log.info("Data fetched: {}", data);
            return data;
        }
    }

    public Map<String, Map<String, String>> getAllMallData() {
        try (Jedis jedis = getJedis()) {
            Set<String> keys = jedis.keys("mall:*");
            Map<String, Map<String, String>> allMallData = new HashMap<>();

            for (String key : keys) {
                Map<String, String> data = jedis.hgetAll(key);
                allMallData.put(key, data);
            }

            log.info("Fetched all mall data: {}", allMallData);
            return allMallData;
        }
    }

    public Map<String, Map<String, String>> getAllBoutiqueData() {
        try (Jedis jedis = getJedis()) {
            Set<String> keys = jedis.keys("boutique:*");
            Map<String, Map<String, String>> allBoutiqueData = new HashMap<>();

            for (String key : keys) {
                Map<String, String> data = jedis.hgetAll(key);
                allBoutiqueData.put(key, data);
            }

            log.info("Fetched all boutique data: {}", allBoutiqueData);
            return allBoutiqueData;
        }
    }

    public void setData(String key, Map<String, String> data, int ttl) {
        try (Jedis jedis = getJedis()) {
            jedis.hset(key, data);
            jedis.expire(key, ttl);
        }
    }

    public boolean isUserInMall(double userLatitude, double userLongitude, String mallId) {
        String key = "mall:" + mallId;
        Map<String, String> mallData = getData(key);

        if (mallData == null || mallData.isEmpty()) {
            return false;
        }

        double latitude1 = Double.parseDouble(mallData.get("latitude1"));
        double longitude1 = Double.parseDouble(mallData.get("longitude1"));
        double latitude2 = Double.parseDouble(mallData.get("latitude2"));
        double longitude2 = Double.parseDouble(mallData.get("longitude2"));

        return (userLatitude >= Math.min(latitude1, latitude2) &&
                userLatitude <= Math.max(latitude1, latitude2) &&
                userLongitude >= Math.min(longitude1, longitude2) &&
                userLongitude <= Math.max(longitude1, longitude2));
    }

    public Map<String, String> findMallByCoordinates(double userLatitude, double userLongitude) {
        Map<String, Map<String, String>> allMallData = getAllMallData();

        for (Map.Entry<String, Map<String, String>> entry : allMallData.entrySet()) {
            Map<String, String> mallData = entry.getValue();
            double latitude1 = Double.parseDouble(mallData.get("latitude1"));
            double longitude1 = Double.parseDouble(mallData.get("longitude1"));
            double latitude2 = Double.parseDouble(mallData.get("latitude2"));
            double longitude2 = Double.parseDouble(mallData.get("longitude2"));

            if (userLatitude >= Math.min(latitude1, latitude2) &&
                    userLatitude <= Math.max(latitude1, latitude2) &&
                    userLongitude >= Math.min(longitude1, longitude2) &&
                    userLongitude <= Math.max(longitude1, longitude2)) {
                return mallData;
            }
        }
        return null;
    }

    public Map<String, String> findBoutiqueByCoordinates(double userLatitude, double userLongitude) {
        Map<String, Map<String, String>> allBoutiqueData = getAllBoutiqueData();

        for (Map.Entry<String, Map<String, String>> entry : allBoutiqueData.entrySet()) {
            Map<String, String> boutiqueData = entry.getValue();
            double latitude1 = Double.parseDouble(boutiqueData.get("latitude1"));
            double longitude1 = Double.parseDouble(boutiqueData.get("longitude1"));
            double latitude2 = Double.parseDouble(boutiqueData.get("latitude2"));
            double longitude2 = Double.parseDouble(boutiqueData.get("longitude2"));

            if (userLatitude >= Math.min(latitude1, latitude2) &&
                    userLatitude <= Math.max(latitude1, latitude2) &&
                    userLongitude >= Math.min(longitude1, longitude2) &&
                    userLongitude <= Math.max(longitude1, longitude2)) {
                return boutiqueData;
            }
        }
        return null;
    }

    public void setNotificationSent(String expoid, String placeType, int ttl) {
        try (Jedis jedis = getJedis()) {
            String key = "notification_sent:" + expoid + ":" + placeType;
            jedis.setex(key, ttl, "true");
        }
    }

    public boolean isNotificationSent(String expoid, String placeType) {
        try (Jedis jedis = getJedis()) {
            String key = "notification_sent:" + expoid + ":" + placeType;
            return jedis.exists(key);
        }
    }
}
