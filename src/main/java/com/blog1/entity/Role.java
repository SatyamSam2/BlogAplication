package com.blog1.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter //Applying Getter & Setter because @Data doesn't work in spring security
@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;
}
