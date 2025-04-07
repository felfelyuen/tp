package command;

import static constants.ErrorMessages.INVALID_INDEX_INPUT;
import static deck.DeckManager.currentDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

/**
 * Class that handles viewing of the question of the flashcard
 */
public class CommandViewQuestion extends Command{
    private final String arguments;
    public CommandViewQuestion(String arguments) {
        this.arguments = arguments;
    }

    /**
     * executes the command to view the question.
     */
    public void executeCommand() {
        try {
            Ui.showToUser(currentDeck.viewFlashcardQuestion(arguments));
        } catch (NumberFormatException e) {
            Ui.showError(INVALID_INDEX_INPUT);
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
