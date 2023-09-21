package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<String> vehicleIds;
    private ArrayList<String> containerIds;
    private ArrayList<String> ongoingTrafficIds;
    private ArrayList<String> pastTrafficIds;

    public Port() {}

    /**
     *
     * Use ONLY when importing mock data / read from files
     */
    public Port(String id, String name, double xLatitude, double yLongitude, double maxCapacity, boolean isLanding, ArrayList<String> vehicleIds, ArrayList<String> containerIds, ArrayList<String> ongoingTrafficIds, ArrayList<String> pastTrafficIds) {
        this.id = id;
        this.name = name;
        this.xLatitude = xLatitude;
        this.yLongitude = yLongitude;
        this.maxCapacity = maxCapacity;
        this.isLanding = isLanding;
        this.vehicleIds = vehicleIds;
        this.containerIds = containerIds;
        this.ongoingTrafficIds = ongoingTrafficIds;
        this.pastTrafficIds = pastTrafficIds;

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
        this.vehicleIds = new ArrayList<>();
        this.containerIds = new ArrayList<>();
        this.ongoingTrafficIds = new ArrayList<>();
        this.pastTrafficIds = new ArrayList<>();

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
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        for (String id_ : this.vehicleIds) {
            vehicles.add(new Vehicle().findVehicleById(id_));
        }

        return vehicles;
    }

    public ArrayList<Container> getContainers() {
        ArrayList<Container> containers = new ArrayList<>();

        for (String id_ : this.containerIds) {
            containers.add(new Container().findContainerById(id_));
        }

        return containers;
    }

    public ArrayList<TripDetails> getOngoingTraffics() {
        ArrayList<TripDetails> tripDetails = new ArrayList<>();

        for (String id_ : this.ongoingTrafficIds) {
            tripDetails.add(new TripDetails().findTripDetailsById(id_));
        }

        return tripDetails;
    }

    public ArrayList<TripDetails> getPastTraffics() {
        ArrayList<TripDetails> tripDetails = new ArrayList<>();

        for (String id_ : this.pastTrafficIds) {
            tripDetails.add(new TripDetails().findTripDetailsById(id_));
        }

        return tripDetails;
    }

    public Container findContainerInPortById(String id) {
        for (String id_ : this.containerIds) {
            if (id.equals(id_)) return new Container().findContainerById(id);
        }

        return null;
    }

    public Vehicle findVehicleInPortById(String id) {
        for (String id_ : this.vehicleIds) {
            if (id_.equals(id)) return new Vehicle().findVehicleById(id);
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

        for (String id_ : this.vehicleIds) {
            Vehicle vehicle = new Vehicle().findVehicleById(id);
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

        this.containerIds.remove(container.getId());
        return true;
    }

    public void unloadContainerFromVehicle(Vehicle vehicle) {
        this.containerIds.addAll(vehicle.getLoadedContainerIds());
        vehicle.getLoadedContainerIds().clear();
        vehicle.setCurrentCarryLoad(0.0);
    }

    public void startTrip(Vehicle vehicle, Port port) {
        int distance = (int) distanceTo(port);  // temporary implementation, should be rework later
        LocalDate now_ = LocalDate.now();
        TripDetails tripDetails = new TripDetails(now_, now_.plusDays(distance), vehicle.getId(), this.getId(), port.getId());

        this.vehicleIds.remove(vehicle.getId());
        this.ongoingTrafficIds.add(tripDetails.getId());
        port.ongoingTrafficIds.add(tripDetails.getId());
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%.2f|%.2f|%.2f|%d|%s|%s|%s|%s",
                id, name, xLatitude, yLongitude, maxCapacity, isLanding ? 1 : 0, vehicleIds, containerIds, ongoingTrafficIds, pastTrafficIds
        );
    }

    @Override
    public String toString() {
        return id;
    }
}
