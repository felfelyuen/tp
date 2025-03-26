package parser;

import command.Command;
import command.CommandCreate;
import command.CommandCreateDeck;
import command.CommandDelete;
import command.CommandEdit;
import command.CommandSwitchDeck;
import command.CommandViewAnswer;
import command.CommandViewQuestion;
import command.CommandListQuestion;
import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

import static constants.CommandConstants.CREATE;
import static constants.CommandConstants.DELETE;
import static constants.CommandConstants.NEW_DECK;
import static constants.CommandConstants.SWITCH_DECK;
import static constants.CommandConstants.VIEW_ANS;
import static constants.CommandConstants.VIEW_QN;
import static constants.CommandConstants.EDIT;
import static constants.CommandConstants.LIST;
import static constants.ErrorMessages.NO_DECK_ERROR;
import static constants.ErrorMessages.POSSIBLE_COMMANDS;
import static deck.DeckManager.currentDeck;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    public static Command parseInput(String input) throws FlashCLIillegalArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        assert arguments != null : "Arguments should not be null";
        ArrayList<String> commandsWithDeck = new ArrayList<>(List.of(CREATE, VIEW_QN, VIEW_ANS, EDIT, LIST, DELETE));
        if (currentDeck == null && commandsWithDeck.contains(command)) {
            throw new FlashCLIillegalArgumentException(NO_DECK_ERROR);
        }
        return switch (command) {
        case CREATE -> new CommandCreate(arguments);
        case VIEW_QN -> new CommandViewQuestion(arguments);
        case VIEW_ANS -> new CommandViewAnswer(arguments);
        case EDIT -> new CommandEdit(arguments);
        case LIST -> new CommandListQuestion();
        case DELETE -> new CommandDelete(arguments);
        case NEW_DECK -> new CommandCreateDeck(arguments);
        case SWITCH_DECK -> new CommandSwitchDeck(arguments);
        default -> throw new FlashCLIillegalArgumentException(POSSIBLE_COMMANDS);
        };
    }
}
