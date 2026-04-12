package com.karlsson.wigellmcrental.config;

import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.entities.Motorcycle;
import com.karlsson.wigellmcrental.repo.BookingRepository;
import com.karlsson.wigellmcrental.repo.CustomerRepository;
import com.karlsson.wigellmcrental.repo.MotorcycleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initTestData(
            CustomerRepository customerRepo,
            MotorcycleRepository motorcycleRepo,
            BookingRepository bookingRepo,
            PasswordEncoder encoder
    ) {
        return args -> {

            bookingRepo.deleteAll();
            motorcycleRepo.deleteAll();
            customerRepo.deleteAll();

            //
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setFirstName("Alex");
            admin.setLastName("Johansson");
            admin.setEmail("alex.johansson@email.com");
            admin.setPassword(encoder.encode("admin"));
            admin.setPhoneNumber("0701234567");
            admin.setAddress("Storgatan 1");
            admin.setCity("Stockholm");
            admin.setRole("ROLE_ADMIN");

            Customer user1 = new Customer();
            user1.setUsername("maria");
            user1.setFirstName("Maria");
            user1.setLastName("Andersson");
            user1.setEmail("maria.andersson@email.com");
            user1.setPassword(encoder.encode("password"));
            user1.setPhoneNumber("0702345678");
            user1.setAddress("Parkvägen 12");
            user1.setCity("Göteborg");
            user1.setRole("ROLE_USER");

            Customer user2 = new Customer();
            user2.setUsername("jonas");
            user2.setFirstName("Jonas");
            user2.setLastName("Nilsson");
            user2.setEmail("jonas.nilsson@email.com");
            user2.setPassword(encoder.encode("password"));
            user2.setPhoneNumber("0703456789");
            user2.setAddress("Björkgatan 5");
            user2.setCity("Malmö");
            user2.setRole("ROLE_USER");

            Customer user3 = new Customer();
            user3.setUsername("emma");
            user3.setFirstName("Emma");
            user3.setLastName("Karlsson");
            user3.setEmail("emma.karlsson@email.com");
            user3.setPassword(encoder.encode("password"));
            user3.setPhoneNumber("0704567890");
            user3.setAddress("Solvägen 8");
            user3.setCity("Uppsala");
            user3.setRole("ROLE_USER");

            Customer user4 = new Customer();
            user4.setUsername("leo");
            user4.setFirstName("Leo");
            user4.setLastName("Eriksson");
            user4.setEmail("leo.eriksson@email.com");
            user4.setPassword(encoder.encode("password"));
            user4.setPhoneNumber("0705678901");
            user4.setAddress("Havsgatan 3");
            user4.setCity("Helsingborg");
            user4.setRole("ROLE_USER");

            customerRepo.saveAll(List.of(admin, user1, user2, user3, user4));

            //mc
            Motorcycle mc1 = new Motorcycle();
            mc1.setBrand("Yamaha");
            mc1.setModel("R1");
            mc1.setMakeYear("2022");
            mc1.setPricePerDay(999);

            Motorcycle mc2 = new Motorcycle();
            mc2.setBrand("Honda");
            mc2.setModel("CBR600RR");
            mc2.setMakeYear("2021");
            mc2.setPricePerDay(850);

            Motorcycle mc3 = new Motorcycle();
            mc3.setBrand("Kawasaki");
            mc3.setModel("Ninja ZX-10R");
            mc3.setMakeYear("2023");
            mc3.setPricePerDay(1100);

            Motorcycle mc4 = new Motorcycle();
            mc4.setBrand("Suzuki");
            mc4.setModel("GSX-R750");
            mc4.setMakeYear("2020");
            mc4.setPricePerDay(780);

            Motorcycle mc5 = new Motorcycle();
            mc5.setBrand("Ducati");
            mc5.setModel("Panigale V4");
            mc5.setMakeYear("2023");
            mc5.setPricePerDay(1500);

            motorcycleRepo.saveAll(List.of(mc1, mc2, mc3, mc4, mc5));

            // bookings
            Booking b1 = new Booking();
            b1.setMotorcycleId(mc1.getId());
            b1.setCustomerId(user1.getId());
            b1.setStartDate(LocalDate.now());
            b1.setEndDate(LocalDate.now().plusDays(3));

            Booking b2 = new Booking();
            b2.setMotorcycleId(mc2.getId());
            b2.setCustomerId(user2.getId());
            b2.setStartDate(LocalDate.now().plusDays(1));
            b2.setEndDate(LocalDate.now().plusDays(4));

            bookingRepo.saveAll(List.of(b1, b2));

            System.out.println("Data loaded.");

        };
    }
}
