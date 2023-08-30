package models;
import java.util.Scanner;

public class PortManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Port assignedPort = new Port("P001", "Sample Port");
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
                            System.out.println("1");
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
                            System.out.println("1");
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

