package interfaces;

import utils.ContainerType;
import utils.VehicleType;

import java.util.ArrayList;

public interface Container {
    public String getId();

    public ContainerType getContainerType();

    public double getWeight();

    public double getFuelConsumption(VehicleType vehicleType);
    public ArrayList<VehicleType> getUsableVehicle();
    public models.Container findContainerById(String id);

    public String toStringSaveFileFormat();

    public String toStringDisplayFormat();

    @Override
    public String toString();
}


