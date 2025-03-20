package command;

import exceptions.EmptyListException;
import ui.Ui;

import static flashcard.types.FlashcardList.listFlashcards;

/**
 * Class that handles listing all the questions
 */
public class CommandListQuestion extends Command{
    /**
     * executes the command to view all the questions.
     */
    public void executeCommand() {
        try {
            String list = listFlashcards();
            Ui.showToUser(list);
        } catch (EmptyListException e) {
            Ui.showError(e.getMessage());
        }
    }
}
