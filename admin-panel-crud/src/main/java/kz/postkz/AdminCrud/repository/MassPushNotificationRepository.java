package kz.postkz.AdminCrud.repository;

import kz.postkz.AdminCrud.entity.MassPushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassPushNotificationRepository extends JpaRepository<MassPushNotification, Integer> {
}
