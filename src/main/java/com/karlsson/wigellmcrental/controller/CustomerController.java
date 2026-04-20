package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.CustomerDTO;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("motorcycles/api/v1/customers")
public class CustomerController {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // get all
    @GetMapping
    public List<CustomerDTO> getAll() {
        return service.getAll();
    }

    // get one by id
    @GetMapping("/{id}")
    public CustomerDTO getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    // post create
    @PostMapping
    public Customer create(@Valid @RequestBody Customer customer) {
        logger.info("Admin creating customer: {} {}", customer.getId(), customer.getUsername());
        return service.save(customer);
    }

    // delete by id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.warn("Admin deleted customer with id={}", id);
        service.delete(id);
    }

    // put update by id
    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO dto) {

        logger.info("Admin updating customer id={}", id);
        return service.update(id, dto);
    }
}
