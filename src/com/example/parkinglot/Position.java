package com.example.parkinglot;

public class Position {
    private final int floor;
    private final int row;
    private final int col;

    public Position(int floor, int row, int col) {
        this.floor = floor;
        this.row = row;
        this.col = col;
    }

    public int getFloor() { return floor; }
    public int getRow() { return row; }
    public int getCol() { return col; }

    public int manhattanTo(Position other, int floorWeight) {
        int df = Math.abs(this.floor - other.floor);
        int dr = Math.abs(this.row - other.row);
        int dc = Math.abs(this.col - other.col);
        return df * floorWeight + dr + dc;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return floor == other.floor && row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(floor);
        result = 31 * result + Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(col);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "floor=" + floor +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}


