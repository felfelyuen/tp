package command;

import static deck.DeckManager.switchDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

public class CommandSwitchDeck extends Command {
    private final String arguments;

    public CommandSwitchDeck(String arguments) {
        this.arguments = arguments;
    }

    public void executeCommand() {
        try {
            Ui.showToUser(switchDeck(arguments));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }
}
