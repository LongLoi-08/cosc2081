import models.PortManagementSystem;

public class Main {
    public static void showCredits() {
        System.out.println("""
                COSC2081 GROUP ASSIGNMENT
                CONTAINER PORT MANAGEMENT SYSTEM
                Instructor: Mr. Minh Vu & Dr. Phong Ngo
                Group: 25
                s3758273, Loi Gia Long
                sXXXXXXX, Student Name
                sXXXXXXX, Student Name
                sXXXXXXX, Student Name
                sXXXXXXX, Student Name
                """);
    }
    public static void sysInit() {
        showCredits();

        // main system implementation should be here

    }
    public static void main(String[] args) {
//        sysInit();
        PortManagementSystem.demo();
    }
}
