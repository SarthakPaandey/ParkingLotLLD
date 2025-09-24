package com.example.parkinglot;

import java.util.*;

public class AllocationIndex {
    private final Map<String, NavigableSet<Distance>> gateToDistanceSet = new HashMap<>();
    private final Map<String, ParkingSpot> spotIdToSpot = new HashMap<>();
    private final Map<String, Gate> gatesById = new HashMap<>();

    public AllocationIndex(List<Gate> gates, Collection<ParkingSpot> spots) {
        for (Gate gate : gates) {
            gatesById.put(gate.getGateId(), gate);
            gateToDistanceSet.put(gate.getGateId(), new TreeSet<>());
        }
        for (ParkingSpot spot : spots) {
            spotIdToSpot.put(spot.getSpotId(), spot);
        }
        recomputeAll();
    }

    private static final int FLOOR_WEIGHT = 1000;

    private int manhattan(Position a, Position b) {
        return a.manhattanTo(b, FLOOR_WEIGHT);
    }

    private void recomputeAll() {
        for (NavigableSet<Distance> set : gateToDistanceSet.values()) {
            set.clear();
        }
        for (Gate gate : gatesById.values()) {
            NavigableSet<Distance> set = gateToDistanceSet.get(gate.getGateId());
            for (ParkingSpot spot : spotIdToSpot.values()) {
                if (!spot.isOccupied()) {
                    int d = manhattan(gate.getPosition(), spot.getPosition());
                    set.add(new Distance(d, spot.getSpotId()));
                }
            }
        }
    }

    public void onSpotOccupied(ParkingSpot spot) {
        for (Map.Entry<String, NavigableSet<Distance>> e : gateToDistanceSet.entrySet()) {
            Gate gate = gatesById.get(e.getKey());
            int d = manhattan(gate.getPosition(), spot.getPosition());
            e.getValue().remove(new Distance(d, spot.getSpotId()));
        }
    }

    public void onSpotFreed(ParkingSpot spot) {
        for (Map.Entry<String, NavigableSet<Distance>> e : gateToDistanceSet.entrySet()) {
            Gate gate = gatesById.get(e.getKey());
            int d = manhattan(gate.getPosition(), spot.getPosition());
            e.getValue().add(new Distance(d, spot.getSpotId()));
        }
    }

    public ParkingSpot getNearestAvailable(String gateId, VehicleType vehicleType) {
        NavigableSet<Distance> set = gateToDistanceSet.get(gateId);
        if (set == null) return null;
        for (Distance d : set) {
            ParkingSpot spot = spotIdToSpot.get(d.getSpotId());
            if (spot != null && !spot.isOccupied() && CompatibilityRules.canPark(vehicleType, spot.getSpotType())) {
                return spot;
            }
        }
        return null;
    }

    public void addSpot(ParkingSpot spot) {
        spotIdToSpot.put(spot.getSpotId(), spot);
        for (Map.Entry<String, NavigableSet<Distance>> e : gateToDistanceSet.entrySet()) {
            Gate gate = gatesById.get(e.getKey());
            if (!spot.isOccupied()) {
                int d = manhattan(gate.getPosition(), spot.getPosition());
                e.getValue().add(new Distance(d, spot.getSpotId()));
            }
        }
    }

    public void removeSpot(String spotId) {
        ParkingSpot spot = spotIdToSpot.remove(spotId);
        if (spot == null) return;
        for (Map.Entry<String, NavigableSet<Distance>> e : gateToDistanceSet.entrySet()) {
            Gate gate = gatesById.get(e.getKey());
            int d = manhattan(gate.getPosition(), spot.getPosition());
            e.getValue().remove(new Distance(d, spot.getSpotId()));
        }
    }

    public Set<String> getGateIds() {
        return Collections.unmodifiableSet(gateToDistanceSet.keySet());
    }
}


