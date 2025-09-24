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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Distance other = (Distance) obj;
        return manhattanDistance == other.manhattanDistance && spotId.equals(other.spotId);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(manhattanDistance);
        result = 31 * result + spotId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "manhattanDistance=" + manhattanDistance +
                ", spotId='" + spotId + '\'' +
                '}';
    }
}


