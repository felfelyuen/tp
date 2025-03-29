package command;

import exceptions.FlashCLIArgumentException;
import ui.Ui;

import static constants.ErrorMessages.*;
import static deck.DeckManager.currentDeck;

public class CommandInsertCode extends Command{
    private final String arguments;
    public CommandInsertCode(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void executeCommand() {
        try {
            int index = Integer.parseInt(arguments.split(" ", 2)[0]);
            String output = currentDeck.insertCodeSnippet(index, arguments);
            Ui.showToUser(output);
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.showError(VIEW_OUT_OF_BOUNDS);
        } catch (NumberFormatException e) {
            Ui.showError(VIEW_INVALID_INDEX);
        } catch (FlashCLIArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(INSERT_USAGE);
        }
    }
}
