package utils;

public enum ContainerType {
    DRY_STORAGE ("DRY STORAGE"  , 3.5, 4.6),
    OPEN_TOP    ("OPEN TOP"     , 2.8, 3.2),
    OPEN_SIDE   ("OPEN_SIDE"    , 2.7, 3.2),
    REFRIGERATED("REFRIGERATED" , 4.5, 5.4),
    LIQUID      ("LIQUID"       , 4.8, 5.3);

    private final String type;
    private final double fuelConsumptionShip;
    private final double fuelConsumptionTruck;

    private ContainerType(String type, double fuelConsumptionShip, double fuelConsumptionTruck) {
        this.type = type;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public double getFuelConsumptionShip() {
        return fuelConsumptionShip;
    }

    public double getFuelConsumptionTruck() {
        return fuelConsumptionTruck;
    }

    @Override
    public String toString() {
        return type;
    }
}
