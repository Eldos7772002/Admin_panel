package com.websocket.app.repository;

import com.websocket.app.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    // Здесь можно добавить дополнительные методы для поиска, если необходимо
}
