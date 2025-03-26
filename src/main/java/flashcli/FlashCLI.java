package flashcli;

import static constants.CommandConstants.EXIT;

import command.Command;
import exceptions.FlashCLIillegalArgumentException;
import parser.Parser;
import ui.Ui;

import static ui.Ui.getUserCommand;

public class FlashCLI {
    /**
     * Main entry-point for the java.flashcli.FlashCLI application.
     */
    public static void main(String[] args) {
        //assert false : "dummy assertion set to fail";
        System.out.println("Welcome to FlashCLI!");
        String fullInputLine = getUserCommand();

        while (!(fullInputLine.equals(EXIT))) {
            try {
                Command c = Parser.parseInput(fullInputLine);
                c.executeCommand();
            } catch (FlashCLIillegalArgumentException e) {
                Ui.showError(e.getMessage());
            } finally {
                fullInputLine = getUserCommand();
            }
        }

        System.out.println("Thank you for using FlashCLI!");
    }
}
