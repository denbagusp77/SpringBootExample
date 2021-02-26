package com.example.demo.datatransferobject;

import com.example.demo.domainvalue.EngineType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDto {

    @JsonIgnore
    private Long id;
    private String licensePlate;
    private int seatCount;
    private boolean convertible;
    private int rating;
    private EngineType engineType;
    private String manufacturer;
    private DriverDTO driver;

    
    private CarDto() {
    }

    private CarDto(Long id, String licensePlate, int seatCount, boolean convertible, int rating, EngineType engineType, String manufacturer, DriverDTO driver) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public int getRating() {
        return rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public static class CarDTOBuilder {
        private Long id;
        private String licensePlate;
        private int seatCount;
        private boolean convertible;
        private int rating;
        private EngineType engineType;
        private String manufacturer;
        private DriverDTO driver;

        public CarDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(int seatCount) {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setConvertible(boolean convertible) {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public CarDTOBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarDTOBuilder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public CarDTOBuilder setDriver(DriverDTO driver) {
            this.driver = driver;
            return this;
        }


        public CarDto build() {
            return new CarDto(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer, driver);
        }
    }
}