package utils;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ContainerType {
    DRY_STORAGE ("DRY STORAGE"  , 3.5, 4.6, Stream.of(VehicleType.SHIP, VehicleType.TRUCK).collect(Collectors.toCollection(ArrayList::new))),
    OPEN_TOP    ("OPEN TOP"     , 2.8, 3.2, Stream.of(VehicleType.SHIP, VehicleType.TRUCK).collect(Collectors.toCollection(ArrayList::new))),
    OPEN_SIDE   ("OPEN_SIDE"    , 2.7, 3.2, Stream.of(VehicleType.SHIP, VehicleType.TRUCK).collect(Collectors.toCollection(ArrayList::new))),
    REFRIGERATED("REFRIGERATED" , 4.5, 5.4, Stream.of(VehicleType.SHIP, VehicleType.REEFER_TRUCK).collect(Collectors.toCollection(ArrayList::new))),
    LIQUID      ("LIQUID"       , 4.8, 5.3, Stream.of(VehicleType.SHIP, VehicleType.TANKER_TRUCK).collect(Collectors.toCollection(ArrayList::new)));

    private final String type;

    // both are in [gallons / Km] unit
    private final double fuelConsumptionShip;
    private final double fuelConsumptionTruck;
    private final ArrayList<VehicleType> usableVehicle;

    private ContainerType(String type, double fuelConsumptionShip, double fuelConsumptionTruck, ArrayList<VehicleType> usableVehicle) {
        this.type = type;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
        this.usableVehicle = usableVehicle;
    }

    public double getFuelConsumptionShip() {
        return fuelConsumptionShip;
    }

    public double getFuelConsumptionTruck() {
        return fuelConsumptionTruck;
    }

    public ArrayList<VehicleType> getUsableVehicle() {
        return usableVehicle;
    }

    @Override
    public String toString() {
        return type;
    }
}
