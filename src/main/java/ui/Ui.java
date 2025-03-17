package ui;

import static constants.CommandConstants.EXIT;

import java.util.Scanner;

public class Ui {
    private static final Scanner in = new Scanner(System.in);
    public static String getUserCommand() {
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return EXIT; // Return a safe default to avoid exceptions
        }
    }

    public static void showToUser(String message) {
        System.out.println(message);
    }

    public static void showError(String message) {
        System.out.println(message);
    }

}
