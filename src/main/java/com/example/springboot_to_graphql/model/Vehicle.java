package com.example.springboot_to_graphql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Original attributes
    private String make;
    private String model;

    @jakarta.persistence.Column(name = "\"year\"")
    private int year;

    // 20 new attributes
    private String vin;
    private String color;
    private int mileage;
    private double price;
    private String engineType;
    private String transmission;
    private String drivetrain;
    private String fuelType;
    private int doors;
    private int seats;
    private String bodyStyle;
    private String conditionStatus;
    private String inventoryStatus;
    private int ownerCount;
    private int horsepower;
    private int torque;
    private double cityMpg;
    private double highwayMpg;
    private double weightGvwr;
    private double wheelbase;

    public Vehicle() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public String getDrivetrain() { return drivetrain; }
    public void setDrivetrain(String drivetrain) { this.drivetrain = drivetrain; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public int getDoors() { return doors; }
    public void setDoors(int doors) { this.doors = doors; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public String getBodyStyle() { return bodyStyle; }
    public void setBodyStyle(String bodyStyle) { this.bodyStyle = bodyStyle; }

    public String getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }

    public String getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(String inventoryStatus) { this.inventoryStatus = inventoryStatus; }

    public int getOwnerCount() { return ownerCount; }
    public void setOwnerCount(int ownerCount) { this.ownerCount = ownerCount; }

    public int getHorsepower() { return horsepower; }
    public void setHorsepower(int horsepower) { this.horsepower = horsepower; }

    public int getTorque() { return torque; }
    public void setTorque(int torque) { this.torque = torque; }

    public double getCityMpg() { return cityMpg; }
    public void setCityMpg(double cityMpg) { this.cityMpg = cityMpg; }

    public double getHighwayMpg() { return highwayMpg; }
    public void setHighwayMpg(double highwayMpg) { this.highwayMpg = highwayMpg; }

    public double getWeightGvwr() { return weightGvwr; }
    public void setWeightGvwr(double weightGvwr) { this.weightGvwr = weightGvwr; }

    public double getWheelbase() { return wheelbase; }
    public void setWheelbase(double wheelbase) { this.wheelbase = wheelbase; }
}
