package kz.postkz.AdminCrud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "malls")
@Data
public class Mall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

}