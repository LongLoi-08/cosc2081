package models;
import utils.ContainerType;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class PortManagementSystem {
    public static void demo() throws IOException {
        Scanner scanner = new Scanner(System.in);
//        Port assignedPort = new Port("P001", "Sample Port");
        ArrayList<Port> Ports = new ArrayList<>();
        Port assignedPort = new Port(
                "Sample Port",
                0,
                0,
                100,
                true
        );
        PortManager portManager = new PortManager(assignedPort);

        while (true) {
            System.out.println("1. Admin Login");
            System.out.println("2. Port Manager Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                scanner.nextLine();
                System.out.print("Enter admin username: ");
                String adminUsername = scanner.nextLine();
                System.out.print("Enter admin password: ");
                String adminPassword = scanner.nextLine();

                Admin admin = new Admin(adminUsername,adminPassword);
                if (admin.login(adminUsername, adminPassword)) {
                    while (true) {
                        admin.showAdminMenu();
                        System.out.print("Enter your choice: ");
                        int adminChoice = scanner.nextInt();

                        if (adminChoice == 1) {
                            portManager.viewPortInformation();
                            System.out.println("1. Create");
                            System.out.println("2. Delete");
                            System.out.print("Enter your choice: ");
                            int adminNextChoice = scanner.nextInt();
                            if (adminNextChoice == 1){
                                Pattern pattern = Pattern.compile("^Port\\d+$");
                                System.out.println("Port name (PortN): ");
                                String name = scanner.next();
                                Matcher matcher = pattern.matcher(name);
                                String portName = "";
                                if (matcher.matches()){
                                    portName = name;
                                }else{
                                    System.out.println("Invalid name");
                                    break;
                                }
                                System.out.println("Latitude: ");
                                Double latitude = scanner.nextDouble();
                                System.out.println("Longitude: ");
                                Double longitude = scanner.nextDouble();
                                System.out.println("MaxCapacity: ");
                                Double maxCapacity = scanner.nextDouble();
                                System.out.println("isLanding: ");
                                Boolean isLanding = scanner.nextBoolean();
                                Port P1 = new Port(portName,latitude, longitude, maxCapacity, isLanding);

                                FileIO.write("Ports", "Ports List", String.valueOf(P1));
//                                Ports.add(P1);
//                                for (int i = 1; i <= Ports.size(); i++ ) {
//                                    System.out.println("Port name: "+ Ports.get(i).getName());
//                                    System.out.println("Port ID: "+ Ports.get(i).getId());
//                                }
                            }
                        } else if (adminChoice == 2) {
                            System.out.println("2");
                        } else if (adminChoice == 3) {
                            System.out.println("3");
                        } else if (adminChoice == 4) {
                            break;
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                } else {
                    System.out.println("Invalid admin credentials.");
                }
            } else if (choice == 2) {
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter port manager username: ");
                String managerUsername = scanner.nextLine();
                System.out.print("Enter port manager password: ");
                String managerPassword = scanner.nextLine();

                if (portManager.login(managerUsername, managerPassword)) {
                    while (true) {
                        portManager.showPortManagerMenu();
                        System.out.print("Enter your choice: ");
                        int managerChoice = scanner.nextInt();


                        if (managerChoice == 1) {
                            System.out.println("1. Create");
                            System.out.println("2. Delete");
                            System.out.print("Enter your choice: ");
                            int PMNextChoice = scanner.nextInt();
                            if (PMNextChoice == 1) {
                                System.out.println("Choose a container type");
                                System.out.println("1. DRY STORAGE");
                                System.out.println("2. OPEN TOP");
                                System.out.println("3. OPEN SIDE");
                                System.out.println("4. REFRIGERATED");
                                System.out.println("5. LIQUID");
                                System.out.println("Enter your choice: ");
                                int typeChoice = scanner.nextInt();
                                ContainerType type;
                                if (typeChoice == 1){
                                    type = ContainerType.DRY_STORAGE;
                                }else if (typeChoice == 2){
                                    type = ContainerType.OPEN_TOP;
                                } else if (typeChoice == 3) {
                                    type = ContainerType.OPEN_SIDE;
                                } else if (typeChoice == 4) {
                                    type = ContainerType.REFRIGERATED;
                                } else if (typeChoice == 5) {
                                    type = ContainerType.LIQUID;
                                }else {
                                    System.out.println("Invalid input");
                                    break;
                                }
                                System.out.println("Enter the weight: ");
                                int weight = scanner.nextInt();
                                int containerWeight = 0;
                                if (weight > 0){
                                    containerWeight = weight;
                                }else{
                                    System.out.println("Invalid weight");
                                    break;
                                }
                                Container C1 = new Container(type,containerWeight);
                            }
                        } else if (managerChoice == 2) {
                            portManager.viewPortInformation();
                        } else if (managerChoice == 3) {
                            break;
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                } else {
                    System.out.println("Invalid port manager credentials.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting the simulation.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}

