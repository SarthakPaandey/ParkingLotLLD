package com.example.parkinglot;

public final class CompatibilityRules {
    private CompatibilityRules() {}

    public static boolean canPark(VehicleType vehicleType, SpotType spotType) {
        switch (vehicleType) {
            case BIKE:
            case ELECTRIC_BIKE:
                return spotType == SpotType.SMALL || spotType == SpotType.MEDIUM || spotType == SpotType.ELECTRIC;
            case CAR:
                return spotType == SpotType.MEDIUM || spotType == SpotType.LARGE || spotType == SpotType.ELECTRIC;
            case BUS:
                return spotType == SpotType.LARGE;
            default:
                return false;
        }
    }
}


