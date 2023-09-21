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
    private ArrayList<String> containerIds;
    private String currentPortId;

    public Vehicle() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public Vehicle(String id, VehicleType vehicleType, double maxFuel, double currentFuel, double maxCarryCapacity, double currentCarryWeight, ArrayList<String> containerIds, String currentPortId) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = currentFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryLoad = currentCarryWeight;
        this.containerIds = containerIds;
        this.currentPortId = currentPortId;

        int idValue = Integer.parseInt(id.substring(2));
        if (vehicleType == VehicleType.SHIP) {
            idCacheS.set(idValue, true);
        } else {
            idCacheT.set(idValue, true);
        }

        vehicles.add(this);
    }

    public Vehicle(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, String currentPortId) {
        this.id = generateId(vehicleType);
        this.vehicleType = vehicleType;
        this.maxFuel = maxFuel;
        this.currentFuel = maxFuel;
        this.maxCarryCapacity = maxCarryCapacity;
        this.currentCarryLoad = 0;
        this.containerIds = new ArrayList<>();
        this.currentPortId = currentPortId;

        vehicles.add(this);
    }

    public ArrayList<Vehicle> getAllVehicles() {
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
    public ArrayList<String> getLoadedContainerIds() {
        return containerIds;
    }

    public String getCurrentPortId() {
        return currentPortId;
    }

    public Port getCurrentPort() {
        return new Port().findPortById(this.currentPortId);
    }

    protected void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    protected void setMaxFuel(double maxFuel) {
        this.maxFuel = maxFuel;
    }

    protected void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    protected void setMaxCarryCapacity(double maxCarryCapacity) {
        this.maxCarryCapacity = maxCarryCapacity;
    }

    protected void setCurrentCarryLoad(double currentCarryLoad) {
        this.currentCarryLoad = currentCarryLoad;
    }

    protected void setContainers(ArrayList<String> containerIds) {
        this.containerIds = containerIds;
    }

    protected void setCurrentPort(String currentPortId) {
        this.currentPortId = currentPortId;
    }

    public Vehicle findVehicleById(String id) {
        for (Vehicle vehicle : vehicles) {
            if (id.equals(vehicle.getId())) return vehicle;
        }

        return null;
    }

    @Override
    public boolean loadContainer(Container container) {
        // weight capacity validation here
        if (!isLoadable(container)) return false;
        containerIds.add(container.getId());
        this.currentCarryLoad += container.getWeight();
        return true;
    }

    public boolean unloadContainer(Container container) {
        if (this.currentCarryLoad == 0.0) return false;
        containerIds.remove(container.getId());
        this.currentCarryLoad -= container.getWeight();
        return true;
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%.2f|%.2f|%.2f|%.2f|%s|%s",
                id, vehicleType.toString(), maxFuel, currentFuel, maxCarryCapacity, currentCarryLoad, containerIds, currentPortId
        );
    }

    @Override
    public String toString() {
        return id;
    }
}
