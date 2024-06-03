package com.websocket.app.controller;

import com.websocket.app.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v2")
public class MainController{
    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public Map<String, String> getMallData(@RequestParam Long id) {
        String key = "mall:" + id;
        log.info("Requesting data for mall id: {}", id);
        Map<String, String> data = redisService.getData(key);
        log.info("Data returned: {}", data);
        return data;
    }

    @GetMapping("/all")
    public Map<String, Map<String, String>> getAllMallData() {
        return redisService.getAllMallData();
    }

    @GetMapping("/check")
    public boolean isUserInMall(@RequestParam double latitude, @RequestParam double longitude, @RequestParam Long mallId) {
        boolean isInMall = redisService.isUserInMall(latitude, longitude, mallId.toString());
        log.info("User with coordinates ({}, {}) is in mall {}: {}", latitude, longitude, mallId, isInMall);
        return isInMall;
    }
}
