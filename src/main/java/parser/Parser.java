package parser;

import command.Command;
import command.CommandCreate;
import command.CommandCreateDeck;
import command.CommandDelete;
import command.CommandEdit;
import command.CommandViewAnswer;
import command.CommandViewQuestion;
import command.CommandListQuestion;
import static constants.CommandConstants.CREATE;
import static constants.CommandConstants.DELETE;
import static constants.CommandConstants.NEW_DECK;
import static constants.CommandConstants.VIEW_ANS;
import static constants.CommandConstants.VIEW_QN;
import static constants.CommandConstants.EDIT;
import static constants.CommandConstants.LIST;


public class Parser {
    public static Command parseInput(String input) throws IllegalArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        assert arguments != null : "Arguments should not be null";
        return switch (command) {
        case CREATE -> new CommandCreate(arguments);
        case VIEW_QN -> new CommandViewQuestion(arguments);
        case VIEW_ANS -> new CommandViewAnswer(arguments);
        case EDIT -> new CommandEdit(arguments);
        case LIST -> new CommandListQuestion();
        case DELETE -> new CommandDelete(arguments);
        case NEW_DECK -> new CommandCreateDeck(arguments);
        default -> throw new IllegalArgumentException();
        };
    }
}
