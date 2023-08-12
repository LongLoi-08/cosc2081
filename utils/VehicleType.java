package utils;

public enum VehicleType {
    SHIP    ("SHIP"),
    TRUCK   ("TRUCK");

    private final String type;

    private VehicleType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
