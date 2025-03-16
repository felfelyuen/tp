package parser;

import static constants.CommandConstants.CREATE;
import static constants.CommandConstants.VIEW;

import command.Command;
import command.CommandCreate;
import command.CommandViewQuestion;

public class Parser {
    public static Command parseInput(String input) throws IllegalArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        return switch (command) {
            case CREATE -> new CommandCreate(arguments);
            case VIEW -> new CommandViewQuestion(arguments);
            default -> throw new IllegalArgumentException();
        };
    }
}
