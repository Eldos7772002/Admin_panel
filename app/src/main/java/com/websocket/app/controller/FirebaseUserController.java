package com.websocket.app.controller;

import com.websocket.app.entity.Balance;
import com.websocket.app.entity.FirebaseUser;
import com.websocket.app.service.FirebaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/firebaseUsers")
public class FirebaseUserController {

    @Autowired
    private FirebaseUserService firebaseUserService;

    @PostMapping
    public ResponseEntity<FirebaseUser> createFirebaseUser(@RequestBody FirebaseUser firebaseUser) {
        FirebaseUser createdFirebaseUser = firebaseUserService.saveFirebaseUser(firebaseUser);
        return ResponseEntity.ok(createdFirebaseUser);
    }

    @GetMapping
    public ResponseEntity<List<FirebaseUser>> getAllFirebaseUsers() {
        List<FirebaseUser> firebaseUsers = firebaseUserService.getAllFirebaseUsers();
        return ResponseEntity.ok(firebaseUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirebaseUser> getFirebaseUserById(@PathVariable Long id) {
        Optional<FirebaseUser> firebaseUser = firebaseUserService.getFirebaseUserById(id);
        return firebaseUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<FirebaseUser> getFirebaseUserByEmail(@RequestParam String email) {
        Optional<FirebaseUser> firebaseUser = firebaseUserService.getFirebaseUserByEmail(email);
        return firebaseUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/expoToken")
    public ResponseEntity<FirebaseUser> getFirebaseUserByExpoToken(@RequestParam String expoToken) {
        Optional<FirebaseUser> firebaseUser = firebaseUserService.getFirebaseUserByExpoToken(expoToken);
        return firebaseUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirebaseUser> updateFirebaseUser(@PathVariable Long id, @RequestBody FirebaseUser firebaseUserDetails) {
        try {
            FirebaseUser updatedFirebaseUser = firebaseUserService.updateFirebaseUser(id, firebaseUserDetails);
            return ResponseEntity.ok(updatedFirebaseUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFirebaseUser(@PathVariable Long id) {
        firebaseUserService.deleteFirebaseUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Balance> getBalanceForFirebaseUser(@PathVariable Long id) {
        Optional<Balance> balance = firebaseUserService.findBalanceById(id);
        return balance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/balance")
    public ResponseEntity<Balance> createBalanceForFirebaseUser(@PathVariable Long id, @RequestBody Balance balance) {
        balance.setId(id); // Привязываем баланс к пользователю
        Balance savedBalance = firebaseUserService.saveBalance(balance);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBalance);
    }

    @DeleteMapping("/{id}/balance")
    public ResponseEntity<Void> deleteBalanceForFirebaseUser(@PathVariable Long id) {
        firebaseUserService.deleteBalance(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/increase-balance")
    public ResponseEntity<Void> increaseBalance(@PathVariable Long id) {
        firebaseUserService.increaseBalance(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/decrease-balance")
    public ResponseEntity<Void> decreaseBalance(@PathVariable Long id, @RequestParam double amount) {
        firebaseUserService.decreaseBalance(id, amount);
        return ResponseEntity.ok().build();
    }
}
