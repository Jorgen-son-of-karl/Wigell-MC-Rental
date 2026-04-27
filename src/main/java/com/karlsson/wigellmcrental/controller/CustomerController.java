package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.CustomerDTO;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motorcycles/api/v1/customers")
public class CustomerController {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        List<CustomerDTO> customers = service.getAll();
        return ResponseEntity.ok(customers);
    }

    // GET one by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getOne(@PathVariable Long id) {
        CustomerDTO customer = service.getById(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    // POST create
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {



        System.out.println("Customer in: " + customer);
        Customer created = service.save(customer);

        URI location = URI.create("/motorcycles/api/v1/customers/" + created.getId());
        logger.info("Admin creating customer: {} {}", customer.getId(), customer.getUsername());
        return ResponseEntity
                .created(location)
                .body(created);
    }

    // DELETE by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        logger.warn("Admin deleted customer with id={}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // PUT update by id
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(
            @PathVariable Long id,
            @RequestBody CustomerDTO dto) {

        logger.info("Admin updating customer id={}", id);

        CustomerDTO updated = service.update(id, dto);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
