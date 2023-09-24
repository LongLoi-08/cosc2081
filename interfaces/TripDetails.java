package interfaces;


import java.time.LocalDate;

public interface TripDetails {
    public String getId();

    public LocalDate getDeparture();

    public LocalDate getArrival();

    public models.Vehicle getVehicle();
    public models.Port getDeparturePort();

    public models.Port getArrivalPort();

    public String getStatus();

    public models.TripDetails findTripDetailsById(String id);



    public String toStringSaveFileFormat();
    public String toStringDisplayFormat();

    @Override
    public String toString();
}









