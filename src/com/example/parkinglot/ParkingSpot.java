package com.example.parkinglot;

import java.util.Objects;

public class ParkingSpot {
    private final String spotId;
    private final SpotType spotType;
    private final Position position;
    private boolean occupied;

    public ParkingSpot(String spotId, SpotType spotType, Position position) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.position = position;
        this.occupied = false;
    }

    public String getSpotId() { return spotId; }
    public SpotType getSpotType() { return spotType; }
    public Position getPosition() { return position; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(spotId, that.spotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotId);
    }
}


