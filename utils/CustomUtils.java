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
    public static boolean isDouble(String str) {
        try {
            // Attempt to parse the string as a double
            Double.parseDouble(str);
            // If parsing succeeds, it's a valid double
            return true;
        } catch (NumberFormatException e) {
            // If parsing fails, it's not a valid double
            return false;
        }
    }
    public static boolean isBoolean(String str) {
        String lowerCaseStr = str.toLowerCase(); // Convert the input to lowercase for case-insensitive comparison
        return lowerCaseStr.equals("true") || lowerCaseStr.equals("false");
    }

    public static boolean parseBoolean(String str) {
        String lowerCaseStr = str.toLowerCase();
        if (lowerCaseStr.equals("true")) {
            return true;
        } else if (lowerCaseStr.equals("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Input is not a valid boolean value: " + str);
        }
    }
}
