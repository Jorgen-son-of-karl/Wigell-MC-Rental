package com.karlsson.wigellmcrental.dto;

import java.time.LocalDate;

public class BookingDTO {

    public Long id;
    public Long motorcycleId;
    public Long customerId;
    public LocalDate startDate;
    public LocalDate endDate;
}
