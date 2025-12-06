package com.goldrental.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "jewellery_items")
public class Jewellery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jeweller_id;
    private String name; // ring, necklace, etc.
    private BigDecimal dailyRentalRate;
    private double weight;
    private String gold_purity; // AVAILABLE, RENTED, MAINTENANCE

    @ManyToOne
    @JoinColumn(name = "jeweller_id")
    private Jeweller jeweller;

    // getters and setters
}
