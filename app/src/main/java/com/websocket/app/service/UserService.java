package com.websocket.app.service;

import com.websocket.app.entity.Balance;
import com.websocket.app.entity.FirebaseUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<FirebaseUser> findAllUsers();
    Optional<FirebaseUser> findUserById(Long id);
    FirebaseUser saveUser(FirebaseUser user);
    void deleteUser(Long id);

    List<Balance> findAllBalances();
    Optional<Balance> findBalanceById(Long id);
    Balance saveBalance(Balance balance);
    void deleteBalance(Long id);
}
