package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.BookingDTO;
import com.karlsson.wigellmcrental.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingController.class);

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public BookingDTO create(@Valid @RequestBody BookingDTO dto) {
        logger.info("User {} booked motorcycle {} from {} to {}",
                dto.customerId,
                dto.motorcycleId,
                dto.startDate,
                dto.endDate
        );
        return service.create(dto);
    }

/*    @GetMapping
    public List<BookingDTO> getByCustomer(@RequestParam Long customerId) {
        return service.getByCustomer(customerId);
    }*/

    @PatchMapping("bookings/{id}")
/*    public BookingDTO update(
            @PathVariable Long id,
            @RequestBody BookingDTO dto) {

        return service.update(id, dto);
    }*/


    //==========================ADMIN=====================================

    // GET all bookings
    @GetMapping
    public List<BookingDTO> getBookings(
            @RequestParam(required = false) Long customerId,
            @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {

        // loged in user
        String currentUsername = userDetails.getUsername();

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (customerId != null) {
            // admins can see everyones bookings with their id
            if (!isAdmin) {
                throw new AccessDeniedException("Du har inte behörighet att se andra kunders bokningar");
            }
            return service.getByCustomer(customerId);
        }

        // if no customerID then admin gets all bookings, the user gets their own bookings

        if (isAdmin) {
            return service.getAll();
        } else {
            return service.getByCustomer(Long.valueOf(currentUsername));
        }
    }

    // get one
/*    @GetMapping("/api/v1/bookings/{id}")
    public BookingDTO getOne(@PathVariable Long id) {
        logger.info("Admin fetching booking id={}", id);
        return service.getById(id);
    }*/

    // PUT update
    @PutMapping("/{id}")
    public BookingDTO update(@PathVariable Long id,
                             @RequestBody BookingDTO dto) {

        logger.info("Admin updating booking id={}", id);
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.warn("Admin deleted booking id={}", id);
        service.delete(id);
    }




}
