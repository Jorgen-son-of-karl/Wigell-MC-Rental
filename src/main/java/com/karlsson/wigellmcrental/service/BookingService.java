package com.karlsson.wigellmcrental.service;

import com.karlsson.wigellmcrental.dto.BookingDTO;
import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.repo.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public BookingDTO create(BookingDTO dto) {
        Booking booking = toEntity(dto);
        return toDTO(repository.save(booking));
    }

    public List<BookingDTO> getByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId)
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
        return dto;
    }

    private Booking toEntity(BookingDTO dto) {
        Booking b = new Booking();
        b.setId(dto.id);
        b.setMotorcycleId(dto.motorcycleId);
        b.setCustomerId(dto.customerId);
        b.setStartDate(dto.startDate);
        b.setEndDate(dto.endDate);
        return b;
    }

    public BookingDTO update(Long id, BookingDTO dto) {

        Booking booking = repository.findById(id)
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

        return toDTO(repository.save(booking));
    }

    public List<BookingDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
