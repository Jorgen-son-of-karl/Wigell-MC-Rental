package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.MotorcycleDTO;
import com.karlsson.wigellmcrental.service.MotorcycleService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v1")
public class MotorcycleController {
    private static final Logger logger =
            LoggerFactory.getLogger(MotorcycleController.class);

    private final MotorcycleService service;

    public MotorcycleController(MotorcycleService service) {
        this.service = service;
    }


    @GetMapping("/motorcycles")
    public List<MotorcycleDTO> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public MotorcycleDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    public MotorcycleDTO create(@Valid @RequestBody MotorcycleDTO dto) {
        logger.info("Admin created motorcycle: {} {} {} {}", dto.brand, dto.model, dto.makeYear, dto.pricePerDay);
        return service.create(dto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        MotorcycleDTO mc = service.getById(id); //fetching for attributes

        logger.warn("Admin deleted motorcycle: id={}, brand={}, model={}, year={}, price={}",
                mc.id,
                mc.brand,
                mc.model,
                mc.makeYear,
                mc.pricePerDay
        );

        service.delete(id);
    }
    @GetMapping("/availability")
    public List<MotorcycleDTO> getAvailable(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return service.getAvailable(from.toString(), to.toString());
    }

    //==========================ADMIN=====================================

    // get all motorcycles
    @GetMapping("/api/v1/bikes")
    public List<MotorcycleDTO> getAllAdmin() {
        logger.info("Admin fetching all motorcycles");
        return service.getAll();
    }

    // get by id
    @GetMapping("/api/v1/bikes/{id}")
    public MotorcycleDTO getOne(@PathVariable Long id) {
        logger.info("Admin fetching motorcycle id={}", id);
        return service.getById(id);
    }

    // put update
    @PutMapping("/api/v1/bikes/{id}")
    public MotorcycleDTO update(@PathVariable Long id,
                                @RequestBody MotorcycleDTO dto) {

        logger.info("Admin updating motorcycle id={}", id);
        return service.update(id, dto);
    }
}
