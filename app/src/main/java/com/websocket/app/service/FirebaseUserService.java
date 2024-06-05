package com.websocket.app.service;

import com.websocket.app.entity.Balance;
import com.websocket.app.entity.FirebaseUser;

import java.util.List;
import java.util.Optional;

public interface FirebaseUserService {
   FirebaseUser saveFirebaseUser(FirebaseUser feignUser);
    List<FirebaseUser> getAllFirebaseUsers();
    Optional<FirebaseUser> getFirebaseUserById(Long userid);
    Optional<FirebaseUser> getFirebaseUserByEmail(String email);
    Optional<FirebaseUser> getFirebaseUserByExpoToken(String expoToken);
   FirebaseUser updateFirebaseUser(Long userid,FirebaseUser feignUserDetails);
    void deleteFirebaseUser(Long userid);
    List<Balance> findAllBalances();
    Optional<Balance> findBalanceById(Long id);
    Balance saveBalance(Balance balance);
    void deleteBalance(Long id);

    void increaseBalance(Long userId);

    void decreaseBalance(Long userId, double amount);
}
