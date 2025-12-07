package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jewellers")
public class Jeweller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean verified;
    public Jeweller() {}

    public Jeweller(String name) {
        this.name = name;
    }

}
