package kz.postkz.AdminCrud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

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
}
