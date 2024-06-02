package com.websocket.app.repository;


import com.websocket.app.entity.NoticePushRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticePushRuleRepository extends JpaRepository<NoticePushRule, Long> {
}
