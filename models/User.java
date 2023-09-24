package models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class User {
    private static ArrayList<Boolean> idCache = new ArrayList<>(Collections.nCopies(1000, false));
    private static ArrayList<User> users = new ArrayList<>();


    private String username;
    private String password;
    private boolean isAdmin = false;
    private String portID = "";

    public User() {}

    public User(String username, String password, boolean isAdmin, String portID) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.portID = portID;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User setAsManager(Port port) {
        this.portID = port.getId();
        users.add(this);
        return this;
    }

    protected User setAsAdmin() {
        this.isAdmin = true;
        users.add(this);
        return this;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Port getManagingPort() {
        // return null if is admin
        return this.portID.equals("") ? null : new Port().findPortById(this.portID);
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    protected void setManagingPort(Port port) {
        if (!this.isAdmin()) this.portID = port.getId();
    }

    public User authenticate() {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), this.username) && Objects.equals(user.getPassword(), this.password)) {
                return user;
            }
        }
        return null;
    }

    public String toStringSaveFileFormat() {
        return String.format(
                "%s|%s|%d|%s",
                username, password, isAdmin ? 1 : 0, portID
        );
    }

    @Override
    public String toString() {
        return this.isAdmin ? "Admin{" +
                "\n     username='" + username + '\'' +
                ", \n     password='" + password + '\'' +
                "\n}" : "Manager{" +
                "\n     username='" + username + '\'' +
                ", \n     password='" + password + '\'' +
                ", \n     portID='" + portID + '\'' +
                "\n}";
    }
}
