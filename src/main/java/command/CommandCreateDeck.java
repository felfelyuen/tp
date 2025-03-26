package command;

import static deck.DeckManager.createDeck;

import deck.Deck;
import ui.Ui;

public class CommandCreateDeck extends Command{
    public final String arguments;

    public CommandCreateDeck(String arguments) {
        this.arguments = arguments;
    }

    public void executeCommand() {
        Ui.showToUser(createDeck(arguments));
    }

}
