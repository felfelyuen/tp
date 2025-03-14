package Parser;

import static Constants.CommandConstants.CREATE;

import Commands.Command;
import Commands.CommandCreate;

public class Parser {
    public static Command parseInput(String input) throws IllegalArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        return switch (command) {
            case CREATE -> new CommandCreate(arguments);
            default -> throw new IllegalArgumentException();
        };
    }
}
