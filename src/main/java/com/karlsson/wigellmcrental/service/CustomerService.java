package com.karlsson.wigellmcrental.service;

import com.karlsson.wigellmcrental.dto.CustomerDTO;
import com.karlsson.wigellmcrental.dto.MotorcycleDTO;
import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.entities.Motorcycle;
import com.karlsson.wigellmcrental.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerDTO getByUsername(String username) {
        Customer customer = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(customer);
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }




    // all users
    public List<CustomerDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    //user from id
    public CustomerDTO getById(Long id) {
        Customer c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return toDTO(c);
    }

    //delete user by id
    public void delete(Long id) {
        repository.deleteById(id);
    }

    //update user
    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (dto.username != null) {
            c.setUsername(dto.username);
        }

        if (dto.firstName != null) {
            c.setFirstName(dto.firstName);
        }

        if (dto.lastName != null) {
            c.setLastName(dto.lastName);
        }
        if (dto.role != null) {
            c.setRole(dto.role);
        }
        if (dto.email != null) {
            c.setEmail(dto.email);
        }

        if (dto.password != null) {
            c.setPassword(dto.password); //not sure how realistic it is that admin can help with passwordreset, but it will serve for now
        }

        if (dto.phoneNumber != null) {
            c.setPhoneNumber(dto.phoneNumber);
        }
        if (dto.address != null) {
            c.setAddress(dto.address);
        }

        if (dto.city != null) {
            c.setCity(dto.city);
        }

        return toDTO(repository.save(c));
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.id = customer.getId();
        dto.username = customer.getUsername();
        dto.firstName = customer.getFirstName();
        dto.lastName = customer.getLastName();
        dto.role = customer.getRole();
        dto.email = customer.getEmail();
        dto.password = customer.getPassword();
        dto.phoneNumber = customer.getPhoneNumber();
        dto.address = customer.getAddress();
        dto.city = customer.getCity();
        return dto;
    }

}
