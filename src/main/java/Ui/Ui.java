package Ui;

import java.util.Scanner;

public class Ui {
    private static final Scanner in = new Scanner(System.in);
    public static String getUserCommand() {
        return in.nextLine();
    }

    public static void showToUser(String message) {
        System.out.println(message);
    }

    public static void showError(String message) {
        System.out.println(message);
    }
}
