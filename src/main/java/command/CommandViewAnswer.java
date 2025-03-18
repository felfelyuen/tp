package command;

import static constants.ErrorMessages.VIEW_INVALID_INDEX;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static flashcard.types.FlashcardList.viewFlashcardAnswer;

import ui.Ui;

public class CommandViewAnswer extends Command {
    private final String arguments;
    public CommandViewAnswer(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void executeCommand() {
        try {
            int index = Integer.parseInt(arguments);
            Ui.showToUser(viewFlashcardAnswer(index));
        }
        catch (NumberFormatException e) {
            Ui.showError(VIEW_INVALID_INDEX);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Ui.showError(VIEW_OUT_OF_BOUNDS);
        }
    }
}
