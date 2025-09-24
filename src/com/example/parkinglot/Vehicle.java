package com.example.parkinglot;

public class Vehicle {
    private final String registrationNumber;
    private final VehicleType vehicleType;

    public Vehicle(String registrationNumber, VehicleType vehicleType) {
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean canParkOn(SpotType spotType) {
        return CompatibilityRules.canPark(this.vehicleType, spotType);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
}


