import models.Container;
import models.FileIO;
import models.PortManagementSystem;
import utils.ContainerType;

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
//        PortManagementSystem.demo();

//        Container c1 = new Container(ContainerType.OPEN_TOP, 10);
//        Container c2 = new Container(ContainerType.DRY_STORAGE, 20);
//        Container c3 = new Container(ContainerType.LIQUID, 30);

        FileIO fileIO = new FileIO();
//        System.out.println(Boolean.getBoolean("1"));
//        if (fileIO.saveContainers()) {
//            System.out.println("Yay!");
//        } else {
//            System.out.println("Nay...");
//        }

//        if (fileIO.loadContainersFromFile()) {
//            for (Container container : new Container().getAllContainer()) {
//                System.out.println(container.toStringSaveFileFormat());
//            }
//        } else {
//            System.out.println("oh no...");
//        }
    }
}
