package com.example.parkinglot;

import java.time.Instant;
import java.util.*;

public class ParkingLot {
    private final List<Gate> entryGates;
    private final List<Gate> exitGates;
    private final Map<String, ParkingSpot> spotIdToSpot;
    private final AllocationIndex allocationIndex;
    private final PricingStrategy pricingStrategy;
    private final Map<String, Ticket> activeTicketsBySpotId = new HashMap<>();

    public ParkingLot(List<Gate> entryGates, List<Gate> exitGates, Collection<ParkingSpot> spots, PricingStrategy pricingStrategy) {
        this.entryGates = new ArrayList<>(entryGates);
        this.exitGates = new ArrayList<>(exitGates);
        this.spotIdToSpot = new HashMap<>();
        for (ParkingSpot s : spots) {
            this.spotIdToSpot.put(s.getSpotId(), s);
        }
        this.pricingStrategy = pricingStrategy;
        this.allocationIndex = new AllocationIndex(entryGates, spots);
    }

    public ParkingSpot getSlot(String entryGateId, VehicleType vehicleType) {
        return allocationIndex.getNearestAvailable(entryGateId, vehicleType);
    }

    public Ticket park(String entryGateId, Vehicle vehicle) {
        ParkingSpot spot = getSlot(entryGateId, vehicle.getVehicleType());
        if (spot == null) return null;
        spot.occupy();
        allocationIndex.onSpotOccupied(spot);
        Ticket ticket = new Ticket(UUID.randomUUID().toString(), vehicle, spot, getGateById(entryGateId), Instant.now());
        activeTicketsBySpotId.put(spot.getSpotId(), ticket);
        return ticket;
    }

    public double unpark(String spotId) {
        Ticket t = activeTicketsBySpotId.remove(spotId);
        if (t == null) return 0.0;
        double fee = pricingStrategy.calculateFee(t, Instant.now());
        ParkingSpot spot = t.getSpot();
        spot.free();
        allocationIndex.onSpotFreed(spot);
        return fee;
    }

    private Gate getGateById(String id) {
        for (Gate g : entryGates) if (g.getGateId().equals(id)) return g;
        for (Gate g : exitGates) if (g.getGateId().equals(id)) return g;
        return null;
    }

    public ParkingSpot getSpotById(String spotId) { return spotIdToSpot.get(spotId); }

    public void addSpot(ParkingSpot spot) {
        spotIdToSpot.put(spot.getSpotId(), spot);
        allocationIndex.addSpot(spot);
    }

    public void removeSpot(String spotId) {
        allocationIndex.removeSpot(spotId);
        spotIdToSpot.remove(spotId);
    }

    public Collection<Ticket> getActiveTickets() {
        return Collections.unmodifiableCollection(activeTicketsBySpotId.values());
    }

    public Set<String> getEntryGateIds() {
        Set<String> ids = new HashSet<>();
        for (Gate g : entryGates) ids.add(g.getGateId());
        return ids;
    }
}


