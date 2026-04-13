package com.karlsson.wigellmcrental.service;

import com.karlsson.wigellmcrental.dto.AddressDTO;
import com.karlsson.wigellmcrental.entities.Address;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.repo.AddressRepository;
import com.karlsson.wigellmcrental.repo.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository){
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public AddressDTO addAddress(Long customerId, AddressDTO dto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Address address = new Address();
        address.setStreet(dto.street);
        address.setCity(dto.city);
        address.setCustomer(customer);

        return toDTO(addressRepository.save(address));
    }

    private AddressDTO toDTO(Address address) {
       AddressDTO dto = new AddressDTO();
       dto.id = address.getId();
       dto.street = address.getStreet();
       dto.city = address.getCity();
       return dto;
    }

    public void delete(Long customerId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (address.getCustomer().getId() != (customerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        addressRepository.delete(address);
    }
}
