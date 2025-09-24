## Parking Lot System

### How to run

1. Compile
```
javac -d bin $(find src -name "*.java")
```
2. Run
```
java -cp bin com.example.parkinglot.App
```

### Key features
- Nearest-slot allocation per entry gate using TreeSet-backed index.
- Multi-floor support via weighted Manhattan distance (floor weight favored).
- Vehicle-to-spot compatibility rules, including electric charging surcharge.

### UML Diagram (Mermaid)

```mermaid
classDiagram
    class VehicleType {<<enum>>}
    class SpotType {<<enum>>}

    class Position {
      -int floor
      -int row
      -int col
      +Position(floor, row, col)
      +getFloor() int
      +getRow() int
      +getCol() int
      +manhattanTo(other, floorWeight) int
      +equals(Object) boolean
      +hashCode() int
      +toString() String
    }

    class Gate {
      -String gateId
      -Position position
      +Gate(gateId, position)
      +getGateId() String
      +getPosition() Position
      +distanceTo(pos, floorWeight) int
      +toString() String
    }

    class ParkingSpot {
      -String spotId
      -SpotType spotType
      -Position position
      -boolean occupied
      +ParkingSpot(spotId, spotType, position)
      +getSpotId() String
      +getSpotType() SpotType
      +getPosition() Position
      +isOccupied() boolean
      +setOccupied(boolean) void
      +canFit(vehicle) boolean
      +occupy() void
      +free() void
      +equals(Object) boolean
      +hashCode() int
      +toString() String
    }

    class Vehicle {
      -String registrationNumber
      -VehicleType vehicleType
      +Vehicle(registrationNumber, vehicleType)
      +getRegistrationNumber() String
      +getVehicleType() VehicleType
      +canParkOn(spotType) boolean
      +toString() String
    }

    class Ticket {
      -String ticketId
      -Vehicle vehicle
      -ParkingSpot spot
      -Gate entryGate
      -Instant entryTime
      +Ticket(ticketId, vehicle, spot, entryGate, entryTime)
      +getTicketId() String
      +getVehicle() Vehicle
      +getSpot() ParkingSpot
      +getEntryGate() Gate
      +getEntryTime() Instant
      +parkedMinutesUntil(exitTime) long
      +toString() String
    }

    class PricingStrategy {
      -double basePerHourSmall
      -double basePerHourMedium
      -double basePerHourLarge
      -double basePerHourElectric
      -double electricSurchargePerHour
      +PricingStrategy(small, medium, large, electric, evSurcharge)
      +calculateFee(ticket, exitTime) double
      +previewFee(spotType, vehicleType, minutes) double
    }

    class AllocationIndex {
      -Map~String, TreeSet~Distance~~ gateToDistanceSet
      -Map~String, ParkingSpot~ spotIdToSpot
      -Map~String, Gate~ gatesById
      +AllocationIndex(gates, spots)
      +onSpotOccupied(spot) void
      +onSpotFreed(spot) void
      +getNearestAvailable(gateId, vehicleType) ParkingSpot
      +addSpot(spot) void
      +removeSpot(spotId) void
      +getGateIds() Set~String~
    }

    class Distance {
      -int manhattanDistance
      -String spotId
      +Distance(manhattanDistance, spotId)
      +getManhattanDistance() int
      +getSpotId() String
      +compareTo(other) int
      +equals(Object) boolean
      +hashCode() int
      +toString() String
    }

    class ParkingLot {
      -List~Gate~ entryGates
      -List~Gate~ exitGates
      -Map~String, ParkingSpot~ spotIdToSpot
      -AllocationIndex allocationIndex
      -PricingStrategy pricingStrategy
      -Map~String, Ticket~ activeTicketsBySpotId
      +getSlot(entryGateId, vehicleType) ParkingSpot
      +park(entryGateId, vehicle) Ticket
      +unpark(spotId) double
      +getSpotById(spotId) ParkingSpot
      +addSpot(spot) void
      +removeSpot(spotId) void
      +getActiveTickets() Collection~Ticket~
      +getEntryGateIds() Set~String~
    }

    class CompatibilityRules {
      +canPark(vehicleType, spotType) boolean
    }

    Vehicle --> VehicleType
    ParkingSpot --> SpotType
    Gate --> Position
    Ticket --> Vehicle
    Ticket --> ParkingSpot
    Ticket --> Gate
    AllocationIndex --> Distance
    AllocationIndex --> ParkingSpot
    AllocationIndex --> Gate
    ParkingLot --> AllocationIndex
    ParkingLot --> PricingStrategy
    ParkingLot --> Ticket
    CompatibilityRules ..> VehicleType
    CompatibilityRules ..> SpotType
```



