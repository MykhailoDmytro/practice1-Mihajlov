package ua.opnu.equipment_rental;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Column(nullable = false)
    private Boolean availability = true; // дефолтне значення

    // --- Геттери ---
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public Boolean getAvailability() {
        return availability;
    }

    // --- Сеттери ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
