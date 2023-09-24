package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class TripDetails {
    public static String STATUS_ON_GOING = "ON GOING";
    public static String STATUS_DELIVERED = "DELIVERED";
//    public static String STATUS_CANCELED = "CANCELED";

    private static ArrayList<Boolean> idCache = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<TripDetails> tripDetails = new ArrayList<>();

    private String id;
    private LocalDate departure;    // yyyy-mm-dd
    private LocalDate arrival;
    private String vehicleId;
    private String departurePortId;
    private String arrivalPortId;
    private String status;

    public TripDetails() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public TripDetails(String id, LocalDate departure, LocalDate arrival, String vehicleId, String departurePortId, String arrivalPortId, String status) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.vehicleId = vehicleId;
        this.departurePortId = departurePortId;
        this.arrivalPortId = arrivalPortId;
        this.status = status;

        new Port().findPortById(departurePortId).addTripDetails(this.id);
        new Port().findPortById(arrivalPortId).addTripDetails(this.id);

        int idValue = Integer.parseInt(id.substring(2));
        idCache.set(idValue, true);

        tripDetails.add(this);
    }

    public TripDetails(LocalDate departure, LocalDate arrival, String vehicleId, String departurePortId, String arrivalPortId) {
        this.id = generateId();
        this.departure = departure;
        this.arrival = arrival;
        this.vehicleId = vehicleId;
        this.departurePortId = departurePortId;
        this.arrivalPortId = arrivalPortId;
        this.status = STATUS_ON_GOING;

        tripDetails.add(this);
    }

    public ArrayList<TripDetails> getAllTripDetails() {
        return tripDetails;
    }

    private String generateId() {
        String idFormat = "td%04d";
        int idIndexValue = idCache.indexOf(false);
        String newId = String.format(idFormat, idIndexValue + 1);
        idCache.set(idIndexValue, true);
        return newId;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public Vehicle getVehicle() {
        return new Vehicle().findVehicleById(this.vehicleId);
    }

    public Port getDeparturePort() {
        return new Port().findPortById(departurePortId);
    }

    public Port getArrivalPort() {
        return new Port().findPortById(arrivalPortId);
    }

    public String getStatus() {
        return status;
    }

    public TripDetails findTripDetailsById(String id) {
        for (TripDetails td : tripDetails) {
            if (td.getId().equals(id)) return td;
        }

        return null;
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%s|%s|%s|%s|%s|",
                id, departure.toString(), arrival.toString(), vehicleId, departurePortId, arrivalPortId, status
        );
    }

    public String toStringDisplayFormat() {
        return "TripDetails{" +
                "\n     id='" + id + '\'' +
                ", \n     departure=" + departure +
                ", \n     arrival=" + arrival +
                ", \n     vehicleId='" + vehicleId + '\'' +
                ", \n     departurePortId='" + departurePortId + '\'' +
                ", \n     arrivalPortId='" + arrivalPortId + '\'' +
                ", \n     status='" + status + '\'' +
                "\n}";
    }

    @Override
    public String toString() {
        return id;
    }
}
