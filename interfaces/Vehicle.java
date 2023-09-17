package interfaces;

import models.Container;
import utils.VehicleType;

import java.util.ArrayList;

public interface Vehicle {
    public String getId();

    public String getName();

    public VehicleType getVehicleType();

    public double getCurrentFuel();

    public double getFuelCapacity();

    public double getCarryingCapacity();

    public double getCurrentCarryingLoad();

    public ArrayList<Container> getLoadedContainers();

    public boolean loadContainer(Container container);
}
