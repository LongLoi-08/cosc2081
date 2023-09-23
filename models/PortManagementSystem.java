package models;
import utils.ContainerType;
import utils.CustomUtils;
import utils.VehicleType;

import java.util.Scanner;

public class PortManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static FileIO fileIO = new FileIO();

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

    private static boolean matchID(String ID){
        for (Port port: new Port().getAllPorts()){
            if (port.getId().equals(ID)){
                return true;
            }
            return false;
        }
        return false;
    }

    private static boolean matchVehicleID(String ID){
        for (Vehicle vehicle: new Vehicle().getAllVehicles()){
            if (vehicle.getId().equals(ID)){
                return true;
            }
            return false;
        }
        return false;
    }

    private static boolean matchContainerID(String ID){
        for (Container container: new Container().getAllContainer()){
            if (container.getId().equals(ID)){
                return true;
            }
            return false;
        }
        return false;
    }

    private static void displayPort(User user) {
        if (user.isAdmin()) {
            for (Port port : new Port().getAllPorts()) {
                System.out.println(port.toStringSaveFileFormat());
            }
            return;
        }

        System.out.println(user.getManagingPort().toStringSaveFileFormat());
    }

    private static void displayContainer(User user) {
        if (user.isAdmin()) {
            for (Container container : new Container().getAllContainer()) {
                System.out.println(container.toStringSaveFileFormat());
            }
            return;
        }

        for (Container container : user.getManagingPort().getContainers()) {
            System.out.println(container.toStringSaveFileFormat());
        }
    }

    private static void displayVehicle(User user) {
        if (user.isAdmin()) {
            for (Vehicle vehicle : new Vehicle().getAllVehicles()) {
                System.out.println(vehicle.toStringSaveFileFormat());
            }
            return;
        }

        for (Vehicle vehicle : user.getManagingPort().getVehicles()) {
            System.out.println(vehicle.toStringSaveFileFormat());
        }
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

    private static void displayMenuLayer0(User user) {
        CustomUtils.breakLn(5);

        if (user.isAdmin()) {
            System.out.println("""
                Admin Menu:
                [1] - List all Ports
                [2] - List all Containers
                [3] - List all Vehicles
                [4] - List all Users
                [5] - View Statistics
                [0] - Exit/Logout""");
            return;
        }

        System.out.println("""
                Manager Menu:
                [1] - View Port information
                [2] - List all Port's Containers
                [3] - List all Port's Vehicles
                [4] - View current User information
                [4] - View Statistics
                [0] - Exit/Logout""");
    }

    private static void displayResponseLayer0(User user, String string) {
        CustomUtils.breakLn(5);
        switch (string) {
            case "1" -> {
                displayPort(user);
                PortCRUD(user);
            }
            case "2" -> {
                displayContainer(user);
                ContainerCRUD(user);
            }
            case "3" -> {
                displayVehicle(user);
                VehicleCRUD(user);
            }
            case "4" -> displayUser(user);
            default -> System.out.println("Error! Undefined option.");
        }
    }

    private static Vehicle crudVehicleCreate(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, Port port) {
        return new Vehicle(vehicleType, maxFuel, maxCarryCapacity, port.getId());
    }
    private static void PortCRUD(User user) {
        if (user.isAdmin()) {
            System.out.println("""
                    [1] - Create
                    [2] - Update
                    [3] - Delete""");

            System.out.println("Enter option: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> PortCreate();
                case "2" -> {
                    System.out.println("Enter the port ID you want to make change: ");
                    String portId = scanner.nextLine();
                    if (matchID(portId) == true) {
                        PortUpdate(portId);
                    }
                }
                case "3" -> {

                }
            }
        }
    }

    private static void PortCreate(){
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter latitude: ");
        String latitude = scanner.nextLine();
        Double Latitude = 0.0;
        if (CustomUtils.isDouble(latitude)){
            Latitude = Double.valueOf(latitude);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter longitude: ");
        String longitude = scanner.nextLine();
        Double Longitude = 0.0;
        if (CustomUtils.isDouble(longitude)){
            Longitude = Double.valueOf(longitude);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter max capacity: ");
        String maxCap = scanner.nextLine();
        Double MaxCap = 0.0;
        if (CustomUtils.isDouble(maxCap)){
            MaxCap = Double.valueOf(maxCap);
        }else{
            System.out.println("Invalid input");
        }System.out.println("Enter is landing: ");
        String isLanding = scanner.nextLine();
        Boolean IsLanding = false;
        if (CustomUtils.isBoolean(isLanding)) {
            IsLanding = Boolean.parseBoolean(isLanding);
        }else{
            System.out.println("Invalid input");
        }
        Port newPort = new Port(name, Longitude, Latitude, MaxCap, IsLanding);
    }

    private static void PortUpdate(String ID){
        Port updatePort = new Port().findPortById(ID);
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        updatePort.setName(name);
        System.out.println("Enter latitude: ");
        String latitude = scanner.nextLine();
        if (CustomUtils.isDouble(latitude)){
            Double Latitude = Double.valueOf(latitude);
            updatePort.setLatitude(Latitude);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter longitude: ");
        String longitude = scanner.nextLine();
        if (CustomUtils.isDouble(longitude)){
            Double Longitude = Double.valueOf(longitude);
            updatePort.setLongitude(Longitude);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter max capacity: ");
        String maxCap = scanner.nextLine();
        if (CustomUtils.isDouble(maxCap)){
            Double MaxCap = Double.valueOf(maxCap);
            updatePort.setMaxCapacity(MaxCap);
        }else{
            System.out.println("Invalid input");
        }System.out.println("Enter is landing: ");
        String isLanding = scanner.nextLine();
        if (CustomUtils.isBoolean(isLanding)) {
            Boolean IsLanding = Boolean.parseBoolean(isLanding);
            updatePort.setIsLanding(IsLanding);
        }else{
            System.out.println("Invalid input");
        }
    }

    private static void PortDelete(String ID){

    }

    private static void VehicleCRUD(User user){
        System.out.println("""
                    [1] - Create
                    [2] - Update
                    [3] - Delete""");
        System.out.println("Enter option: ");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> VehicleCreate(user);
            case "2" -> {
                System.out.println("Enter the vehicle ID you want to make change: ");
                String vehicleId = scanner.nextLine();
                if (matchVehicleID(vehicleId) == true) {
                    VehicleUpdate(vehicleId, user);
                }
            }
            case "3" -> {

            }
        }
    }

    private static Vehicle VehicleCreate(User user){
        Vehicle newVehicle;
        System.out.println("[1] - Ship");
        System.out.println("[2] - Truck");
        System.out.println("[3] - Refridgerated Truck");
        System.out.println("[4] - Tanker Truck");
        System.out.println("Enter vehicle choice: ");
        String input = scanner.nextLine();
        VehicleType type = VehicleType.TRUCK;
        switch (input){
            case "1" -> type = VehicleType.SHIP;
            case "2" -> type = VehicleType.TRUCK;
            case "3" -> type = VehicleType.REEFER_TRUCK;
            case "4" -> type = VehicleType.TANKER_TRUCK;
            default -> System.out.println("Invalid input");
        }
        System.out.println("Enter max fuel: ");
        String maxFuel = scanner.next();
        Double MaxFuel = null;
        Double MaxCarryCap = null;
        Port Port;
        if (CustomUtils.isDouble(maxFuel)){
            MaxFuel = Double.valueOf(maxFuel);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter max carry capacity: ");
        String maxCarryCap = scanner.next();
        if (CustomUtils.isDouble(maxCarryCap)){
            MaxCarryCap = Double.valueOf(maxCarryCap);
        }else{
            System.out.println("Invalid input");
        }
        if (user.isAdmin()){
            displayPort(user);
            System.out.println("Enter port ID: ");
            String portId = scanner.next();
            if (matchID(portId) == true){
                newVehicle = new Vehicle(type, MaxFuel, MaxCarryCap, portId);
            }
        }
        return newVehicle = new Vehicle(type, MaxFuel, MaxCarryCap, user.getManagingPort().getId());
    }
    private static Vehicle VehicleUpdate(String ID, User user){
        Vehicle updateVehicle = new Vehicle().findVehicleById(ID);
        System.out.println("[1] - Ship");
        System.out.println("[2] - Truck");
        System.out.println("[3] - Refridgerated Truck");
        System.out.println("[4] - Tanker Truck");
        System.out.println("Enter vehicle choice: ");
        String input = scanner.nextLine();
        VehicleType type = VehicleType.TRUCK;
        switch (input){
            case "1" -> type = VehicleType.SHIP;
            case "2" -> type = VehicleType.TRUCK;
            case "3" -> type = VehicleType.REEFER_TRUCK;
            case "4" -> type = VehicleType.TANKER_TRUCK;
            default -> System.out.println("Invalid input");
        }
        updateVehicle.setVehicleType(type);
        System.out.println("Enter max fuel: ");
        String maxFuel = scanner.next();
        Double MaxFuel = null;
        Double MaxCarryCap = null;
        Port Port;
        if (CustomUtils.isDouble(maxFuel)){
            MaxFuel = Double.valueOf(maxFuel);
            updateVehicle.setMaxFuel(MaxFuel);
        }else{
            System.out.println("Invalid input");
        }
        System.out.println("Enter max carry capacity: ");
        String maxCarryCap = scanner.next();
        if (CustomUtils.isDouble(maxCarryCap)){
            MaxCarryCap = Double.valueOf(maxCarryCap);
            updateVehicle.setMaxCarryCapacity(MaxCarryCap);
        }else{
            System.out.println("Invalid input");
        }
        if (user.isAdmin()){
            displayPort(user);
            System.out.println("Enter port ID: ");
            String portId = scanner.next();
            if (matchID(portId) == true){
                updateVehicle = new Vehicle(type, MaxFuel, MaxCarryCap, portId);
            }
        }
        return updateVehicle;
    }
//    private static Vehicle VehicleDelete(User user){}
    private static void ContainerCRUD (User user) {
        System.out.println("""
                [1] - Create
                [2] - Update
                [3] - Delete""");
        System.out.println("Enter option: ");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> ContainerCreate(user);
            case "2" -> {
                System.out.println("Enter the container ID you want to make change: ");
                String containerId = scanner.nextLine();
                if (matchContainerID(containerId) == true) {
                    ContainerUpdate(containerId);
                }
            }
            case "3" -> {

            }
        }
    }

    private static Container ContainerCreate(User user){
        Container newContainer;
        System.out.println("[1] - DRY STORAGE");
        System.out.println("[2] - OPEN TOP");
        System.out.println("[3] - OPEN SIDE");
        System.out.println("[4] - REFRIGERATED");
        System.out.println("[5] - LIQUID");
        String input = scanner.nextLine();
        ContainerType type = ContainerType.DRY_STORAGE;
        switch (input){
            case "1" -> type = ContainerType.DRY_STORAGE;
            case "2" -> type = ContainerType.OPEN_TOP;
            case "3" -> type = ContainerType.OPEN_SIDE;
            case "4" -> type = ContainerType.OPEN_TOP;
            case "5" -> type = ContainerType.LIQUID;
            default -> System.out.println("Invalid input");
        }
        System.out.println("Enter the weight: ");
        String weight = scanner.nextLine();
        Double Weight = 0.0;
        if (CustomUtils.isDouble(weight)){
            Weight = Double.valueOf(weight);
        }else{
            System.out.println("Invalid input");
        }
        if (user.isAdmin()){
            displayPort(user);
            System.out.println("Enter port ID: ");
            String portId = scanner.next();
            if (matchID(portId) == true){
                newContainer = new Container(type, Weight);
            }
        }
        return newContainer = new Container(type, Weight);
    }
    private static Container ContainerUpdate(String ID){
        Container updateContainer = new Container().findContainerById(ID);
        System.out.println("[1] - DRY STORAGE");
        System.out.println("[2] - OPEN TOP");
        System.out.println("[3] - OPEN SIDE");
        System.out.println("[4] - REFRIGERATED");
        System.out.println("[5] - LIQUID");
        String input = scanner.nextLine();
        ContainerType type = ContainerType.DRY_STORAGE;
        switch (input){
            case "1" -> type = ContainerType.DRY_STORAGE;
            case "2" -> type = ContainerType.OPEN_TOP;
            case "3" -> type = ContainerType.OPEN_SIDE;
            case "4" -> type = ContainerType.REFRIGERATED;
            case "5" -> type = ContainerType.LIQUID;
            default -> System.out.println("Invalid input");
        }
        updateContainer.setContainerType(type);
        System.out.println("Enter the weight: ");
        String weight = scanner.nextLine();
        Double Weight = 0.0;
        if (CustomUtils.isDouble(weight)){
            Weight = Double.valueOf(weight);
        }else{
            System.out.println("Invalid input");
        }
        updateContainer.setWeight(Weight);
        return updateContainer;
    }
//    private static Container ContainerDelete(User user){}


//    private static Vehicle crudVehicleCreate(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, Port port) {
//        return new Vehicle(vehicleType, maxFuel, maxCarryCapacity, port);
//    }

//    private static boolean crudVehicleUpdate(User user, String id) {
//        // search for vehicle via id (depends on user.isAdmin())
//        // ask what to me update
//
//        // vehicle type?
//        //      => get input -> validate -> call setters -> return true if success
//        // vehicle max fuel?
//        //      => get input -> validate -> call setters -> return true if success
//        // vehicle carry cap?
//        //      => get input -> validate -> call setters -> return true if success
//        // vehicle port?
//        //      => get input -> validate -> call setters -> return true if success
//
//        // return false in case something is fucked...
//    }
//
//    private static boolean crudVehicleDelete(User user, String id) {
//        // search for vehicle via id (depends on user.isAdmin())
//        // if the vehicle is traveling (the obj should be inside the Port.ongoingTrips) then ignore - cannot delete an ongoing trip
//        // if manager -> search in port -> delete if found -> return true else false
//        // if admin -> search for each port and find what port contains that vehicle
//        // IMPORTANT: UNLOAD THE VEHICLE BEFORE DELETING
//    }
//
//    private static void crudVehicle() {
//        // print options
//        // if create new vehicle
//        // => get user input then validate
//        // if not validate => do something (maybe ask to input again or throw error and return to home screen idk)
//        // if validated information
//        crudVehicleCreate(/*input here*/);
//    }

    public static void demo() {
        fileIO.generateMockData();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
