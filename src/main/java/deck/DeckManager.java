package deck;

import static constants.ErrorMessages.DECK_INDEX_OUT_OF_BOUNDS;
import static constants.ErrorMessages.EMPTY_DECK_NUMBER;
import static constants.ErrorMessages.INVALID_DECK_INDEX;
import static constants.ErrorMessages.MISSING_DECK_NAME;
import static constants.ErrorMessages.NO_DECK_TO_SWITCH;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static constants.SuccessMessages.CREATE_DECK_SUCCESS;
import static constants.SuccessMessages.SWITCH_DECK_SUCCESS;
import static constants.SuccessMessages.VIEW_QUESTION_SUCCESS;
import static java.lang.Integer.parseInt;

import java.util.ArrayList;

import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

public class DeckManager {
    public static Deck currentDeck;
    public static ArrayList<Deck> decks = new ArrayList<>();

    public static int getDeckSize() {
        return decks.size();
    }

    public static String createDeck(String arguments) throws FlashCLIillegalArgumentException {
        if (arguments.trim().isEmpty()) {
            throw new FlashCLIillegalArgumentException(MISSING_DECK_NAME);
        }
        String deckName = arguments.trim();
        decks.add(new Deck(deckName));
        return String.format(CREATE_DECK_SUCCESS, deckName, getDeckSize());
    }

    public static String switchDeck(String arguments) throws FlashCLIillegalArgumentException{
        try {
            if (decks.isEmpty()) {
                throw new FlashCLIillegalArgumentException(NO_DECK_TO_SWITCH);
            }
            if (arguments.trim().isEmpty()) {
                throw new FlashCLIillegalArgumentException(EMPTY_DECK_NUMBER);
            }
            int index = parseInt(arguments) - 1;
            currentDeck = decks.get(index);
            return String.format(SWITCH_DECK_SUCCESS, currentDeck.getName());
        } catch (NumberFormatException e) {
            throw new FlashCLIillegalArgumentException(INVALID_DECK_INDEX);
        } catch (IndexOutOfBoundsException e) {
            throw new FlashCLIillegalArgumentException(DECK_INDEX_OUT_OF_BOUNDS);
        }
    }

}
