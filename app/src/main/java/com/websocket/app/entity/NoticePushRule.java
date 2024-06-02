package com.websocket.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "z_notice_push_rules")
public class NoticePushRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Code", nullable = false, length = 255)
    private String code;

    @Column(name = "Priority", nullable = false)
    private int priority;

    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "SMS_only", nullable = false)
    private boolean smsOnly;

    @Column(name = "SMS_on", nullable = false)
    private boolean smsOn;

    @Column(name = "Count")
    private Integer count;

    @Column(name = "Periodic", nullable = false)
    private int periodic;

}
