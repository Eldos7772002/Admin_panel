package com.websocket.app.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "malls")
public class Mall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude1")
    private Double latitude1;

    @Column(name = "longitude1")
    private Double longitude1;

    @Column(name = "latitude2")
    private Double latitude2;

    @Column(name = "longitude2")
    private Double longitude2;

    @Column(name = "defaultnotification")
    private String defaultNotification;

}
