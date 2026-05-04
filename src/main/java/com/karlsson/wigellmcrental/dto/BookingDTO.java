package com.karlsson.wigellmcrental.dto;

import com.karlsson.wigellmcrental.entities.Booking;

import java.time.LocalDate;

public class BookingDTO {

    public Long id;
    public Long motorcycleId;
    public Long customerId;
    public LocalDate startDate;
    public LocalDate endDate;
    public Booking.BookingStatus status;
    public double bookingPriceSek;
    public double bookingPriceGbp;
}
