import models.Container;
import models.FileIO;
import models.Port;
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
                s3907087, Tran Khanh Duc
                s3940677, Vu Phat Dai
                s4021266, Muhammad Zainulabideen Noaman
                s3939240, Nguyen Kim Anh
                """);
    }
    public static void sysInit() {
        showCredits();
        PortManagementSystem.demo(false);
    }
    public static void main(String[] args) {
        sysInit();
    }
}
