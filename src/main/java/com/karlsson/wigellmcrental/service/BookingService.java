package com.karlsson.wigellmcrental.service;

import com.karlsson.wigellmcrental.dto.BookingDTO;
import com.karlsson.wigellmcrental.dto.CustomerDTO;
import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.entities.Motorcycle;
import com.karlsson.wigellmcrental.repo.BookingRepository;
import com.karlsson.wigellmcrental.repo.CustomerRepository;
import com.karlsson.wigellmcrental.repo.MotorcycleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final CurrencyService currencyService;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, MotorcycleRepository motorcycleRepository, CurrencyService currencyService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.currencyService = currencyService;
    }

    public BookingDTO create(BookingDTO dto) {
        Motorcycle motorcycle = motorcycleRepository.findById(dto.motorcycleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motorcycle not found"));

        if (dto.startDate == null || dto.endDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start and end date are required");
        }
        if (dto.endDate.isBefore(dto.startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date");
        }

        long numberOfDays = ChronoUnit.DAYS.between(dto.startDate, dto.endDate);

        double totalSek = motorcycle.getPricePerDaySek() * numberOfDays;
        double totalGbp = currencyService.sekToGBP(totalSek);

        Booking booking = new Booking(
                dto.customerId,
                dto.motorcycleId,
                dto.startDate,
                dto.endDate,
                totalSek,
                totalGbp
        );
        Booking saved = bookingRepository.save(booking);
        return toDTO(saved);
    }


    public List<BookingDTO> getByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private BookingDTO toDTO(Booking b) {
        BookingDTO dto = new BookingDTO();
        dto.id = b.getId();
        dto.motorcycleId = b.getMotorcycleId();
        dto.customerId = b.getCustomerId();
        dto.startDate = b.getStartDate();
        dto.endDate = b.getEndDate();
        dto.status = b.getStatus();
        dto.bookingPriceSek = b.getBookingPriceSek();
        dto.bookingPriceGbp = b.getBookingPriceGbp();
        return dto;
    }

    private Booking toEntity(BookingDTO dto) {
        Booking b = new Booking();
        b.setMotorcycleId(dto.motorcycleId);
        b.setCustomerId(dto.customerId);
        b.setStartDate(dto.startDate);
        b.setEndDate(dto.endDate);
        b.setStatus(dto.status);
        b.setBookingPriceSek(dto.bookingPriceSek);
        b.setBookingPriceGbp(dto.bookingPriceGbp);
        return b;
    }

    public BookingDTO update(Long id, BookingDTO dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (dto.motorcycleId != null) {
            booking.setMotorcycleId(dto.motorcycleId);
        }

        if (dto.startDate != null) {
            booking.setStartDate(dto.startDate);
        }

        if (dto.endDate != null) {
            booking.setEndDate(dto.endDate);
        }
        if (dto.status != null) {
            booking.setStatus(dto.status);
        }

        return toDTO(bookingRepository.save(booking));
    }

    public List<BookingDTO> getAll(Long customerId, Authentication auth) {
        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        //admin gets all
        if (isAdmin) {

            if (customerId != null) {
                return bookingRepository.findByCustomerId(customerId)
                        .stream()
                        .map(this::toDTO)
                        .toList();
            }

            return bookingRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .toList();
        }

        // user only gets its own
        String username = auth.getName();

        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByCustomerId(customer.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BookingDTO getById(Long id, Authentication auth) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return toDTO(booking);
        }

        String username = auth.getName();
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (booking.getCustomerId() != (customer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return toDTO(booking);
    }
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public BookingDTO updateStatus(Long id, String status) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Booking.BookingStatus newStatus;
        try {
            newStatus = Booking.BookingStatus.valueOf(status.toUpperCase());
            booking.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        booking.setStatus(newStatus);

        return toDTO(bookingRepository.save(booking));
    }
}
