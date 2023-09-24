package models;
import utils.ContainerType;
import utils.CustomUtils;
import utils.VehicleType;

import java.util.Scanner;

//import static models.Port.ports;

public class PortManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FileIO fileIO = new FileIO();


    private static void portCreate() {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter latitude: ");
        String latitude = scanner.nextLine();

        double Latitude = 0.0;

        if (CustomUtils.isDouble(latitude)){
            Latitude = Double.parseDouble(latitude);
        } else {
            System.out.println("Invalid input");
        }

        System.out.println("Enter longitude: ");
        String longitude = scanner.nextLine();

        double Longitude = 0.0;

        if (CustomUtils.isDouble(longitude)){
            Longitude = Double.parseDouble(longitude);
        } else {
            System.out.println("Invalid input");
        }

        System.out.println("Enter max capacity: ");
        String maxCap = scanner.nextLine();

        double MaxCap = 0.0;

        if (CustomUtils.isDouble(maxCap)){
            MaxCap = Double.parseDouble(maxCap);
        } else {
            System.out.println("Invalid input");
        }

        System.out.println("Enter is landing ([true] / [false]): ");
        String isLanding = scanner.nextLine();

        boolean IsLanding = false;

        if (CustomUtils.isBoolean(isLanding)) {
            IsLanding = Boolean.parseBoolean(isLanding);
        } else {
            System.out.println("Invalid input");
        }

        new Port(name, Longitude, Latitude, MaxCap, IsLanding);
    }

    private static void portUpdate(String id) {
        Port updatePort = new Port().findPortById(id);

        if (updatePort == null) {
            System.out.println("Port not found with ID: " + id);
            return;
        }

        System.out.println("Enter name: ");
        String name = scanner.nextLine();

        System.out.println("Enter latitude: ");
        String latitude = scanner.nextLine();
        if (!CustomUtils.isDouble(latitude)){
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter longitude: ");
        String longitude = scanner.nextLine();
        if (!CustomUtils.isDouble(longitude)){
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter max capacity: ");
        String maxCap = scanner.nextLine();
        if (!CustomUtils.isDouble(maxCap)){
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter is landing: ");
        String isLanding = scanner.nextLine();
        if (!CustomUtils.isBoolean(isLanding)) {
            System.out.println("Invalid input, please try again...");
            return;
        }

        updatePort.setName(name);
        updatePort.setLatitude(Double.valueOf(latitude));
        updatePort.setLongitude(Double.valueOf(longitude));
        updatePort.setMaxCapacity(Double.valueOf(maxCap));
        updatePort.setIsLanding(Boolean.parseBoolean(isLanding));
    }

    private static void portDelete(String id) {
        Port portToDelete = new Port().findPortById(id);

        if (portToDelete == null) {
            System.out.println("Port not found with ID: " + id);
            return;
        }

        System.out.println("Are you sure you want to delete this port? ([yes] / [no])");
        System.out.println(portToDelete.toStringSaveFileFormat());
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("no")) {
            System.out.println("Deletion canceled.");
            return;
        }

        if (confirmation.equals("yes")) {
            if (portToDelete.getContainers().size() > 0 || portToDelete.getVehicles().size() > 0) {
                System.out.println("Deletion canceled. Port still has containers and vehicle in operations!");
                return;
            }

            new Port().getAllPorts().remove(portToDelete);
            System.out.println("Port deleted successfully.");
            return;
        }

        System.out.println("Invalid input! Deletion canceled");
    }

    private static void portCRUD(User user) {
        if (user.isAdmin()) {
            System.out.println("""
                    [1] - Create
                    [2] - Update
                    [3] - Delete
                    [0] - Quit""");

            System.out.println("Enter option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "0" -> {}

                case "1" -> portCreate();

                case "2" -> {
                    System.out.println("Enter the port ID you want to make change: ");
                    String portId = scanner.nextLine();
                    portUpdate(portId);
                }

                case "3" -> {
                    System.out.println("Enter the port ID you want to delete: ");
                    String portId = scanner.nextLine();
                    portDelete(portId);
                }

                default -> System.out.println("Error! Invalid input option.");
            }

            fileIO.savePorts();
        }
    }


    private static VehicleType getVehicleType(String input) {
        if (input.equals("1")) return VehicleType.SHIP;
        if (input.equals("2")) return VehicleType.TRUCK;
        if (input.equals("3")) return VehicleType.REEFER_TRUCK;
        if (input.equals("4")) return VehicleType.TANKER_TRUCK;
        return null;
    }

    private static void vehicleCreate(User user) {
        System.out.println("[1] - Ship");
        System.out.println("[2] - Truck");
        System.out.println("[3] - Refrigerated Truck");
        System.out.println("[4] - Tanker Truck");

        System.out.println("Enter vehicle choice: ");

        VehicleType type = getVehicleType(scanner.nextLine());

        if (type == null) {
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter max fuel: ");
        String maxFuel = scanner.next();

        if (!CustomUtils.isDouble(maxFuel)) {
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter max carry capacity: ");
        String maxCarryCap = scanner.next();

        if (CustomUtils.isDouble(maxCarryCap)){
            System.out.println("Invalid input, please try again...");
            return;
        }

        if (user.isAdmin()) {
            displayPort(user);

            System.out.println("Enter port ID: ");
            String portId = scanner.next();

            if (new Port().findPortById(portId) == null) {
                System.out.println("Invalid input, please try again...");
                return;
            }

            new Vehicle(type, Double.parseDouble(maxFuel), Double.parseDouble(maxCarryCap), portId);
        } else {
            new Vehicle(type, Double.parseDouble(maxFuel), Double.parseDouble(maxCarryCap), user.getManagingPort().getId());
        }
    }

    private static void vehicleUpdate(String id, User user) {
        Vehicle updateVehicle = user.isAdmin() ? new Vehicle().findVehicleById(id) : user.getManagingPort().findVehicleInPortById(id);

        if (updateVehicle == null) {
            System.out.println("Vehicle not found with ID: " + id);
            return;
        }

        System.out.println("[1] - Ship");
        System.out.println("[2] - Truck");
        System.out.println("[3] - Refrigerated Truck");
        System.out.println("[4] - Tanker Truck");

        System.out.println("Enter vehicle choice: ");
        VehicleType type = getVehicleType(scanner.nextLine());


        System.out.println("Enter max fuel: ");
        String maxFuel = scanner.next();
        if (!CustomUtils.isDouble(maxFuel)){
            System.out.println("Invalid input, please try again...");
            return;
        }
        if (updateVehicle.getFuelCapacity() > Double.parseDouble(maxFuel)) {
            System.out.println("Invalid input: the current value is higher then the inputted, please try again...");
            return;
        }


        System.out.println("Enter max carry capacity: ");
        String maxCarryCap = scanner.next();
        if (!CustomUtils.isDouble(maxCarryCap)){
            System.out.println("Invalid input, please try again...");
            return;
        }
        if (updateVehicle.getCarryingCapacity() > Double.parseDouble(maxCarryCap)) {
            System.out.println("Invalid input: the current value is higher then the inputted, please try again...");
            return;
        }


        if (user.isAdmin()) {
            displayPort(user);

            System.out.println("Enter port ID: ");
            String portId = scanner.next();

            if (new Port().findPortById(portId) != null) {
                updateVehicle.setVehicleType(type);
                updateVehicle.setMaxFuel(Double.parseDouble(maxFuel));
                updateVehicle.setMaxCarryCapacity(Double.parseDouble(maxCarryCap));
                updateVehicle.setCurrentPort(portId);
            }
        } else {
            updateVehicle.setVehicleType(type);
            updateVehicle.setMaxFuel(Double.parseDouble(maxFuel));
            updateVehicle.setMaxCarryCapacity(Double.parseDouble(maxCarryCap));
            updateVehicle.setCurrentPort(user.getManagingPort().getId());
        }
    }

    private static void vehicleDelete(String id, User user) {
        Vehicle vehicleToDelete = user.isAdmin() ? new Vehicle().findVehicleById(id) : user.getManagingPort().findVehicleInPortById(id);

        if (vehicleToDelete == null) {
            System.out.println("Vehicle not found with ID: " + id);
            return;
        }

        System.out.println("Are you sure you want to delete this vehicle? ([yes] / [no])");
        System.out.println(vehicleToDelete.toStringSaveFileFormat());
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("no")) {
            System.out.println("Deletion canceled.");
            return;
        }

        if (confirmation.equals("yes")) {
            if (vehicleToDelete.getLoadedContainerIds().size() > 0) {
                System.out.println("Deletion canceled. Vehicle still has containers!");
                return;
            }

            new Vehicle().getAllVehicles().remove(vehicleToDelete);
            System.out.println("Vehicle deleted successfully.");
            return;
        }

        System.out.println("Invalid input! Deletion canceled");
    }

    private static void vehicleCRUD(User user) {
        System.out.println("""
                        [1] - Create
                        [2] - Update
                        [3] - Delete
                        [0] - Quit""");

        System.out.println("Enter option: ");
        String input = scanner.nextLine();

        switch (input) {
            case "0" -> {}

            case "1" -> vehicleCreate(user);

            case "2" -> {
                System.out.println("Enter the vehicle ID you want to make change: ");
                String vehicleId = scanner.nextLine();
                vehicleUpdate(vehicleId, user);

            }

            case "3" -> {
                System.out.println("Enter the vehicle ID you want to delete: ");
                String vehicleId = scanner.nextLine();
                vehicleDelete(vehicleId, user);
            }

            default -> System.out.println("Error! Invalid input option.");
        }

        fileIO.saveVehicles();
    }


    private static ContainerType getContainerType(String input) {
        if (input.equals("1")) return ContainerType.DRY_STORAGE;
        if (input.equals("2")) return ContainerType.OPEN_TOP;
        if (input.equals("3")) return ContainerType.OPEN_SIDE;
        if (input.equals("4")) return ContainerType.REFRIGERATED;
        if (input.equals("5")) return ContainerType.LIQUID;
        return null;
    }

    private static void containerCreate(User user) {
        System.out.println("[1] - DRY STORAGE");
        System.out.println("[2] - OPEN TOP");
        System.out.println("[3] - OPEN SIDE");
        System.out.println("[4] - REFRIGERATED");
        System.out.println("[5] - LIQUID");

        System.out.println("Enter vehicle choice: ");
        ContainerType type = getContainerType(scanner.nextLine());
        if (type == null) {
            System.out.println("Invalid input, please try again...");
            return;
        }


        System.out.println("Enter the weight: ");
        String weight = scanner.nextLine();
        if (!CustomUtils.isDouble(weight)){
            System.out.println("Invalid input, please try again...");
            return;
        }

        if (user.isAdmin()){
            displayPort(user);

            System.out.println("Enter port ID: ");
            String portId = scanner.next();

            Port port = new Port().findPortById(portId);
            if (port == null){
                System.out.println("Invalid input, please try again...");
                return;
            }

            port.addContainerToPort(new Container(type, Double.parseDouble(weight)).getId());
        } else {
            user.getManagingPort().addContainerToPort(new Container(type, Double.parseDouble(weight)).getId());
        }
    }

    private static void containerUpdate(String id, User user) {
        Container updateContainer = user.isAdmin() ? new Container().findContainerById(id) : user.getManagingPort().findContainerInPortById(id);

        if (updateContainer == null) {
            System.out.println("Container not found with ID: " + id);
            return;
        }

        System.out.println("[1] - DRY STORAGE");
        System.out.println("[2] - OPEN TOP");
        System.out.println("[3] - OPEN SIDE");
        System.out.println("[4] - REFRIGERATED");
        System.out.println("[5] - LIQUID");

        System.out.println("Enter vehicle choice: ");
        String input = scanner.nextLine();

        ContainerType type = getContainerType(input);
        if (type == null) {
            System.out.println("Invalid input, please try again...");
            return;
        }

        System.out.println("Enter the weight: ");
        String weight = scanner.nextLine();

        if (!CustomUtils.isDouble(weight)){
            System.out.println("Invalid input, please try again...");
            return;
        }
        updateContainer.setContainerType(type);
        updateContainer.setWeight(Double.parseDouble(weight));
    }

    private static void containerDelete(String id, User user) {
        Container containerToDelete = user.isAdmin() ? new Container().findContainerById(id) : user.getManagingPort().findContainerInPortById(id);
        Vehicle vehicle = new Vehicle();
        boolean inVehicle = false;

        if (!user.isAdmin()) {
            for (Vehicle v : user.getManagingPort().getVehicles()) {
                if (v.getLoadedContainerIds().contains(id)) {
                    containerToDelete = new Container().findContainerById(id);
                    vehicle = v;
                    inVehicle = true;
                }
            }
        }

        if (containerToDelete == null) {
            System.out.println("Container not found with ID: " + id);
            return;
        }

        System.out.println("Are you sure you want to delete this container? ([yes] / [no])");
        System.out.println(containerToDelete.toStringSaveFileFormat());
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("no")) {
            System.out.println("Deletion canceled.");
            return;
        }

        if (confirmation.equals("yes")) {
            if (inVehicle) {
                vehicle.getLoadedContainerIds().remove(containerToDelete.getId());
                new Container().getAllContainer().remove(containerToDelete);
                System.out.println("Container deleted successfully.");
                return;
            }

            if (user.isAdmin()) {
                for (User user_ : new User().getAllUsers()) {
                    if (!user_.isAdmin()) user_.getManagingPort().getContainerIds().remove(containerToDelete.getId());
                }

                new Container().getAllContainer().remove(containerToDelete);
                System.out.println("Container deleted successfully.");
                return;
            }

            user.getManagingPort().getContainerIds().remove(containerToDelete.getId());
            new Container().getAllContainer().remove(containerToDelete);
            System.out.println("Container deleted successfully.");
            return;
        }

        System.out.println("Invalid input! Deletion canceled");
    }

    private static void containerCRUD(User user) {
        System.out.println("""
                    [1] - Create
                    [2] - Update
                    [3] - Delete
                    [0] - Quit""");
        System.out.println("Enter option: ");
        String input = scanner.nextLine();

        switch (input) {
            case "0" -> {}

            case "1" -> containerCreate(user);

            case "2" -> {
                System.out.println("Enter the container ID you want to make change: ");
                String containerId = scanner.nextLine();
                containerUpdate(containerId, user);
            }

            case "3" -> {
                System.out.println("Enter the container ID you want to delete: ");
                String containerId = scanner.nextLine();
                containerDelete(containerId, user);
            }

            default -> System.out.println("Error! Invalid input option.");
        }

        fileIO.saveContainers();
    }




    private static User login() {
        CustomUtils.breakLn(5);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        return new User(username, password).authenticate();
    }

    private static boolean logout(User user, String input_) {
        if (input_.equals("0")) {
            user = null;
            return true;
        }

        return false;
    }

    private static void displayPort(User user) {
        if (user.isAdmin()) {
            for (Port port : new Port().getAllPorts()) {
                System.out.println(port.toStringDisplayFormat());
            }
            return;
        }

        System.out.println(user.getManagingPort().toStringDisplayFormat());
    }

    private static void displayContainer(User user) {
        if (user.isAdmin()) {
            for (Container container : new Container().getAllContainer()) {
                System.out.println(container.toStringDisplayFormat());
            }
            return;
        }

        for (Container container : user.getManagingPort().getContainers()) {
            System.out.println(container.toStringDisplayFormat());
        }
    }

    private static void displayVehicle(User user) {
        if (user.isAdmin()) {
            for (Vehicle vehicle : new Vehicle().getAllVehicles()) {
                System.out.println(vehicle.toStringDisplayFormat());
            }
            return;
        }

        for (Vehicle vehicle : user.getManagingPort().getVehicles()) {
            System.out.println(vehicle.toStringDisplayFormat());
        }
    }

    private static void loadContainer(User user) {
        for (Vehicle v : user.getManagingPort().getVehicles()) {
            System.out.println(v.toStringDisplayFormat());
        }

        System.out.println("Enter a vehicleId: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = user.getManagingPort().findVehicleInPortById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found with ID: " + vehicleId);
            return;
        }

        for (Container c : user.getManagingPort().getContainers()) {
            System.out.println(c.toStringDisplayFormat());
        }

        System.out.println("Enter a containerId: ");
        String containerId = scanner.nextLine();
        Container container = user.getManagingPort().findContainerInPortById(containerId);
        if (container == null) {
            System.out.println("Container not found with ID: " + vehicleId);
            return;
        }

        user.getManagingPort().loadContainerToVehicle(container, vehicle);

        fileIO.saveVehicles();
        fileIO.savePorts();
    }

    private static void displayUser(User user) {
        if (user.isAdmin()) {
            for (User user_ : new User().getAllUsers()) {
                if (!user_.isAdmin()) System.out.println(user_);
            }
            return;
        }

        System.out.println(user);
    }

    private static void displayTripDetails(User user) {
        if (user.isAdmin()) {
            for (TripDetails tripDetails : new TripDetails().getAllTripDetails()) {
                System.out.println(tripDetails.toStringDisplayFormat());
            }
            return;
        }

        System.out.println("Ongoing trips: ");
        for (TripDetails tripDetails : user.getManagingPort().getOngoingTraffics()) {
            System.out.println(tripDetails.toStringDisplayFormat());
        }

        System.out.println("Past trips: ");
        for (TripDetails tripDetails : user.getManagingPort().getPastTraffics()) {
            System.out.println(tripDetails.toStringDisplayFormat());
        }
    }

    private static void startTrips(User user) {
        CustomUtils.breakLn(3);

        for (Port p : new Port().getAllPorts()) {
            System.out.println(p.toStringDisplayFormat());
        }

        System.out.println("Enter a portId: ");
        String portId = scanner.nextLine();
        Port port = new Port().findPortById(portId);
        if (port == null) {
            System.out.println("Port not found with ID: " + portId);
            return;
        }

        for (Vehicle v : user.getManagingPort().getVehicles()) {
            System.out.println(v.toStringDisplayFormat());
        }

        System.out.println("Enter a vehicleId: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = user.getManagingPort().findVehicleInPortById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found with ID: " + vehicleId);
            return;
        }

        user.getManagingPort().startTrip(vehicle, port);

        fileIO.saveTripDetails();
        fileIO.savePorts();
    }

    private static void displayMenuLayer0(User user) {
        CustomUtils.breakLn(5);

        if (user.isAdmin()) {
            System.out.println("""
                Admin Menu:
                [1] - List all Ports
                [2] - List all Containers
                [3] - List all Vehicles
                [4] - List all Users
                [5] - List all TripDetails
                [0] - Exit/Logout""");
            return;
        }

        System.out.println("""
                Manager Menu:
                [1] - View Port information
                [2] - List all Port's Containers
                [3] - List all Port's Vehicles
                [4] - View current User information
                [5] - List all Port's TripDetails
                [6] - Load container to vehicle
                [7] - Start trips to other ports
                [0] - Exit/Logout""");
    }

    private static void displayResponseLayer0(User user, String string) {
        CustomUtils.breakLn(5);

        switch (string) {
            case "1" -> {
                displayPort(user);
                portCRUD(user);
            }

            case "2" -> {
                displayContainer(user);
                containerCRUD(user);
            }

            case "3" -> {
                displayVehicle(user);
                vehicleCRUD(user);
            }

            case "4" -> {
                displayUser(user);
            }

            case "5" -> {
                displayTripDetails(user);

            }

            case "6" -> {
                if (user.isAdmin()) {
                    System.out.println("Error! Undefined option.");
                } else {
                    loadContainer(user);
                }
            }

            case "7" -> {
                if (user.isAdmin()) {
                    System.out.println("Error! Undefined option.");
                } else {
                    startTrips(user);
                }
            }

            default -> System.out.println("Error! Undefined option.");
        }
    }

    public static void demo() {
        fileIO.generateMockData();

        do {
            User user = login();
            while (true) {
                if (user == null) {
                    System.out.println("Error! Incorrect username or password!\nPlease try again.");
                    break;
                } else {
                    displayMenuLayer0(user);

                    System.out.println("Enter option: ");
                    String inputString = scanner.nextLine();

                    if (logout(user, inputString)) {
                        System.out.println("GoodBye!");
                        CustomUtils.pressEnterToContinue();
                        break;
                    }

                    displayResponseLayer0(user, inputString);
                    CustomUtils.pressEnterToContinue();
                }
            }
        } while (true);
    }
}
