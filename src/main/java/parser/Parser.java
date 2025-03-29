package parser;

import command.*;

import exceptions.FlashCLIArgumentException;

import static constants.CommandConstants.*;
import static constants.ErrorMessages.NO_DECK_ERROR;
import static constants.ErrorMessages.POSSIBLE_COMMANDS;
import static deck.DeckManager.currentDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input and returns the corresponding command.
 *
 * <p>Splits the input string into command and arguments,
 * validates them, and returns the appropriate command object for execution.</p>
 */
public class Parser {

    /**
     * Parses the given input string to create a corresponding command.
     *
     * @param input the user input containing a command and optional arguments.
     * @return the corresponding Command object based on the parsed input.
     * @throws FlashCLIArgumentException if the input is invalid or required arguments are missing.
     */
    public static Command parseInput(String input) throws FlashCLIArgumentException {
        String[] inputList = input.split(" ", 2);
        String command = inputList[0];
        String arguments = inputList.length > 1 ? inputList[1] : "";
        assert arguments != null : "Arguments should not be null";

        ArrayList<String> commandsWithDeck =
                new ArrayList<>(List.of(CREATE, VIEW_QN, VIEW_ANS, EDIT, LIST, DELETE, QUIZ, RENAME_DECK, INSERT_CODE));
        if (currentDeck == null && commandsWithDeck.contains(command)) {
            throw new FlashCLIArgumentException(NO_DECK_ERROR);
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
        case RENAME_DECK -> new CommandRenameDeck(arguments);
        case VIEW_DECKS -> new CommandViewDecks();
        case QUIZ -> new CommandQuizFlashcards();
        case INSERT_CODE -> new CommandInsertCode(arguments);
        default -> throw new FlashCLIArgumentException(POSSIBLE_COMMANDS);
        };
    }

    /**
     * Parses a given code snippet to the correct formatting.
     *
     * @param codeSnippet the code snippet input.
     * @return the formatted code snippet for storage and printing.
     * @throws FlashCLIArgumentException if the input is invalid or required arguments are missing.
     */
    public static String parseCodeSnippet(String codeSnippet) {
        return codeSnippet;
    }

}
