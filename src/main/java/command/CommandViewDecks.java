package command;

import static deck.DeckManager.viewDecks;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

public class CommandViewDecks extends Command {
    public void executeCommand() {
        try {
            Ui.showToUser(viewDecks());
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
