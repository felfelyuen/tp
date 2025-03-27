package deck;

import static constants.ErrorMessages.DUPLICATE_DECK_NAME;
import static constants.ErrorMessages.EMPTY_DECK_NAME;
import static constants.ErrorMessages.MISSING_DECK_NAME;
import static constants.ErrorMessages.NO_DECK_TO_SWITCH;
import static constants.ErrorMessages.NO_DECK_TO_VIEW;
import static constants.ErrorMessages.NO_SUCH_DECK;
import static constants.SuccessMessages.CREATE_DECK_SUCCESS;
import static constants.SuccessMessages.RENAME_DECK_SUCCESS;
import static constants.SuccessMessages.SWITCH_DECK_SUCCESS;
import static constants.SuccessMessages.VIEW_DECKS_SUCCESS;

import java.util.LinkedHashMap;
import java.util.Map;

import exceptions.FlashCLIArgumentException;

public class DeckManager {
    public static Deck currentDeck;
    public static LinkedHashMap<String, Deck> decks = new LinkedHashMap<>();

    public static int getDeckSize() {
        return decks.size();
    }

    public static String createDeck(String arguments) throws FlashCLIArgumentException {
        String newDeckName = arguments.trim();

        if (newDeckName.isEmpty()) {
            throw new FlashCLIArgumentException(MISSING_DECK_NAME);
        }

        if (decks.containsKey(newDeckName)) {
            throw new FlashCLIArgumentException(DUPLICATE_DECK_NAME);
        }

        decks.put(newDeckName, new Deck(newDeckName));
        return String.format(CREATE_DECK_SUCCESS, newDeckName, getDeckSize());
    }

    public static String renameDeck(String arguments) throws FlashCLIArgumentException {
        String newDeckName = arguments.trim();
        if (newDeckName.isEmpty()) {
            throw new FlashCLIArgumentException(EMPTY_DECK_NAME);
        }
        boolean isNewDeckNameSameAsCurrent = currentDeck.getName().equals(newDeckName);
        boolean isDeckNameDuplicate = decks.containsKey(newDeckName);
        if (!isNewDeckNameSameAsCurrent && isDeckNameDuplicate) {
            throw new FlashCLIArgumentException(DUPLICATE_DECK_NAME);
        }

        String oldDeckName = currentDeck.getName();
        decks.remove(oldDeckName);
        Deck newDeck = new Deck(newDeckName);
        decks.put(newDeckName, newDeck);
        currentDeck = newDeck;
        return String.format(RENAME_DECK_SUCCESS, oldDeckName, currentDeck.getName());
    }

    public static String viewDecks() throws FlashCLIArgumentException {
        if (decks.isEmpty()) {
            throw new FlashCLIArgumentException(NO_DECK_TO_VIEW);
        }

        StringBuilder deckList = new StringBuilder();
        int listIndex = 1;
        for (Map.Entry<String, Deck> deck : decks.entrySet()) {
            deckList.append(listIndex).append(". ").append(deck.getKey());
            if (listIndex != decks.size()) {
                deckList.append("\n");
            }
            listIndex++;
        }
        return String.format(VIEW_DECKS_SUCCESS, deckList);
    }

    public static String switchDeck(String arguments) throws FlashCLIArgumentException {
        String deckName = arguments.trim();
        if (decks.isEmpty()) {
            throw new FlashCLIArgumentException(NO_DECK_TO_SWITCH);
        }
        if (deckName.isEmpty()) {
            throw new FlashCLIArgumentException(EMPTY_DECK_NAME);
        }
        currentDeck = decks.get(deckName);

        if (currentDeck == null) {
            throw new FlashCLIArgumentException(NO_SUCH_DECK);
        }

        return String.format(SWITCH_DECK_SUCCESS, currentDeck.getName());
    }

}
