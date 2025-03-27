package command;

import static deck.DeckManager.renameDeck;

import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

public class CommandRenameDeck extends Command {
    private final String arguments;

    public CommandRenameDeck(String arguments) {
        this.arguments = arguments;
    }

    public void executeCommand() {
        try {
            Ui.showToUser(renameDeck(this.arguments));
        } catch (FlashCLIillegalArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
