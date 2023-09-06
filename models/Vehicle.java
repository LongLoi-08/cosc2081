package models;

import utils.VehicleType;

import java.util.ArrayList;
import java.util.Collections;

public class Vehicle implements interfaces.Vehicle {
    private static ArrayList<Boolean> idCacheT = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<Boolean> idCacheS = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();

    private String id;
    private VehicleType vehicleType;
    private double maxFuel;
    private double currentFuel;
    private double maxCarryCapacity;
    private double currentCarryLoad;
    private ArrayList<Container> containers;
    private Port currentPort;

    public Vehicle() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public Vehicle(String id, VehicleType vehicleType, double maxFuel, double currentFuel, double maxCarryCapacity, double currentCarryWeight, ArrayList<Container> containers, Port currentPort) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = currentFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryLoad = currentCarryWeight;
        this.containers = containers;
        this.currentPort = currentPort;

        int idValue = Integer.parseInt(id.substring(2));
        if (vehicleType == VehicleType.SHIP) {
            idCacheS.set(idValue, true);
        } else {
            idCacheT.set(idValue, true);
        }

        vehicles.add(this);
    }

    public Vehicle(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, Port port) {
        this.id = generateId(vehicleType);
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = maxFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryLoad = 0;
        this.containers = new ArrayList<>();
        this.currentPort = port;

        vehicles.add(this);
    }

    public static ArrayList<Vehicle> getAllVehicles() {
        return vehicles;
    }

    private String generateId(VehicleType vehicleType) {
        String idFormatT = "tr%04d", idFormatS = "sh%04d";
        int idIndexValue;
        String newId;

        if (vehicleType.equals(VehicleType.SHIP)) {
            idIndexValue = idCacheS.indexOf(false);
            newId = String.format(idFormatS, idIndexValue + 1);
            idCacheS.set(idIndexValue, true);
        } else {
            idIndexValue = idCacheT.indexOf(false);
            newId = String.format(idFormatT, idIndexValue + 1);
            idCacheT.set(idIndexValue, true);
        }

        return newId;
    }

    private boolean isLoadable(Container container) {
        return container.getWeight() <= (maxCarryCapacity - currentCarryLoad);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return vehicleType.toString();
    }

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public double getCurrentFuel() {
        return currentFuel;
    }

    @Override
    public double getFuelCapacity() {
        return maxFuel;
    }

    @Override
    public double getCarryingCapacity() {
        return maxCarryCapacity;
    }

    @Override
    public double getCurrentCarryingLoad() {
        return currentCarryLoad;
    }

    @Override
    public ArrayList<Container> getLoadedContainers() {
        return containers;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    @Override
    public Vehicle loadContainer(Container container) {
        // weight capacity validation here
        if (isLoadable(container)) {
            containers.add(container);
            System.out.println("Load successful!");
            return this;
        }
        System.out.println("Over carrying capacity alert!");
        return this;
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%.2f|%.2f|%.2f|%.2f|%s|%s",
                id, vehicleType.toString(), maxFuel, currentFuel, maxCarryCapacity, currentCarryLoad, containers, currentPort.getId()
        );
    }

    @Override
    public String toString() {
        return id;
    }
}
