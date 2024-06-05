package com.websocket.app.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;
    @OneToOne(mappedBy = "balance")
    private FirebaseUser firebaseUser;

    // Геттеры и сеттеры
}
