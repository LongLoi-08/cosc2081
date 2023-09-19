package models;


import java.util.ArrayList;
import java.util.Objects;

public class User {
    private static ArrayList<User> users = new ArrayList<>();

    private static ArrayList<String> adminMenus = new ArrayList<String>() {{
        // index 0: start menu
       add("Admin Menu:\n" +
               "[1] - List all Ports\n" +
               "[2] - List all Containers\n" +
               "[3] - List all Vehicles\n" +
               "[4] - List all Users\n" +
               "[5] - View Statistics\n" +
               "[0] - Exit/Logout");
    }};

    private static ArrayList<String> managerMenus = new ArrayList<String>() {{
        // index 0: start menu
        add("Manager Menu:\n" +
                "[1] - List all Ports\n" +
                "[2] - List all Containers\n" +
                "[3] - List all Vehicles\n" +
                "[4] - List all Users\n" +
                "[5] - View Statistics\n" +
                "[0] - Exit/Logout");
    }};

    private String username;
    private String password;
    private boolean isAdmin = false;
    private Port managerPort = null;

    public User() {}

    public User(String username, String password, boolean isAdmin, Port managerPort) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.managerPort = managerPort;
        users.add(this);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User setAsManager(Port port) {
        this.managerPort = port;
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

    public Port getManagerPort() {
        return managerPort;
    }

    public User authenticate(/*String username, String password*/) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), this.username) && Objects.equals(user.getPassword(), this.password)) {
                return user;
            }
        }
        return null;
    }

//    public String showAdminMenu() {
//        Scanner inputScanner = new Scanner(System.in);
//
//        CustomUtils.breakLn(5);
//        System.out.println(adminMenus.get(0));
//
//        return inputScanner.nextLine();
//    }
//
//    public String showManagerMenu() {
//        Scanner inputScanner = new Scanner(System.in);
//
//        CustomUtils.breakLn(5);
//        System.out.println("""
//                Admin Menu:
//                [1] - View Port information
//                [2] - List all Port's Containers
//                [3] - List all Port's Vehicles
//                [4] - View Statistics
//                """);
//        System.out.println("[0] - Exit/Logout");
//
//
//        return inputScanner.nextLine();
//    }
//
//    public String showMenu() {
//        if (this.isAdmin) return showAdminMenu();
//        return showManagerMenu();
//    }
}

