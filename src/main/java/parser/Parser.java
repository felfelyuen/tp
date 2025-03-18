package parser;

import command.Command;
import command.CommandCreate;
import command.CommandEdit;
import command.CommandViewQuestion;
import static constants.CommandConstants.CREATE;
import static constants.CommandConstants.VIEW_QN;
import static constants.CommandConstants.EDIT;


public class Parser {
    public static Command parseInput(String input) throws IllegalArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        return switch (command) {
        case CREATE -> new CommandCreate(arguments);
        case VIEW_QN -> new CommandViewQuestion(arguments);
        case EDIT -> new CommandEdit(arguments);
        default -> throw new IllegalArgumentException();
        };
    }
}
