package command;

import exceptions.EmptyListException;
import exceptions.FlashCLIArgumentException;
import ui.Ui;

import static deck.DeckManager.currentDeck;
import static deck.DeckManager.globalSearch;

public class CommandSearchFlashcard extends Command {
    private final String arguments;
    public CommandSearchFlashcard(String arguments) {
        this.arguments = arguments;
    }
    /**
     * Executes the command. The specific implementation of this method is provided
     * by subclasses and defines what happens when the command is triggered.
     */
    @Override
    public void executeCommand() {
        try {
            String list;
            if (currentDeck == null) {
                list = globalSearch(arguments);
            } else {
                list = currentDeck.searchFlashcard(arguments);
            }
            Ui.showToUser(list);
        } catch (FlashCLIArgumentException | EmptyListException e) {
            Ui.showError(e.getMessage());
        }
    }
}
