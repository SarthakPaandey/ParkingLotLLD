package com.example.parkinglot;

public class App {
    public static void main(String[] args) {
        Gate g1 = new Gate("G1", new Position(0, 0, 0));
        Gate g2 = new Gate("G2", new Position(0, 5, 5));

        java.util.List<Gate> entries = java.util.Arrays.asList(g1, g2);
        java.util.List<Gate> exits = java.util.Collections.singletonList(new Gate("X1", new Position(0, 10, 0)));

        java.util.List<ParkingSpot> spots = new java.util.ArrayList<>();
        spots.add(new ParkingSpot("S-0-0", SpotType.SMALL, new Position(0, 0, 1)));
        spots.add(new ParkingSpot("S-0-1", SpotType.SMALL, new Position(0, 0, 2)));
        spots.add(new ParkingSpot("M-0-0", SpotType.MEDIUM, new Position(0, 1, 0)));
        spots.add(new ParkingSpot("L-0-0", SpotType.LARGE, new Position(0, 2, 0)));
        spots.add(new ParkingSpot("E-0-0", SpotType.ELECTRIC, new Position(0, 1, 1)));
        spots.add(new ParkingSpot("M-1-0", SpotType.MEDIUM, new Position(1, 0, 0)));

        PricingStrategy pricing = new PricingStrategy(20, 30, 50, 35, 10);
        ParkingLot lot = new ParkingLot(entries, exits, spots, pricing);

        Vehicle v1 = new Vehicle("KA01AB1234", VehicleType.CAR);
        ParkingSpot nearestForCarAtG1 = lot.getSlot("G1", v1.getVehicleType());
        System.out.println("Nearest slot for CAR at G1: " + (nearestForCarAtG1 != null ? nearestForCarAtG1.getSpotId() : "NONE"));
        Ticket t1 = lot.park("G1", v1);
        System.out.println("Parked CAR at: " + (t1 != null ? t1.getSpot().getSpotId() : "FAILED"));

        Vehicle v2 = new Vehicle("KA02CD5678", VehicleType.ELECTRIC_BIKE);
        ParkingSpot nearestForEBikeAtG1 = lot.getSlot("G1", v2.getVehicleType());
        System.out.println("Nearest slot for E-BIKE at G1: " + (nearestForEBikeAtG1 != null ? nearestForEBikeAtG1.getSpotId() : "NONE"));
        Ticket t2 = lot.park("G1", v2);
        System.out.println("Parked E-BIKE at: " + (t2 != null ? t2.getSpot().getSpotId() : "FAILED"));

        try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
        double fee1 = lot.unpark(t1.getSpot().getSpotId());
        double fee2 = lot.unpark(t2.getSpot().getSpotId());
        System.out.println("Fees: car=" + fee1 + ", ebike=" + fee2);
    }
}


