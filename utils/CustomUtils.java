package utils;

import java.util.Scanner;

public class CustomUtils {
    public static void breakLn(int numberOfLines) {
        if (numberOfLines <= 0) throw new IllegalArgumentException();
        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("\n");
        }
    }

    public static void breakLn() {
        System.out.println("\n");
    }

    public static void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press [Enter] to continue...");
        scanner.nextLine();
//        scanner.close();
    }
}
