package models;


public class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public boolean login(String enteredUsername, String enteredPassword){
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super("admin", "1");
    }
    public void showAdminMenu(){
        System.out.println(" $$$$$$\\        $$\\               $$\\           \n" +
                "$$  __$$\\       $$ |              \\__|          \n" +
                "$$ /  $$ | $$$$$$$ |$$$$$$\\$$$$\\  $$\\ $$$$$$$\\  \n" +
                "$$$$$$$$ |$$  __$$ |$$  _$$  _$$\\ $$ |$$  __$$\\ \n" +
                "$$  __$$ |$$ /  $$ |$$ / $$ / $$ |$$ |$$ |  $$ |\n" +
                "$$ |  $$ |$$ |  $$ |$$ | $$ | $$ |$$ |$$ |  $$ |\n" +
                "$$ |  $$ |\\$$$$$$$ |$$ | $$ | $$ |$$ |$$ |  $$ |\n" +
                "\\__|  \\__| \\_______|\\__| \\__| \\__|\\__|\\__|  \\__|");
//        System.out.println("Admin Menu:");
        System.out.println("1. Manage Ports");
        System.out.println("2. Manage Containers");
        System.out.println("3. View Statistics");
        System.out.println("4. Exit");
    }
}

class PortManager extends User {
    private Port assignedPort;

    public PortManager(Port assignedPort) {
        super("manager1", "1");
        this.assignedPort = assignedPort;
    }

    public void showPortManagerMenu() {
        System.out.println("$$$$$$$\\                       $$\\           $$\\      $$\\                                                             \n" +
                "$$  __$$\\                      $$ |          $$$\\    $$$ |                                                            \n" +
                "$$ |  $$ | $$$$$$\\   $$$$$$\\ $$$$$$\\         $$$$\\  $$$$ | $$$$$$\\  $$$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\  \n" +
                "$$$$$$$  |$$  __$$\\ $$  __$$\\\\_$$  _|        $$\\$$\\$$ $$ | \\____$$\\ $$  __$$\\  \\____$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ \n" +
                "$$  ____/ $$ /  $$ |$$ |  \\__| $$ |          $$ \\$$$  $$ | $$$$$$$ |$$ |  $$ | $$$$$$$ |$$ /  $$ |$$$$$$$$ |$$ |  \\__|\n" +
                "$$ |      $$ |  $$ |$$ |       $$ |$$\\       $$ |\\$  /$$ |$$  __$$ |$$ |  $$ |$$  __$$ |$$ |  $$ |$$   ____|$$ |      \n" +
                "$$ |      \\$$$$$$  |$$ |       \\$$$$  |      $$ | \\_/ $$ |\\$$$$$$$ |$$ |  $$ |\\$$$$$$$ |\\$$$$$$$ |\\$$$$$$$\\ $$ |      \n" +
                "\\__|       \\______/ \\__|        \\____/       \\__|     \\__| \\_______|\\__|  \\__| \\_______| \\____$$ | \\_______|\\__|      \n" +
                "                                                                                        $$\\   $$ |                    \n" +
                "                                                                                        \\$$$$$$  |                    \n" +
                "                                                                                         \\______/");
//        System.out.println("Port Manager Menu:");
        System.out.println("1. Manage Containers");
        System.out.println("2. View Port Information");
        System.out.println("3. Exit");
    }

    public void viewPortInformation() {
        System.out.println("Port Name: " + assignedPort.getName());
        System.out.println("Port ID: " + assignedPort.getId());
    }
}

