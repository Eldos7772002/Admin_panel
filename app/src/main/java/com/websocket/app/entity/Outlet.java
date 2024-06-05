package com.websocket.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outlets")
@Data
public class Outlet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mall_id", referencedColumnName = "id")
    private Mall mall;

    @Column(name = "latitude1")
    private Double latitude1;

    @Column(name = "longitude1")
    private Double longitude1;

    @Column(name = "latitude2")
    private Double latitude2;

    @Column(name = "longitude2")
    private Double longitude2;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "default_notification")
    private String defaultNotification;

    @Column(name = "image_url")
    private String imageUrl;
}
