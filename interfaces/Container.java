package interfaces;

import utils.ContainerType;
import utils.VehicleType;

public interface Container {
    public String getId();

    public ContainerType getContainerType();

    public double getWeight();

    public double getFuelConsumption(VehicleType vehicleType);
}
