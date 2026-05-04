package com.karlsson.wigellmcrental.config;

import com.karlsson.wigellmcrental.entities.Address;
import com.karlsson.wigellmcrental.entities.Booking;
import com.karlsson.wigellmcrental.entities.Customer;
import com.karlsson.wigellmcrental.entities.Motorcycle;
import com.karlsson.wigellmcrental.repo.AddressRepository;
import com.karlsson.wigellmcrental.repo.BookingRepository;
import com.karlsson.wigellmcrental.repo.CustomerRepository;
import com.karlsson.wigellmcrental.repo.MotorcycleRepository;
import com.karlsson.wigellmcrental.service.CurrencyService;
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
            AddressRepository addressRepo,
            PasswordEncoder encoder
    ) {
        return args -> {

            //
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setFirstName("Alex");
            admin.setLastName("Johansson");
            admin.setEmail("alex.johansson@email.com");
            admin.setPassword(encoder.encode("admin"));
            admin.setPhoneNumber("0701234567");
            admin.setRole("ROLE_ADMIN");

            Customer user1 = new Customer();
            user1.setUsername("maria");
            user1.setFirstName("Maria");
            user1.setLastName("Andersson");
            user1.setEmail("maria.andersson@email.com");
            user1.setPassword(encoder.encode("password"));
            user1.setPhoneNumber("0702345678");
            user1.setRole("ROLE_USER");

            Customer user2 = new Customer();
            user2.setUsername("jonas");
            user2.setFirstName("Jonas");
            user2.setLastName("Nilsson");
            user2.setEmail("jonas.nilsson@email.com");
            user2.setPassword(encoder.encode("password"));
            user2.setPhoneNumber("0703456789");
            user2.setRole("ROLE_USER");

            Customer user3 = new Customer();
            user3.setUsername("emma");
            user3.setFirstName("Emma");
            user3.setLastName("Karlsson");
            user3.setEmail("emma.karlsson@email.com");
            user3.setPassword(encoder.encode("password"));
            user3.setPhoneNumber("0704567890");
            user3.setRole("ROLE_USER");

            Customer user4 = new Customer();
            user4.setUsername("leo");
            user4.setFirstName("Leo");
            user4.setLastName("Eriksson");
            user4.setEmail("leo.eriksson@email.com");
            user4.setPassword(encoder.encode("password"));
            user4.setPhoneNumber("0705678901");
            user4.setRole("ROLE_USER");

            customerRepo.saveAll(List.of(admin, user1, user2, user3, user4));

            Address a1 = new Address();
            a1.setStreet("Storgatan 1");
            a1.setCity("Stockholm");
            a1.setCustomer(admin);

            Address a2 = new Address();
            a2.setStreet("Parkvägen 12");
            a2.setCity("Göteborg");
            a2.setCustomer(user1);

            Address a3 = new Address();
            a3.setStreet("Björkgatan 5");
            a3.setCity("Malmö");
            a3.setCustomer(user2);

            Address a4 = new Address();
            a4.setStreet("Solvägen 8");
            a4.setCity("Uppsala");
            a4.setCustomer(user3);

            Address a5 = new Address();
            a5.setStreet("Havsgatan 3");
            a5.setCity("Helsingborg");
            a5.setCustomer(user4);
            addressRepo.saveAll(List.of(a1, a2, a3, a4, a5));

            //mc
            Motorcycle mc1 = new Motorcycle();
            mc1.setBrand("Yamaha");
            mc1.setModel("R1");
            mc1.setMakeYear("2022");
            mc1.setPricePerDaySek(999);

            Motorcycle mc2 = new Motorcycle();
            mc2.setBrand("Honda");
            mc2.setModel("CBR600RR");
            mc2.setMakeYear("2021");
            mc2.setPricePerDaySek(850);

            Motorcycle mc3 = new Motorcycle();
            mc3.setBrand("Kawasaki");
            mc3.setModel("Ninja ZX-10R");
            mc3.setMakeYear("2023");
            mc3.setPricePerDaySek(1100);

            Motorcycle mc4 = new Motorcycle();
            mc4.setBrand("Suzuki");
            mc4.setModel("GSX-R750");
            mc4.setMakeYear("2020");
            mc4.setPricePerDaySek(780);

            Motorcycle mc5 = new Motorcycle();
            mc5.setBrand("Ducati");
            mc5.setModel("Panigale V4");
            mc5.setMakeYear("2023");
            mc5.setPricePerDaySek(1500);

            motorcycleRepo.saveAll(List.of(mc1, mc2, mc3, mc4, mc5));

            // bookings
            Booking b1 = new Booking();
            b1.setMotorcycleId(mc1.getId());
            b1.setCustomerId(user1.getId());
            b1.setStartDate(LocalDate.now());
            b1.setEndDate(LocalDate.now().plusDays(3));
            b1.setStatus(Booking.BookingStatus.valueOf("ACTIVE"));
            b1.setBookingPriceSek(mc1.getPricePerDaySek() + 3);
            b1.setBookingPriceGbp(CurrencyService.sekToGBP(mc1.getPricePerDaySek() + 3));

            Booking b2 = new Booking();
            b2.setMotorcycleId(mc2.getId());
            b2.setCustomerId(user2.getId());
            b2.setStartDate(LocalDate.now().plusDays(1));
            b2.setEndDate(LocalDate.now().plusDays(4));
            b2.setStatus(Booking.BookingStatus.valueOf("ACTIVE"));
            b2.setBookingPriceSek(mc2.getPricePerDaySek() + 3);
            b2.setBookingPriceGbp(CurrencyService.sekToGBP(mc2.getPricePerDaySek() + 3));

            bookingRepo.saveAll(List.of(b1, b2));

            System.out.println("Data loaded.");

        };
    }
}
