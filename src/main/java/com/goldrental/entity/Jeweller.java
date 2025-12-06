package main.java.com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "jewellers")
public class Jeweller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    // <-- Getter
    private boolean verified; // <-- Add this field

    public Jeweller() {}

    public Jeweller(String name, String email, boolean verified) {
        this.name = name;
        this.email = email;
        this.verified = verified;
    }

    // --- Getters and Setters ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(boolean verified) { // <-- Setter
        this.verified = verified;
    }
}
