package models;

import utils.ContainerType;
import utils.VehicleType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileIO {
    public final String SAVE_LOCATION = System.getProperty("user.dir")  + "\\data";
    public final String CONTAINER_FILE_NAME = "container.txt";
    public final String VEHICLE_FILE_NAME = "vehicle.txt";
    public final String PORT_FILE_NAME = "port.txt";
    public final String TRIP_DETAILS_FILE_NAME = "trip_details.txt";
    public final String USER_FILE_NAME = "user.txt";

    public FileIO() {}

    public boolean saveContainers() {
        ArrayList<Container> containers = new  Container().getAllContainer();

        File file = new File(SAVE_LOCATION + "\\" + CONTAINER_FILE_NAME);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Container container : containers) {
                bufferedWriter.write(container.toStringSaveFileFormat());
                bufferedWriter.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveVehicles() {
        ArrayList<Vehicle> vehicles = new  Vehicle().getAllVehicles();

        File file = new File(SAVE_LOCATION + "\\" + VEHICLE_FILE_NAME);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Vehicle vehicle : vehicles) {
                bufferedWriter.write(vehicle.toStringSaveFileFormat());
                bufferedWriter.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean savePorts() {
        ArrayList<Port> ports = new Port().getAllPorts();

        File file = new File(SAVE_LOCATION + "\\" + PORT_FILE_NAME);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Port port : ports) {
                bufferedWriter.write(port.toStringSaveFileFormat());
                bufferedWriter.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveTripDetails() {
        ArrayList<TripDetails> tripDetails = new TripDetails().getAllTripDetails();

        File file = new File(SAVE_LOCATION + "\\" + TRIP_DETAILS_FILE_NAME);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (TripDetails tripDetails_ : tripDetails) {
                bufferedWriter.write(tripDetails_.toStringSaveFileFormat());
                bufferedWriter.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveUsers() {
        ArrayList<User> users = new User().getAllUsers();

        File file = new File(SAVE_LOCATION + "\\" + USER_FILE_NAME);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                bufferedWriter.write(user.toStringSaveFileFormat());
                bufferedWriter.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected boolean loadUsersFromFile() {
        File file = new File(SAVE_LOCATION + "\\" + USER_FILE_NAME);

        if (!file.exists()) return false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String username = parts[0];
                String password = parts[1];
                boolean isAdmin = parts[2].equals("1");
                String portId = parts[3];

                new User(username, password, isAdmin, portId);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected boolean loadContainersFromFile() {
        File file = new File(SAVE_LOCATION + "\\" + CONTAINER_FILE_NAME);

        if (!file.exists()) return false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                ContainerType containerType = ContainerType.getType(parts[1]);
                double weight = Double.parseDouble(parts[2]);

                Container container = new Container(id, containerType, weight);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected boolean loadVehicleFromFile() {
        File file = new File(SAVE_LOCATION + "\\" + VEHICLE_FILE_NAME);

        if (!file.exists()) return false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                VehicleType vehicleType = VehicleType.getType(parts[1]);
                double maxFuel = Double.parseDouble(parts[2]);
                double currentFuel = Double.parseDouble(parts[3]);
                double maxCarryingCapacity = Double.parseDouble(parts[4]);
                double currentCarryingLoad = Double.parseDouble(parts[5]);

                ArrayList<String> cId = new ArrayList<>(Arrays.asList(parts[6].substring(1, parts[6].length() - 1).split(", ")));

                String portId = parts[7];

                new Vehicle(id, vehicleType, maxFuel, currentFuel, maxCarryingCapacity, currentCarryingLoad, cId, portId);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean loadPortsFromFile() {
        File file = new File(SAVE_LOCATION + "\\" + PORT_FILE_NAME);

        if (!file.exists()) return false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String name = parts[1];
                double xLatitude = Double.parseDouble(parts[2]);
                double yLongitude = Double.parseDouble(parts[3]);
                double maxCapacity = Double.parseDouble(parts[4]);
                boolean isLanding = parts[5].equals("1");

                ArrayList<String> vId = new ArrayList<>(Arrays.asList(parts[6].substring(1, parts[6].length() - 1).split(", ")));
                ArrayList<String> cId = new ArrayList<>(Arrays.asList(parts[7].substring(1, parts[7].length() - 1).split(", ")));
                ArrayList<String> oTDId = new ArrayList<>(Arrays.asList(parts[8].substring(2, parts[8].length() - 1).split(", ")));
                ArrayList<String> pTDId = new ArrayList<>(Arrays.asList(parts[9].substring(2, parts[9].length() - 1).split(", ")));

                // move the trips that finish to the past array
                for (String id_ : oTDId) {
                    TripDetails tripDetails = new TripDetails().findTripDetailsById(id_);
                    if (LocalDate.now().isAfter(tripDetails.getArrival())) {
                        oTDId.remove(id_);
                        pTDId.add(id_);
                    }
                }

                // delete trips that has arrived 7days or more
                for (String id_ : pTDId) {
                    TripDetails tripDetails = new TripDetails().findTripDetailsById(id_);
                    if (LocalDate.now().isAfter(tripDetails.getArrival().plusDays(7))) pTDId.remove(id_);
                }

                new Port(id, name, xLatitude, yLongitude, maxCapacity, isLanding, vId, cId, oTDId, pTDId);
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected boolean loadTripsDetailsFromFile() {
        File file = new File(SAVE_LOCATION + "\\" + TRIP_DETAILS_FILE_NAME);

        if (!file.exists()) return false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                LocalDate departure = LocalDate.parse(parts[1]);
                LocalDate arrival = LocalDate.parse(parts[2]);
                String vehicleId = parts[3];
                String departurePortId = parts[4];
                String arrivalPortId = parts[5];
                String status = parts[6];

                // only create if arrival is less than 7days
                if (LocalDate.now().isBefore(arrival.plusDays(7))) new TripDetails(id, departure, arrival, vehicleId, departurePortId, arrivalPortId, status);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void generate5OPorts() {
        new Port("Port 0", 0, 0, 5000, true);
        new Port("Port 1", 0, 100, 5000, false);
        new Port("Port 2", 100, 100, 5000, true);
        new Port("Port 3", 100, 0, 5000, false);
        new Port("Port 4", 100, -100, 5000, true);
    }

    private void generate30Containers() {
        new Container(ContainerType.OPEN_TOP, 10);
        new Container(ContainerType.OPEN_TOP, 10);
        new Container(ContainerType.OPEN_TOP, 10);
        new Container(ContainerType.OPEN_TOP, 10);
        new Container(ContainerType.OPEN_TOP, 10);
        new Container(ContainerType.OPEN_TOP, 10);

        new Container(ContainerType.LIQUID, 20);
        new Container(ContainerType.LIQUID, 20);
        new Container(ContainerType.LIQUID, 20);
        new Container(ContainerType.LIQUID, 20);
        new Container(ContainerType.LIQUID, 20);
        new Container(ContainerType.LIQUID, 20);

        new Container(ContainerType.DRY_STORAGE, 30);
        new Container(ContainerType.DRY_STORAGE, 30);
        new Container(ContainerType.DRY_STORAGE, 30);
        new Container(ContainerType.DRY_STORAGE, 30);
        new Container(ContainerType.DRY_STORAGE, 30);
        new Container(ContainerType.DRY_STORAGE, 30);

        new Container(ContainerType.OPEN_SIDE, 40);
        new Container(ContainerType.OPEN_SIDE, 40);
        new Container(ContainerType.OPEN_SIDE, 40);
        new Container(ContainerType.OPEN_SIDE, 40);
        new Container(ContainerType.OPEN_SIDE, 40);
        new Container(ContainerType.OPEN_SIDE, 40);

        new Container(ContainerType.REFRIGERATED, 50);
        new Container(ContainerType.REFRIGERATED, 50);
        new Container(ContainerType.REFRIGERATED, 50);
        new Container(ContainerType.REFRIGERATED, 50);
        new Container(ContainerType.REFRIGERATED, 50);
        new Container(ContainerType.REFRIGERATED, 50);
    }

    private void generate25Vehicles() {
        ArrayList<Port> ports = new Port().getAllPorts();

        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(0).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(1).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(2).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(3).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(4).getId());

        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(0).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(1).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(2).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(3).getId());
        new Vehicle(VehicleType.TRUCK, 1000, 500, ports.get(4).getId());

        new Vehicle(VehicleType.REEFER_TRUCK, 1000, 500, ports.get(0).getId());
        new Vehicle(VehicleType.REEFER_TRUCK, 1000, 500, ports.get(1).getId());
        new Vehicle(VehicleType.REEFER_TRUCK, 1000, 500, ports.get(2).getId());
        new Vehicle(VehicleType.REEFER_TRUCK, 1000, 500, ports.get(3).getId());
        new Vehicle(VehicleType.REEFER_TRUCK, 1000, 500, ports.get(4).getId());

        new Vehicle(VehicleType.TANKER_TRUCK, 1000, 500, ports.get(0).getId());
        new Vehicle(VehicleType.TANKER_TRUCK, 1000, 500, ports.get(1).getId());
        new Vehicle(VehicleType.TANKER_TRUCK, 1000, 500, ports.get(2).getId());
        new Vehicle(VehicleType.TANKER_TRUCK, 1000, 500, ports.get(3).getId());
        new Vehicle(VehicleType.TANKER_TRUCK, 1000, 500, ports.get(4).getId());

        new Vehicle(VehicleType.SHIP, 3000, 500, ports.get(0).getId());
        new Vehicle(VehicleType.SHIP, 3000, 500, ports.get(1).getId());
        new Vehicle(VehicleType.SHIP, 3000, 500, ports.get(2).getId());
        new Vehicle(VehicleType.SHIP, 3000, 500, ports.get(3).getId());
        new Vehicle(VehicleType.SHIP, 3000, 500, ports.get(4).getId());

        ArrayList<Container> containers = new Container().getAllContainer();

        for (int indexContainer = 0; indexContainer <= 5; indexContainer++) {
            for (int indexPort = 0; indexPort < 5; indexPort++) {
                ports.get(indexPort).addContainerToPort(containers.get(indexContainer).getId());
                ports.get(indexPort).addContainerToPort(containers.get(indexContainer + 6).getId());
                ports.get(indexPort).addContainerToPort(containers.get(indexContainer + (6 * 2)).getId());
                ports.get(indexPort).addContainerToPort(containers.get(indexContainer + (6 * 3)).getId());
                ports.get(indexPort).addContainerToPort(containers.get(indexContainer + (6 * 4)).getId());
            }
        }
    }

    private void mockFLoadC2V(Port port) {
        for (int i = 0; i < 5; i++) {
            port.loadContainerToVehicle(port.getContainers().get(0), port.getVehicles().get(i));
        }
    }

    private void mockFStartTrips(Port port) {
        for (Port destPort : new Port().getAllPorts()) {
            if (!port.getId().equals(destPort.getId())) port.startTrip(port.getVehicles().get(0), destPort);
        }
    }

    private void generate25TripDetails() {
        ArrayList<Port> ports = new Port().getAllPorts();

        Port p1 = ports.get(0);
        Port p2 = ports.get(1);
        Port p3 = ports.get(2);
        Port p4 = ports.get(3);
        Port p5 = ports.get(4);

        mockFLoadC2V(p1);
        mockFLoadC2V(p2);
        mockFLoadC2V(p3);
        mockFLoadC2V(p4);
        mockFLoadC2V(p5);

        mockFStartTrips(p1);
        mockFStartTrips(p2);
        mockFStartTrips(p3);
        mockFStartTrips(p4);
        mockFStartTrips(p5);
    }

    private void mockFGenerate5Manager() {
        int i = 0;
        for (Port port : new Port().getAllPorts()) {
            User manager = new User("manager" + String.valueOf(i), "123").setAsManager(port);
            i++;
        }
    }

    private void generateUsers() {
        mockFGenerate5Manager();
        User admin_ = new User("admin", "123").setAsAdmin();
    }

    public void generateMockData() {
        generate5OPorts();
        generate30Containers();
        generate25Vehicles();
        generate25TripDetails();
        generateUsers();
    }
}
