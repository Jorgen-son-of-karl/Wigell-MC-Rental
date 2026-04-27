package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.BookingDTO;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.service.BookingService;
import com.karlsson.wigellmcrental.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/motorcycles/api/v1/bookings")
public class BookingController {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final CustomerService customerService;

    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO dto) {

        BookingDTO created = bookingService.create(dto);

        URI location = URI.create("/api/v1/bookings/" + created.id);

        return ResponseEntity
                .created(location)
                .body(created);
    }

    //==========================ADMIN=====================================

    // GET all bookings
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getBookings(
            @RequestParam(required = false) Long customerId,
            Authentication authentication) {

        List<BookingDTO> bookings = bookingService.getAll(customerId, authentication);

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long id,
            Authentication authentication) {

        BookingDTO booking = bookingService.getById(id, authentication);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(booking);
    }

    // PUT update
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(
            @PathVariable Long id,
            @RequestBody BookingDTO dto) {

        logger.info("Admin updating booking id={}", id);

        BookingDTO updated = bookingService.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        logger.warn("Admin deleted booking id={}", id);

        bookingService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        logger.info("Admin updated status of booking id={}", id);

        BookingDTO updated = bookingService.updateStatus(id, body.get("status"));

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
