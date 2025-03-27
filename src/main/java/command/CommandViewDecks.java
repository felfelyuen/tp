package command;

import static deck.DeckManager.viewDecks;

import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

public class CommandViewDecks extends Command {
    public void executeCommand() {
        try {
            Ui.showToUser(viewDecks());
        } catch (FlashCLIillegalArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
