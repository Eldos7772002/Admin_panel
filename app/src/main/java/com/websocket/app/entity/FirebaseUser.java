package com.websocket.app.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "firebase_user")
public class FirebaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String email;
    private String name;
    private String expoToken;
}
