package com.example.parkinglot;

public class Gate {
    private final String gateId;
    private final Position position;

    public Gate(String gateId, Position position) {
        this.gateId = gateId;
        this.position = position;
    }

    public String getGateId() { return gateId; }
    public Position getPosition() { return position; }
}


