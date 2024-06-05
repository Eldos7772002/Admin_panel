package com.websocket.app.entity;

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

    @OneToOne(mappedBy = "firebaseUser", cascade = CascadeType.ALL)
    private Balance balance; // убедитесь, что это свойство существует
}

