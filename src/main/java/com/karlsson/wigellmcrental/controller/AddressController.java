package com.karlsson.wigellmcrental.controller;

import com.karlsson.wigellmcrental.dto.AddressDTO;
import com.karlsson.wigellmcrental.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("motorcycles/api/v1/")
public class AddressController {

    private static final Logger logger =
            LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/customers/{customerId}/addresses")
    public AddressDTO addAddress(
            @PathVariable Long customerId,
            @RequestBody AddressDTO dto) {

        AddressDTO saved = addressService.addAddress(customerId, dto); //workaround to get addressid into log

        logger.info("Admin created address id={} for customerId={}", saved.id, customerId);

        return saved;
    }

    @DeleteMapping("/customers/{customerId}/addresses/{addressId}")
    public void deleteAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId) {

        addressService.delete(customerId, addressId);

        logger.warn("Admin deleted address id={} for customerId={}",
                addressId, customerId);
    }
}
