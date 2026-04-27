package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.MotorcycleDTO;
import com.karlsson.wigellmcrental.service.MotorcycleService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/motorcycles/api/v1")
public class MotorcycleController {

    private static final Logger logger =
            LoggerFactory.getLogger(MotorcycleController.class);

    private final MotorcycleService service;

    public MotorcycleController(MotorcycleService service) {
        this.service = service;
    }

    @GetMapping("/motorcycles")
    public ResponseEntity<List<MotorcycleDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/motorcycles/{id}")
    public ResponseEntity<MotorcycleDTO> getById(@PathVariable Long id) {

        MotorcycleDTO mc = service.getById(id);

        if (mc == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mc);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<MotorcycleDTO>> getAvailable(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<MotorcycleDTO> available = service.getAvailable(from.toString(), to.toString());

        return ResponseEntity.ok(available);
    }

    // ========================== ADMIN ==========================

    @PostMapping("/bikes")
    public ResponseEntity<MotorcycleDTO> create(@Valid @RequestBody MotorcycleDTO dto) {

        logger.info("Admin created motorcycle: {} {} {} {}", dto.brand, dto.model, dto.makeYear, dto.pricePerDay);

        MotorcycleDTO created = service.create(dto);

        URI location = URI.create("/api/v1/bikes/" + created.id);

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        MotorcycleDTO mc = service.getById(id);

        if (mc == null) {
            return ResponseEntity.notFound().build();
        }

        logger.warn("Admin deleted motorcycle: id={}, brand={}, model={}, year={}, price={}",
                mc.id,
                mc.brand,
                mc.model,
                mc.makeYear,
                mc.pricePerDay
        );

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<MotorcycleDTO>> getAllAdmin() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/bikes/{id}")
    public ResponseEntity<MotorcycleDTO> getOne(@PathVariable Long id) {

        MotorcycleDTO mc = service.getById(id);

        if (mc == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mc);
    }

    @PutMapping("/bikes/{id}")
    public ResponseEntity<MotorcycleDTO> update(
            @PathVariable Long id,
            @RequestBody MotorcycleDTO dto) {

        logger.info("Admin updating motorcycle id={}", id);

        MotorcycleDTO updated = service.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
