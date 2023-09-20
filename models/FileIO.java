package models;

import java.io.*;
import java.util.ArrayList;

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
}