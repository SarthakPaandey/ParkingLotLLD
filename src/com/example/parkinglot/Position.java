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
}


