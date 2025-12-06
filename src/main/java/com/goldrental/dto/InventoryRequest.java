package main.java.com.goldrental.dto;

import java.math.BigDecimal;

public class InventoryRequest {

    private Long jewellerId;      // Which jeweller owns this item
    private String name;          // Jewellery name
    private String type;          // Ring, Necklace, Bracelet, etc.
    private BigDecimal price;     // Total value in INR
    private BigDecimal dailyRentalRate; // Daily rental charge
    private double weight;        // Weight in grams
    private String status;        // AVAILABLE, RENTED, MAINTENANCE

    public InventoryRequest() {}

    public InventoryRequest(Long jewellerId, String name, String type, BigDecimal price,
                            BigDecimal dailyRentalRate, double weight, String status) {
        this.jewellerId = jewellerId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.dailyRentalRate = dailyRentalRate;
        this.weight = weight;
        this.status = status;
    }

    // --- Getters and Setters ---

    public Long getJewellerId() {
        return jewellerId;
    }

    public void setJewellerId(Long jewellerId) {
        this.jewellerId = jewellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDailyRentalRate() {
        return dailyRentalRate;
    }

    public void setDailyRentalRate(BigDecimal dailyRentalRate) {
        this.dailyRentalRate = dailyRentalRate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
