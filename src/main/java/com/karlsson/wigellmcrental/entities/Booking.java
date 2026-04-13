package com.karlsson.wigellmcrental.entities;

import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
   private BookingStatus status;

    public Booking() {}

    public Booking(long customerId, long motorcycleId, LocalDate startDate, LocalDate endDate) {
        this.customerId = customerId;
        this.motorcycleId = motorcycleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = BookingStatus.ACTIVE;
    }
    public enum BookingStatus {
        ACTIVE,
        RETURNED,
        LATE
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

    public BookingStatus getStatus() {
        return status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
