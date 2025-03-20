package command;

import static constants.ErrorMessages.CREATE_USAGE;
import static flashcard.types.FlashcardList.createFlashcard;

import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

/**
 * command class to create a new flashcard
 */

public class CommandCreate extends Command{
    private final String arguments;
    public CommandCreate(String arguments) {
        this.arguments = arguments;
    }

    /**
     * executes the command to create the flashcard
     */
    public void executeCommand() {
        try {
            Ui.showToUser(createFlashcard(arguments));
        } catch (FlashCLIillegalArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(CREATE_USAGE);
        }
    }
}
