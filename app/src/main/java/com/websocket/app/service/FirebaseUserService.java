package com.websocket.app.service;

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
}
