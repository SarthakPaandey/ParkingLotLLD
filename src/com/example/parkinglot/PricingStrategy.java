package com.example.parkinglot;

import java.time.Duration;
import java.time.Instant;

public class PricingStrategy {
    private final double basePerHourSmall;
    private final double basePerHourMedium;
    private final double basePerHourLarge;
    private final double basePerHourElectric;
    private final double electricSurchargePerHour;

    public PricingStrategy(double small, double medium, double large, double electric, double evSurcharge) {
        this.basePerHourSmall = small;
        this.basePerHourMedium = medium;
        this.basePerHourLarge = large;
        this.basePerHourElectric = electric;
        this.electricSurchargePerHour = evSurcharge;
    }

    public double calculateFee(Ticket ticket, Instant exitTime) {
        long minutes = Math.max(1, Duration.between(ticket.getEntryTime(), exitTime).toMinutes());
        double hours = Math.ceil(minutes / 60.0);
        SpotType st = ticket.getSpot().getSpotType();
        double base;
        switch (st) {
            case SMALL: base = basePerHourSmall; break;
            case MEDIUM: base = basePerHourMedium; break;
            case LARGE: base = basePerHourLarge; break;
            case ELECTRIC: base = basePerHourElectric; break;
            default: base = basePerHourMedium;
        }
        double cost = hours * base;
        if (st == SpotType.ELECTRIC && (ticket.getVehicle().getVehicleType() == VehicleType.ELECTRIC_BIKE || ticket.getVehicle().getVehicleType() == VehicleType.CAR)) {
            cost += hours * electricSurchargePerHour;
        }
        return cost;
    }

    public double previewFee(SpotType spotType, VehicleType vehicleType, long minutes) {
        double hours = Math.ceil(Math.max(1, minutes) / 60.0);
        double base;
        switch (spotType) {
            case SMALL: base = basePerHourSmall; break;
            case MEDIUM: base = basePerHourMedium; break;
            case LARGE: base = basePerHourLarge; break;
            case ELECTRIC: base = basePerHourElectric; break;
            default: base = basePerHourMedium;
        }
        double cost = hours * base;
        if (spotType == SpotType.ELECTRIC && (vehicleType == VehicleType.ELECTRIC_BIKE || vehicleType == VehicleType.CAR)) {
            cost += hours * electricSurchargePerHour;
        }
        return cost;
    }
}


