package command;

import static constants.ErrorMessages.CREATE_USAGE;
import static flashcard.types.FlashcardList.createFlashcard;

import ui.Ui;

public class CommandCreate extends Command{
    private final String arguments;
    public CommandCreate(String arguments) {
        this.arguments = arguments;
    }
    public void executeCommand() {
        try {
            Ui.showToUser(createFlashcard(arguments));
        } catch (IllegalArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(CREATE_USAGE);
        }
    }
}
