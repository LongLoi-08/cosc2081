package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Port {
    /**
     * An {@link java.util.ArrayList ArrayList} of Boolean values used as an unordered list with 1000 elements
     */
    private static ArrayList<Boolean> idCache = new ArrayList<>(Collections.nCopies(1000, false));

    /**
     * An {@link java.util.ArrayList ArrayList} of Port objects used as a record of every object that is alive
     */
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
     *
     * Use ONLY when importing mock data / read from files
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

    /**
     * The main constructor that should be use for creating new objects.
     * @param name Name of the Port
     * @param xLatitude Latitude(X) value
     * @param yLongitude Longitude(Y) value
     * @param maxCapacity The Port's max weight in terms of Containers
     * @param isLanding If the Port is compatible for Ships
     */
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

    /**
     *
     * @return A static {@link java.util.ArrayList ArrayList<>} of ALL Port objects
     */
    public ArrayList<Port> getAllPorts() {
        return ports;
    }

    /**
     *
     * @return an ID String formatted as "p$$$$" (% is an int value ranging from 0 to 9)
     */
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

    public Container findContainerInPortById(String id) {
        for (Container container : this.containers) {
            if (id.equals(container.getId())) return container;
        }

        return null;
    }

    public Vehicle findVehicleInPortById(String id) {
        for (Vehicle vehicle : this.vehicles) {
            if (id.equals(vehicle.getId())) return vehicle;
        }

        return null;
    }

    public Port findPortById(String id) {
        for (Port port : ports) {
            if (id.equals(port.getId())) return port;
        }

        return null;
    }

    public ArrayList<Vehicle> findSuitableVehiclesForContainer(Container container) {
        ArrayList<Vehicle> suitableVehicle = new ArrayList<>();
        for (Vehicle vehicle : this.vehicles) {
            if (container.getUsableVehicle().contains(vehicle.getVehicleType())) suitableVehicle.add(vehicle);
        }
        return suitableVehicle;
    }

    public double distanceTo(Port port) {
        // distance between 2 points can be calculated using the following equation:
        // [(x1 - x2)^2 + (y1 - y2)^2] ^ 0.5
        return Math.sqrt(
                Math.pow(this.xLatitude - port.xLatitude, 2)
                +
                Math.pow(this.yLongitude - port.yLongitude, 2)
        );
    }

    public boolean loadContainerToVehicle(Container container, Vehicle vehicle) {
        if (!container.getUsableVehicle().contains(vehicle.getVehicleType())) return false;
        if (!vehicle.loadContainer(container)) return false;
        this.containers.remove(container);
        return true;
    }

    public void unloadContainerFromVehicle(Vehicle vehicle) {
        this.containers.addAll(vehicle.getLoadedContainers());
        vehicle.getLoadedContainers().clear();
    }

    public void startTrip(Vehicle vehicle, Port port) {
        int distance = (int) distanceTo(port);  // temporary implementation, should be rework later
        LocalDate now_ = LocalDate.now();
        TripDetails tripDetails = new TripDetails(now_, now_.plusDays(distance), vehicle, this, port);

        this.vehicles.remove(vehicle);
        this.ongoingTraffics.add(tripDetails);
        port.ongoingTraffics.add(tripDetails);
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%.2f|%.2f|%.2f|%d|%s|%s|%s|%s",
                id, name, xLatitude, yLongitude, maxCapacity, isLanding ? 1 : 0, vehicles, containers, ongoingTraffics, pastTraffics
        );
    }

    @Override
    public String toString() {
        return id;
    }
}
