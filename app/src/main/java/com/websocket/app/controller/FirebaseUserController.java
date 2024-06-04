package com.websocket.app.controller;

import com.websocket.app.entity.FirebaseUser;
import com.websocket.app.service.FirebaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
