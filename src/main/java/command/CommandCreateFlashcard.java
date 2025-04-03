package command;

import static constants.ErrorMessages.CREATE_USAGE;
import static deck.DeckManager.currentDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

/**
 * command class to create a new flashcard
 */

public class CommandCreateFlashcard extends Command{
    private final String arguments;
    public CommandCreateFlashcard(String arguments) {
        this.arguments = arguments;
    }

    /**
     * executes the command to create the flashcard
     */
    public void executeCommand() {
        try {
            Ui.showToUser(currentDeck.createFlashcard(arguments));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(CREATE_USAGE);
        }
    }
}
