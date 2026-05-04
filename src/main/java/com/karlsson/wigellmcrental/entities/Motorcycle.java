package com.karlsson.wigellmcrental.entities;

import com.karlsson.wigellmcrental.service.CurrencyService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$")
    private String makeYear;

    @Positive(message = "Price must be positive")
    private double pricePerDaySek;

    private double pricePerDayGbp;

    public Motorcycle() {}

    public Motorcycle(String brand, String model, String makeYear, double pricePerDaySek) {
        this.brand = brand;
        this.model = model;
        this.makeYear = makeYear;
        this.pricePerDaySek = pricePerDaySek;
        this.pricePerDayGbp = CurrencyService.sekToGBP(pricePerDaySek);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDayGbp() {
        return pricePerDayGbp;
    }

    public void setPricePerDayGbp(double pricePerDayGbp) {
        this.pricePerDayGbp = pricePerDayGbp;
    }

    public String getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public double getPricePerDaySek() {
        return pricePerDaySek;
    }

    public void setPricePerDaySek(double pricePerDay) {
        this.pricePerDaySek = pricePerDay;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", makeYear='" + makeYear + '\'' +
                ", pricePerDay=" + pricePerDaySek +
                '}';
    }
}
