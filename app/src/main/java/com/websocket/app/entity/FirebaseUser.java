package com.websocket.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "firebase_user")
@Data
public class FirebaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String email;
    private String name;
    private String expoToken;

    @OneToOne
    @JoinColumn(name = "balance_id")
    @JsonIgnore // Игнорировать поле balance при сериализации
    private Balance balance;

}

