package deck;

import static constants.ErrorMessages.DUPLICATE_DECK_NAME;
import static constants.ErrorMessages.EMPTY_DECK_NAME;
import static constants.ErrorMessages.MISSING_DECK_NAME;
import static constants.ErrorMessages.NO_DECK_TO_SWITCH;
import static constants.ErrorMessages.NO_SUCH_DECK;
import static constants.SuccessMessages.CREATE_DECK_SUCCESS;
import static constants.SuccessMessages.RENAME_DECK_SUCCESS;
import static constants.SuccessMessages.SWITCH_DECK_SUCCESS;

import java.util.HashMap;

import exceptions.FlashCLIillegalArgumentException;

public class DeckManager {
    public static Deck currentDeck;
    public static HashMap<String, Deck> decks = new HashMap<>();

    public static int getDeckSize() {
        return decks.size();
    }

    public static String createDeck(String arguments) throws FlashCLIillegalArgumentException {
        String newDeckName = arguments.trim();

        if (newDeckName.isEmpty()) {
            throw new FlashCLIillegalArgumentException(MISSING_DECK_NAME);
        }

        if (decks.containsKey(newDeckName)) {
            throw new FlashCLIillegalArgumentException(DUPLICATE_DECK_NAME);
        }

        decks.put(newDeckName, new Deck(newDeckName));
        return String.format(CREATE_DECK_SUCCESS, newDeckName, getDeckSize());
    }

    public static String renameDeck(String arguments) throws FlashCLIillegalArgumentException {
        String newDeckName = arguments.trim();
        if (newDeckName.isEmpty()) {
            throw new FlashCLIillegalArgumentException(EMPTY_DECK_NAME);
        }
        boolean isNewDeckNameSameAsCurrent = currentDeck.getName().equals(newDeckName);
        boolean isDeckNameDuplicate = decks.containsKey(newDeckName);
        if (!isNewDeckNameSameAsCurrent && isDeckNameDuplicate) {
            throw new FlashCLIillegalArgumentException(DUPLICATE_DECK_NAME);
        }

        String oldDeckName = currentDeck.getName();
        decks.remove(oldDeckName);
        Deck newDeck = new Deck(newDeckName);
        decks.put(newDeckName, newDeck);
        currentDeck = newDeck;
        return String.format(RENAME_DECK_SUCCESS, oldDeckName, currentDeck.getName());
    }

    public static String switchDeck(String arguments) throws FlashCLIillegalArgumentException{
        String deckName = arguments.trim();
        if (decks.isEmpty()) {
            throw new FlashCLIillegalArgumentException(NO_DECK_TO_SWITCH);
        }
        if (deckName.isEmpty()) {
            throw new FlashCLIillegalArgumentException(EMPTY_DECK_NAME);
        }
        currentDeck = decks.get(deckName);

        if (currentDeck == null) {
            throw new FlashCLIillegalArgumentException(NO_SUCH_DECK);
        }

        return String.format(SWITCH_DECK_SUCCESS, currentDeck.getName());
    }

}
