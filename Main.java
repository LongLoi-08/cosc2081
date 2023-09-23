import models.Container;
import models.FileIO;
import models.PortManagementSystem;
import utils.ContainerType;

import java.time.LocalDate;

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

//        System.out.println(LocalDate.parse("2023-09-21").toString());
//        System.out.println(LocalDate.parse("2023-09-21").compareTo(LocalDate.parse("2023-09-14")));
    }
}
