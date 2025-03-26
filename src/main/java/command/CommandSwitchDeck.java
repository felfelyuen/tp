package command;

import static deck.DeckManager.switchDeck;

import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

public class CommandSwitchDeck extends Command {
    private final String arguments;

    public CommandSwitchDeck(String arguments) {
        this.arguments = arguments;
    }

    public void executeCommand() {
        try {
            Ui.showToUser(switchDeck(arguments));
        } catch (FlashCLIillegalArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
