package command;

import ui.Ui;

import static constants.ErrorMessages.VIEW_INVALID_INDEX;
import static constants.ErrorMessages.EDIT_USAGE;
import static flashcard.types.FlashcardList.editFlashcard;

public class CommandEdit extends Command {
    private final String arguments;

    public CommandEdit(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void executeCommand() {
        try {
            int index = Integer.parseInt(arguments.split(" ", 2)[0]);
            Ui.showToUser(editFlashcard(index, arguments));
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.showError(e.getMessage());
        } catch (NumberFormatException e) {
            Ui.showError(VIEW_INVALID_INDEX);
        } catch (IllegalArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(EDIT_USAGE);
        }
    }
}
