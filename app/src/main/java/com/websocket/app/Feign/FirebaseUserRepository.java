package com.websocket.app.Feign;

import com.websocket.app.entity.FirebaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirebaseUserRepository extends JpaRepository<FirebaseUser, Long> {
}
