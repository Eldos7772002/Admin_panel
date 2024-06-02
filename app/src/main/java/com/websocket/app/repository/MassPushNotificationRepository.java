package com.websocket.app.repository;

import com.websocket.app.entity.MassPushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassPushNotificationRepository extends JpaRepository<MassPushNotification, Integer> {
}
