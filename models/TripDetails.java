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
    private LocalDate departure;
    private LocalDate arrival;
    private Vehicle vehicle;
    private Port departurePort;
    private Port arrivalPort;
    private String status;

    public TripDetails() {}

    /**
     * For dev purpose only!
     * Use when importing mock data
     */
    public TripDetails(String id, LocalDate departure, LocalDate arrival, Vehicle vehicle, Port departurePort, Port arrivalPort, String status) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;

        int idValue = Integer.parseInt(id.substring(2));
        idCache.set(idValue, true);

        tripDetails.add(this);
    }

    public TripDetails(LocalDate departure, LocalDate arrival, Vehicle vehicle, Port departurePort, Port arrivalPort) {
        this.id = generateId();
        this.departure = departure;
        this.arrival = arrival;
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
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
        return vehicle;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public String getStatus() {
        return status;
    }
}
