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

    public static VehicleType getType(String type) {
        for (VehicleType vehicleType : VehicleType.values()) {
            if (type.equals(vehicleType.toString())) return vehicleType;
        }

        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}
