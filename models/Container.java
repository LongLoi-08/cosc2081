package models;

import utils.ContainerType;
import utils.VehicleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Container implements interfaces.Container {
    private static ArrayList<Boolean> idCache = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<Container> containers = new ArrayList<>();

    private String id;
    private ContainerType containerType;
    private double weight;


    public Container() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public Container(String id, ContainerType containerType, double weight) {
        this.id = id;
        this.containerType = containerType;
        this.weight = weight;

        // check for id that are inuse when read from file
        int idValue = Integer.parseInt(id.substring(1));
        idCache.set(idValue, true);

        // add obj to a static list
        containers.add(this);
    }

    public Container(ContainerType containerType, double weight) {
        this.id = generateId();
        this.containerType = containerType;
        this.weight = weight;
        containers.add(this);
    }

    public ArrayList<Container> getAllContainer() {
        return containers;
    }

    // custom id generator
    private String generateId() {
        String idFormat = "c%04d";
        int idIndexValue = idCache.indexOf(false);
        String newId = String.format(idFormat, idIndexValue + 1);
        idCache.set(idIndexValue, true);
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

    @Override   // Currently not working, will solve later in the future
    public double getFuelConsumption(VehicleType vehicleType) {
        return containerType.getFuelConsumptionShip() * vehicleType.compareTo(VehicleType.SHIP) + containerType.getFuelConsumptionTruck() * vehicleType.compareTo(VehicleType.TRUCK);
    }

    protected void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    public Container findContainerById(String id) {
        for (Container container : containers) {
            if (id.equals(container.getId())) return container;
        }

        return null;
    }

    public String toStringSaveFileFormat() {
        return String.format("%s|%s|%.2f", id, containerType.toString(), weight);
    }

    @Override
    public String toString() {
        return id;
    }
}
