package com.example.parkinglot;

import java.time.Instant;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final Gate entryGate;
    private final Instant entryTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot spot, Gate entryGate, Instant entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryGate = entryGate;
        this.entryTime = entryTime;
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public Gate getEntryGate() { return entryGate; }
    public Instant getEntryTime() { return entryTime; }

    public long parkedMinutesUntil(Instant exitTime) {
        return Math.max(0, java.time.Duration.between(entryTime, exitTime).toMinutes());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicle=" + vehicle +
                ", spot=" + spot +
                ", entryGate=" + entryGate +
                ", entryTime=" + entryTime +
                '}';
    }
}


