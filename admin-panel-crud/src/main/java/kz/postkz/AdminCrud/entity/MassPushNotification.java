package kz.postkz.AdminCrud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "mass_push_notifications", schema = "smsgate")
public class MassPushNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String description;

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String rule_code;

    @Column(nullable = false)
    private Boolean sent = false;

    @Column(name = "sent_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime sentAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

}