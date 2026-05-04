package com.karlsson.wigellmcrental.service;

import com.karlsson.wigellmcrental.dto.MotorcycleDTO;
import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.entities.Motorcycle;
import com.karlsson.wigellmcrental.repo.BookingRepository;
import com.karlsson.wigellmcrental.repo.MotorcycleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final BookingRepository bookingRepository;

    public MotorcycleService(MotorcycleRepository motorcycleRepository, BookingRepository bookingRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<MotorcycleDTO> getAll() {
        return motorcycleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public MotorcycleDTO getById(Long id) {
        Motorcycle mc = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found"));
        return toDTO(mc);
    }

    public MotorcycleDTO create(MotorcycleDTO dto) {
        Motorcycle mc = toEntity(dto);
        return toDTO(motorcycleRepository.save(mc));
    }

    public void delete(Long id) {
        motorcycleRepository.deleteById(id);
    }

    private MotorcycleDTO toDTO(Motorcycle mc) {
        MotorcycleDTO dto = new MotorcycleDTO();
        dto.id = mc.getId();
        dto.brand = mc.getBrand();
        dto.model = mc.getModel();
        dto.makeYear = mc.getMakeYear();
        dto.pricePerDaySek = mc.getPricePerDaySek();
        dto.pricePerDayGbp = mc.getPricePerDayGbp();
        return dto;
    }

    private Motorcycle toEntity(MotorcycleDTO dto) {
        Motorcycle mc = new Motorcycle();
        mc.setBrand(dto.brand);
        mc.setModel(dto.model);
        mc.setMakeYear(dto.makeYear);
        mc.setPricePerDaySek(dto.pricePerDaySek);
        mc.setPricePerDayGbp(dto.pricePerDayGbp);
        return mc;
    }

    public List<MotorcycleDTO> getAvailable(String from, String to) {

        LocalDate start = LocalDate.parse(from);
        LocalDate end = LocalDate.parse(to);

        List<Motorcycle> all = motorcycleRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        return all.stream()
                .filter(mc -> bookings.stream()
                        .noneMatch(b ->
                                b.getMotorcycleId() == mc.getId() &&
                                        !(b.getEndDate().isBefore(start) || b.getStartDate().isAfter(end))
                        )
                )
                .map(this::toDTO)
                .toList();
    }

    public MotorcycleDTO update(Long id, MotorcycleDTO dto) {
        Motorcycle mc = motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found"));

        if (dto.brand != null) {
            mc.setBrand(dto.brand);
        }
        if (dto.model != null) {
            mc.setModel(dto.model);
        }
        if (dto.makeYear != null) {
            mc.setMakeYear(dto.makeYear);
        }
        if (dto.pricePerDaySek > 0) {
            mc.setPricePerDaySek(dto.pricePerDaySek);
        }
        return toDTO(motorcycleRepository.save(mc));
    }
}
