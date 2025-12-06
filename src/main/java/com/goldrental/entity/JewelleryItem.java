package main.java.com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "jewellery_items")
public class JewelleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;               // Name of the jewellery item
    private String type;               // Ring, Necklace, Bracelet, etc.
    private BigDecimal price;          // Total value in INR
    private BigDecimal dailyRentalRate; // Daily rental charge
    private double weight;             // Weight in grams
    private String status;             // AVAILABLE, RENTED, MAINTENANCE

    @ManyToOne
    @JoinColumn(name = "jeweller_id")
    private Jeweller jeweller;         // Owner jeweller

    public JewelleryItem() {}

    public JewelleryItem(String name, String type, BigDecimal price, BigDecimal dailyRentalRate,
                         double weight, String status, Jeweller jeweller) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.dailyRentalRate = dailyRentalRate;
        this.weight = weight;
        this.status = status;
        this.jeweller = jeweller;
    }

    // --- Getters and Setters ---

}
