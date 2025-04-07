package command;

import static deck.DeckManager.deleteDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

public class CommandDeleteDeck extends Command {

    private final int listIndex;

    public CommandDeleteDeck(int listIndex) {
        this.listIndex = listIndex;
    }

    public void executeCommand() {
        try {
            Ui.loadingeffect();
            Ui.showToUser(deleteDeck(this.listIndex));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }

}
