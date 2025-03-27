package command;

import static deck.DeckManager.createDeck;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

public class CommandCreateDeck extends Command{
    public final String arguments;

    public CommandCreateDeck(String arguments) {
        this.arguments = arguments;
    }

    public void executeCommand() {
        try {
            Ui.showToUser(createDeck(arguments));
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
        }
    }

}
