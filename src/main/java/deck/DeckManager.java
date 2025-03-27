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

/**
 * Manages a collection of Deck objects, allowing operations such as creation,
 * renaming, viewing, and switching between decks.
 *
 * <p>This class maintains a static reference to the current deck and a map of
 * all available decks. It provides methods to manage decks and enforce
 * constraints like unique deck names.</p>
 *
 * <p>Throws {@code FlashCLIArgumentException} for invalid input conditions.</p>
 */
public class DeckManager {
    public static Deck currentDeck;
    public static LinkedHashMap<String, Deck> decks = new LinkedHashMap<>();

    /**
     * Returns the number of decks currently stored.
     *
     * @return the total count of decks.
     */
    public static int getDeckSize() {
        return decks.size();
    }

    /**
     * Creates a new deck with the given name.
     *
     * @param arguments the name of the new deck.
     * @return a success message indicating the deck was created.
     * @throws FlashCLIArgumentException if the name is empty or already exists.
     */
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

    /**
     * Renames the current deck to a new name.
     *
     * @param arguments the new name for the current deck.
     * @return a success message indicating the deck was renamed.
     * @throws FlashCLIArgumentException if the name is empty or already exists.
     */
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

    /**
     * Returns a formatted list of all decks.
     *
     * @return a formatted string listing all available decks.
     * @throws FlashCLIArgumentException if there are no decks to view.
     */
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

    /**
     * Switches the current deck to the specified deck.
     *
     * @param arguments the name of the deck to switch to.
     * @return a success message indicating the active deck has changed.
     * @throws FlashCLIArgumentException if the deck does not exist or input is invalid.
     */
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

