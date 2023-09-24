package interfaces;

import models.Container;
import models.TripDetails;
import models.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;

public interface Port {
    public String getId();
    public String getName();

    public double[] getCoordinate();
    public double getMaxCapacity();
    public boolean isLanding();
    public ArrayList<String> getVehicleIds();

    public ArrayList<String> getContainerIds();
    public ArrayList<String> getOngoingTrafficIds();

    public ArrayList<String> getPastTrafficIds();

    public ArrayList<models.Vehicle> getVehicles();

    public ArrayList<models.Container> getContainers();

    public ArrayList<models.TripDetails> getOngoingTraffics();

    public ArrayList<models.TripDetails> getPastTraffics();

    public models.Container findContainerInPortById(String id);

    public models.Vehicle findVehicleInPortById(String id);

    public models.Port findPortById(String id);




    public ArrayList<models.Vehicle> findSuitableVehiclesForContainer(models.Container container);

    public double distanceTo(models.Port port);


    public void unloadContainerFromVehicle(models.Vehicle vehicle);

    public void startTrip(Vehicle vehicle, models.Port port);

    public String toStringSaveFileFormat();


    public String toStringDisplayFormat();

    @Override
    public String toString();
}



