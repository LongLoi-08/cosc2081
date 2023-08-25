package models;

import utils.VehicleType;

import java.util.ArrayList;

public class Vehicle implements interfaces.Vehicle {
    private static int idCounterT = 0;
    private static int idCounterS = 0;

    private String id;
    private VehicleType vehicleType;
    private double maxFuel;
    private double currentFuel;
    private double maxCarryCapacity;
    private double currentCarryCapacity;
    private ArrayList<Container> containers;
    private Port currentPort;   ////////////////////////////////////////

    public Vehicle() {}

    public Vehicle(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, Port port) {
        this.id = generateId(vehicleType);
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = maxFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryCapacity = 0;
        this.containers = new ArrayList<>();
        this.currentPort = port;
    }

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public Vehicle(String id, VehicleType vehicleType, double maxFuel, double currentFuel, double maxCarryCapacity, double currentCarryCapacity, ArrayList<Container> containers, Port currentPort) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = currentFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryCapacity = currentCarryCapacity;
        this.containers = containers;
        this.currentPort = currentPort;
    }

    private String generateId(VehicleType vehicleType) {
        String idFormatT = "tr%04d";
        String idFormatS = "sh%04d";
        String newId;
        if (vehicleType.equals(VehicleType.SHIP)) {
            newId = String.format(idFormatS, idCounterS);
            idCounterS++;
        }
        else {
            newId = String.format(idFormatT, idCounterT);
            idCounterT++;
        }

        return newId;
    }

    private boolean isLoadable(Container container) {
        return container.getWeight() <= (maxCarryCapacity - currentCarryCapacity);
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
    public double getCurrentCarryingCapacity() {
        return currentCarryCapacity;
    }

    @Override
    public ArrayList<Container> getLoadedContainers() {
        return containers;
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
}
