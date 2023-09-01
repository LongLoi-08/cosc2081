package models;

import utils.VehicleType;

import java.util.ArrayList;
import java.util.Collections;

public class Port {
    private static ArrayList<Boolean> idCache = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<Port> ports = new ArrayList<>();

    private String id;
    private String name;
    private double xLatitude;
    private double yLongitude;
    private double maxCapacity;
    private boolean isLanding;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Container> containers;
    private ArrayList<TripDetails> ongoingTraffics;
    private ArrayList<TripDetails> pastTraffics;

    public Port() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public Port(String id, String name, double xLatitude, double yLongitude, double maxCapacity, boolean isLanding, ArrayList<Vehicle> vehicles, ArrayList<Container> containers, ArrayList<TripDetails> ongoingTraffics, ArrayList<TripDetails> pastTraffics) {
        this.id = id;
        this.name = name;
        this.xLatitude = xLatitude;
        this.yLongitude = yLongitude;
        this.maxCapacity = maxCapacity;
        this.isLanding = isLanding;
        this.vehicles = vehicles;
        this.containers = containers;
        this.ongoingTraffics = ongoingTraffics;
        this.pastTraffics = pastTraffics;

        int idValue = Integer.parseInt(id.substring(1));
        idCache.set(idValue, true);

        ports.add(this);
    }

    public Port(String name, double xLatitude, double yLongitude, double maxCapacity, boolean isLanding) {
        this.id = generateId();
        this.name = name;
        this.xLatitude = xLatitude;
        this.yLongitude = yLongitude;
        this.maxCapacity = maxCapacity;
        this.isLanding = isLanding;
        this.vehicles = new ArrayList<>();
        this.containers = new ArrayList<>();
        this.ongoingTraffics = new ArrayList<>();
        this.pastTraffics = new ArrayList<>();

        ports.add(this);
    }

    private String generateId() {
        String idFormat = "p%04d";
        int idIndexValue = idCache.indexOf(false);
        String newId = String.format(idFormat, idIndexValue + 1);
        idCache.set(idIndexValue, true);
        return newId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double[] getCoordinate() {
        return new double[]{xLatitude, yLongitude};
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<TripDetails> getOngoingTraffics() {
        return ongoingTraffics;
    }

    public ArrayList<TripDetails> getPastTraffics() {
        return pastTraffics;
    }

    @Override
    public String toString() {
        return String.format(
                "%s|%s|%.2f|%.2f|%.2f|%d|%s|%s|%s|%s",
                id, name, xLatitude, yLongitude, maxCapacity, isLanding ? 1 : 0, vehicles, containers, ongoingTraffics, pastTraffics
        );
    }
}
