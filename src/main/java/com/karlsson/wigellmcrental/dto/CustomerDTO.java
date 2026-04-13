package com.karlsson.wigellmcrental.dto;

import java.util.List;

public class CustomerDTO {


    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;
    public List<AddressDTO> addresses;
    public String role;
}
