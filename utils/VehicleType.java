package utils;

public enum VehicleType {
    SHIP            ("SHIP"),
    TRUCK           ("TRUCK"),
    REEFER_TRUCK    ("REEFER TRUCK"),
    TANKER_TRUCK    ("TANKER TRUCK");

    private final String type;

    private VehicleType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
