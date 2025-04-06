package command;

import static deck.DeckManager.viewDecks;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

/**
 * Command to view all decks available.
 *
 * <p>Lists all decks that are available.
 * Displays either the success message or an error message based on the result.</p>
 */
public class CommandViewDecks extends Command {
    private final String arguments;
    public CommandViewDecks(String arguments) {
        this.arguments = arguments;
    }
    /**
     * Executes the command to list decks and shows the result to the user.
     *
     * <p>If an error occurs during deck viewing, an error message is displayed.</p>
     */
    public void executeCommand() {
        try {
            Ui.showToUser(viewDecks(this.arguments));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
