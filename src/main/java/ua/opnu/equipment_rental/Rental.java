package ua.opnu.equipment_rental;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean returned;

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    // Обчислення вартості оренди
    public BigDecimal calculateRentalCost() {
        if (equipment == null || startDate == null || endDate == null) {
            return BigDecimal.ZERO;
        }
        long daysRented = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        return equipment.getDailyRate().multiply(BigDecimal.valueOf(daysRented));
    }

    public boolean isAvailable() {
        return equipment != null && Boolean.TRUE.equals(equipment.getAvailability());
    }
}
