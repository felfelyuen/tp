package FlashCLI;

import static Constants.CommandConstants.EXIT;

import Commands.Command;
import Ui.Ui;
import Parser.Parser;

public class FlashCLI {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to FlashCLI!");
        String fullInputLine = Ui.getUserCommand();

        while (!(fullInputLine.equals(EXIT))) {
            try {
                Command c = Parser.parseInput(fullInputLine);
                c.executeCommand();
            } catch (IllegalArgumentException e) {
                System.out.println("Possible commands are: add, exit");
            } finally {
                fullInputLine = Ui.getUserCommand();
            }
        }

        System.out.println("Thank you for using FlashCLI!");
    }
}
