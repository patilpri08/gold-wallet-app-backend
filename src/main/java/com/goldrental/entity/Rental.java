package main.java.com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rentals")
public class Rental {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jewellery_id")
    private Jewellery jewellery;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private BigDecimal total_rent;
    private Long jeweller_id;
    private Long jewellery_id;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private String rentalStatus;
}
