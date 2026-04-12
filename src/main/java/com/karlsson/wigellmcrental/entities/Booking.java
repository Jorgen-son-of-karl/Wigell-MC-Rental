package com.karlsson.wigellmcrental.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    @NotNull
    private Long motorcycleId; //motorcyckleid instead of manytoone for loose coupling

    private LocalDate startDate;
    private LocalDate endDate;

    public Booking() {}

    public Booking(long customerId, long motorcycleId, LocalDate startDate, LocalDate endDate) {
        this.customerId = customerId;
        this.motorcycleId = motorcycleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long userId) {
        this.customerId = userId;
    }

    public long getMotorcycleId() {
        return motorcycleId;
    }

    public void setMotorcycleId(long motorcycleId) {
        this.motorcycleId = motorcycleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
