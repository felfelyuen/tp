package command;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

import static constants.ErrorMessages.INDEX_OUT_OF_BOUNDS;
import static deck.DeckManager.currentDeck;

/**
 * Class that handles viewing of the quiz results
 */

public class CommandViewQuizResult extends Command{
    /**
     * executes command to quiz all the questions in the deck.
     */
    @Override
    public void executeCommand() {
        try {
            Ui.loadingeffect();
            Ui.showToUser(currentDeck.showQuizResult());
        }  catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }  catch (ArrayIndexOutOfBoundsException e) {
            Ui.showError(INDEX_OUT_OF_BOUNDS);
        }
    }
}
