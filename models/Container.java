package models;

import utils.ContainerType;
import utils.VehicleType;

import java.util.ArrayList;

public class Container implements interfaces.Container {
    private static int idCounter = 0;

    private String id;
    private ContainerType containerType;
    private double weight;

    public Container() {}

    protected Container(ContainerType containerType, double weight) {
        this.id = generateId();
        this.containerType = containerType;
        this.weight = weight;
    }

    // custom id incrementer
    private String generateId() {
        String idFormat = "c%04d";
        String newId = String.format(idFormat, idCounter);
        idCounter++;
        return newId;
    }

    public ArrayList<VehicleType> getUsableVehicle() {
        return this.containerType.getUsableVehicle();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ContainerType getContainerType() {
        return containerType;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getFuelConsumption(VehicleType vehicleType) {
        return containerType.getFuelConsumptionShip() * vehicleType.compareTo(VehicleType.SHIP) + containerType.getFuelConsumptionTruck() * vehicleType.compareTo(VehicleType.TRUCK);
    }
}
