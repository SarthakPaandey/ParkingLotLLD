package com.example.parkinglot;

public final class Distance implements Comparable<Distance> {
    private final int manhattanDistance;
    private final String spotId;

    public Distance(int manhattanDistance, String spotId) {
        this.manhattanDistance = manhattanDistance;
        this.spotId = spotId;
    }

    public int getManhattanDistance() { return manhattanDistance; }
    public String getSpotId() { return spotId; }

    @Override
    public int compareTo(Distance other) {
        if (this.manhattanDistance != other.manhattanDistance) {
            return Integer.compare(this.manhattanDistance, other.manhattanDistance);
        }
        return this.spotId.compareTo(other.spotId);
    }
}


