package command;

import static deck.DeckManager.unselectDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

public class CommandUnselectDeck extends Command {
    private final String arguments;
    public CommandUnselectDeck(String arguments) {
        this.arguments = arguments;
    }
    /**
     * Executes the command to unselect current deck
     */
    public void executeCommand() {
        try {
            Ui.loadingeffect();
            Ui.showToUser(unselectDeck(this.arguments));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
