package command;

import exceptions.EmptyListException;
import ui.Ui;

import static deck.DeckManager.currentDeck;

public class CommandQuizFlashcards extends Command{
    //arguments likely to be utilised when decks are updated
    private final String arguments;
    public CommandQuizFlashcards(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void executeCommand() {
        try {
            currentDeck.quizFlashcards();
        } catch (EmptyListException e) {
            Ui.showError(e.getMessage());
        }
    }
}
