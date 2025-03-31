package flashcli;

import static constants.CommandConstants.EXIT;

import command.Command;
import exceptions.FlashCLIArgumentException;
import logger.LoggingSetup;
import parser.Parser;
import ui.Ui;

import static ui.Ui.getUserCommand;

public class FlashCLI {
    /**
     * Main entry-point for the java.flashcli.FlashCLI application.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to FlashCLI!");
        LoggingSetup.configureGlobalLogging();
        String fullInputLine = getUserCommand();

        while (!(fullInputLine.equals(EXIT))) {
            try {
                Command c = Parser.parseInput(fullInputLine);
                if (c != null) {
                    c.executeCommand();
                }
            } catch (FlashCLIArgumentException e) {
                Ui.showError(e.getMessage());
            } finally {
                fullInputLine = getUserCommand();
            }
        }

        System.out.println("Thank you for using FlashCLI!");
    }
}
