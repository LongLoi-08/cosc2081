package models;
import utils.ContainerType;
import utils.CustomUtils;
import utils.VehicleType;

import java.util.Scanner;

public class PortManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);

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
            case "1" -> displayPort(user);
            case "2" -> displayContainer(user);
            case "3" -> displayVehicle(user);
            case "4" -> displayUser(user);
            default -> System.out.println("Error! Undefined option.");
        }
    }

    private static Vehicle crudVehicleCreate(VehicleType vehicleType, double maxFuel, double maxCarryCapacity, Port port) {
        return new Vehicle(vehicleType, maxFuel, maxCarryCapacity, port);
    }

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

//    private static void displayMenuLayer2(User user, String string) {
//        switch (string) {
//            case "1" -> {
//                if (user.isAdmin()) {
//
//                }
//            }
//
//            case "2" -> {
//
//            }
//
//            case "3" -> {
//
//            }
//        }
//    }

    public static void demo() {
        Port assignedPort = new Port(
                "Sample Port",
                0,
                0,
                100,
                true
        );

        Vehicle vehicle = new Vehicle(VehicleType.TRUCK, 100.0, 200.0, assignedPort);

        Container container1 = new Container(ContainerType.DRY_STORAGE, 10);
        Container container2 = new Container(ContainerType.OPEN_TOP, 50);

        vehicle.loadContainer(container2);

        assignedPort.getContainers().add(container1);
        assignedPort.getVehicles().add(vehicle);

        User portManager_ = new User("manager", "pass").setAsManager(assignedPort);
        User admin_ = new User("admin", "pass").setAsAdmin();
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
