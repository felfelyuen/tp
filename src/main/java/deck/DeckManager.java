//@@author Betahaxer
package deck;

import static constants.ErrorMessages.DECK_EMPTY_INPUT;
import static constants.ErrorMessages.DECK_INDEX_OUT_OF_BOUNDS;
import static constants.ErrorMessages.DUPLICATE_DECK_NAME;
import static constants.ErrorMessages.EMPTY_DECK_NAME;
import static constants.ErrorMessages.INVALID_INDEX_INPUT;
import static constants.ErrorMessages.MISSING_DECK_NAME;
import static constants.ErrorMessages.NO_DECK_TO_SWITCH;
import static constants.ErrorMessages.NO_DECK_TO_UNSELECT;
import static constants.ErrorMessages.NO_DECK_TO_VIEW;
import static constants.ErrorMessages.SAME_DECK_SELECTED;
import static constants.ErrorMessages.SEARCH_RESULT_EMPTY;
import static constants.ErrorMessages.UNCHANGED_DECK_NAME;
import static constants.ErrorMessages.UNSELECT_NO_ARGUMENTS_ALLOWED;
import static constants.ErrorMessages.VIEW_DECKS_NO_ARGUMENTS_ALLOWED;
import static constants.SuccessMessages.CREATE_DECK_SUCCESS;
import static constants.SuccessMessages.DELETE_DECK_SUCCESS;
import static constants.SuccessMessages.RENAME_DECK_SUCCESS;
import static constants.SuccessMessages.SEARCH_SUCCESS;
import static constants.SuccessMessages.SELECT_DECK_SUCCESS;
import static constants.SuccessMessages.UNSELECT_DECK_SUCCESS;
import static constants.SuccessMessages.VIEW_DECKS_SUCCESS;
import static storage.Saving.deleteDeckFile;
import static storage.Saving.renameDeckFile;
import static storage.Saving.saveDeck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import exceptions.EmptyListException;
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
    public static Deck currentDeck = null;
    public static LinkedHashMap<String, Deck> decks = new LinkedHashMap<>();
    private static final Logger logger = Logger.getLogger(DeckManager.class.getName());

    /**
     * Returns the number of decks currently stored.
     *
     * @return the total count of decks.
     */
    public static int getDeckSize() {
        return decks.size();
    }

    public static Deck getDeckByIndex(int index) {
        List<Map.Entry<String, Deck>> entryList = new ArrayList<>(decks.entrySet());
        return entryList.get(index).getValue();
    }

    public static void removeDeckByIndex(int index) {
        List<Map.Entry<String, Deck>> entryList = new ArrayList<>(decks.entrySet());
        Map.Entry<String, Deck> entryToRemove = entryList.get(index);
        decks.remove(entryToRemove.getKey());
    }

    public static void updateDeckByIndex(int index, String newKey, Deck newDeck) {
        List<Map.Entry<String, Deck>> entryList = new ArrayList<>(decks.entrySet());
        Map.Entry<String, Deck> entry = entryList.get(index);
        decks.remove(entry.getKey());
        decks.put(newKey, newDeck);
    }

    /**
     * Creates a new deck with the given name.
     *
     * @param arguments the name of the new deck.
     * @return a success message indicating the deck was created.
     * @throws FlashCLIArgumentException if the name is empty or already exists.
     */
    public static String createDeck(String arguments) throws FlashCLIArgumentException {
        logger.info("Entering createDeck method with arguments: " + arguments);

        String newDeckName = arguments.trim();

        if (newDeckName.isEmpty()) {
            logger.warning("Deck name is empty.");
            throw new FlashCLIArgumentException(MISSING_DECK_NAME);
        }

        if (decks.containsKey(newDeckName)) {
            logger.warning("Attempt to create duplicate deck: " + newDeckName);
            throw new FlashCLIArgumentException(DUPLICATE_DECK_NAME);
        }

        decks.put(newDeckName, new Deck(newDeckName));
        logger.info("Deck created successfully: " + newDeckName);
        try {
            saveDeck(newDeckName, decks.get(newDeckName));
        } catch (IOException e) {
            System.out.println("Error saving deck: " + e.getMessage());
        }

        assert decks.containsKey(newDeckName) : "Deck was not added successfully!";

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
        logger.info("Entering renameDeck method with arguments: " + arguments);

        String newDeckName = arguments.trim();
        validateNewDeckName(newDeckName);

        String oldDeckName = currentDeck.getName();
        logger.info("Renaming deck: " + oldDeckName + " -> " + newDeckName);

        renameDeckInCollection(oldDeckName, newDeckName);

        logger.info("Deck renamed successfully: " + oldDeckName + " -> " + newDeckName);
        return String.format(RENAME_DECK_SUCCESS, oldDeckName, currentDeck.getName());
    }

    /**
     * Validates the new deck name by ensuring it is not empty, unchanged, or a duplicate.
     *
     * @param newDeckName The new name for the deck.
     * @throws FlashCLIArgumentException If the new deck name is invalid.
     */
    private static void validateNewDeckName(String newDeckName) throws FlashCLIArgumentException {
        if (newDeckName.isEmpty()) {
            logger.warning("Deck name is empty.");
            throw new FlashCLIArgumentException(EMPTY_DECK_NAME);
        }

        boolean isNewDeckNameSameAsCurrent = currentDeck.getName().equals(newDeckName);
        boolean isDeckNameDuplicate = decks.containsKey(newDeckName);

        if (isNewDeckNameSameAsCurrent) {
            logger.warning("Deck name is unchanged");
            throw new FlashCLIArgumentException(UNCHANGED_DECK_NAME);
        }

        if (isDeckNameDuplicate) {
            logger.warning("Attempt to rename deck to an existing deck name: " + newDeckName);
            throw new FlashCLIArgumentException(DUPLICATE_DECK_NAME);
        }
    }

    /**
     * Renames the deck in the collection, updating the deck name and removing the old name.
     *
     * @param oldDeckName The old name of the deck.
     * @param newDeckName The new name of the deck.
     */
    private static void renameDeckInCollection(String oldDeckName, String newDeckName) {
        assert currentDeck != null : "A deck must be selected before renaming!";

        currentDeck.setName(newDeckName);

        LinkedHashMap<String, Deck> newMap = new LinkedHashMap<>();
        for (Map.Entry<String, Deck> entry : decks.entrySet()) {
            String key = entry.getKey();
            Deck value = entry.getValue();

            if (key.equals(oldDeckName)) {
                newMap.put(newDeckName, value);
            } else {
                newMap.put(key, value);
            }
        }
        decks = newMap;
        try {
            renameDeckFile(oldDeckName, newDeckName);
        } catch (IOException e) {
            System.out.println("Error renaming deck file: " + e.getMessage());
        }

        assert !decks.containsKey(oldDeckName) : "Old deck name still exists after renaming!";
        assert decks.containsKey(newDeckName) : "New deck name was not successfully added!";
        assert currentDeck.getName().equals(newDeckName) : "Current deck name was not updated properly!";
    }

    /**
     * Returns a formatted list of all decks.
     *
     * @return a formatted string listing all available decks.
     * @throws FlashCLIArgumentException if there are no decks to view.
     */
    public static String viewDecks(String arguments) throws FlashCLIArgumentException {
        logger.info("Entering viewDecks method");

        if (decks.isEmpty()) {
            logger.warning("Attempted to view decks, but no decks are available.");
            throw new FlashCLIArgumentException(NO_DECK_TO_VIEW);
        }

        if (!arguments.trim().isEmpty()) {
            logger.warning("Unexpected arguments provided to viewDecks: " + arguments);
            throw new FlashCLIArgumentException(VIEW_DECKS_NO_ARGUMENTS_ALLOWED);
        }

        String deckListString = buildDeckList();

        logger.info("Decks viewed successfully. Total decks: " + decks.size());
        return String.format(VIEW_DECKS_SUCCESS, deckListString);
    }

    /**
     * Builds a formatted string listing all deck names with their respective index.
     * Decks are separated by newline characters, and an assertion ensures the deck count matches.
     *
     * @return A string of numbered deck names.
     */
    private static String buildDeckList() {
        StringBuilder deckList = new StringBuilder();
        int listIndex = 1;

        for (Map.Entry<String, Deck> deck : decks.entrySet()) {
            deckList.append(listIndex).append(". ").append(deck.getKey());
            if (listIndex != decks.size()) {
                deckList.append("\n");
            }
            listIndex++;
        }

        int expectedDeckCount = decks.size();
        int actualDeckCount = listIndex - 1;
        assert actualDeckCount == expectedDeckCount :
                "Mismatch in deck count! Expected: " + expectedDeckCount + ", Found: " + actualDeckCount;

        return deckList.toString();
    }

    /**
     * Selects the deck with the index provided
     *
     * @param arguments the index of the deck to switch to.
     * @return a success message indicating the active deck has changed.
     * @throws FlashCLIArgumentException if the deck does not exist or input is invalid.
     */
    public static String selectDeck(String arguments) throws FlashCLIArgumentException {
        logger.info("Entering selectDeck method with arguments: " + arguments);

        if (decks.isEmpty()) {
            logger.warning("Attempted to switch decks, but no decks are available.");
            throw new FlashCLIArgumentException(NO_DECK_TO_SWITCH);
        }

        int listIndex = checkAndGetListIndex(arguments);
        Deck deckToSelect = getDeckByIndex(listIndex);

        if (currentDeck == deckToSelect) {
            throw new FlashCLIArgumentException(SAME_DECK_SELECTED);
        }

        currentDeck = deckToSelect;
        assert currentDeck != null : "Current deck should not be null after switching!";
        assert decks.containsKey(currentDeck.getName()) : "Switched deck does not exist in decks!";
        logger.info("Switched to deck: " + currentDeck.getName());
        return String.format(SELECT_DECK_SUCCESS, currentDeck.getName());
    }

    /**
     * Validates and converts the input arguments to a valid deck index.
     *
     * @param arguments the string input representing the deck index.
     * @return the zero-based index of the deck.
     * @throws FlashCLIArgumentException if the input is empty, not a valid integer, or otherwise invalid.
     */
    public static int checkAndGetListIndex(String arguments) throws FlashCLIArgumentException {
        String trimmedArguments = arguments.trim();
        if (trimmedArguments.isEmpty()) {
            throw new FlashCLIArgumentException(DECK_EMPTY_INPUT);
        }

        int listIndex;
        try {
            listIndex = Integer.parseInt(trimmedArguments) - 1;
        } catch (NumberFormatException e) {
            throw new FlashCLIArgumentException(INVALID_INDEX_INPUT);
        }

        if (listIndex < 0 || listIndex >= decks.size()) {
            throw new FlashCLIArgumentException(DECK_INDEX_OUT_OF_BOUNDS);
        }
        return listIndex;
    }

    /**
     * Deletes a deck by its name if the user has confirmed the deletion.
     * Ensures that the deck exists before removal and updates the current deck if necessary.
     *
     * @param listIndex the name of the deck to be deleted.
     * @return a success message indicating that the deck has been deleted.
     * @throws FlashCLIArgumentException if the deck list is empty, the deck name is missing,
     *                                   or the specified deck does not exist.
     */
    public static String deleteDeck(int listIndex) throws FlashCLIArgumentException {
        // checks if selected deck is the one that will be deleted
        Deck deckToDelete = getDeckByIndex(listIndex);
        if (currentDeck == deckToDelete) {
            currentDeck = null;
        }
        try {
            deleteDeckFile(deckToDelete.getName());
        } catch (IOException e) {
            System.out.println("Error deleting deck: " + e.getMessage());
        }
        removeDeckByIndex(listIndex);
        return String.format(DELETE_DECK_SUCCESS, deckToDelete.getName());
    }

    /**
     * Unselects the currently selected deck if one is active.
     *
     * @param arguments should be an empty string; no arguments are expected.
     * @return a success message indicating the deck has been unselected.
     * @throws FlashCLIArgumentException if no deck is currently selected or if unexpected arguments are provided.
     */
    public static String unselectDeck(String arguments) throws FlashCLIArgumentException {
        if (currentDeck == null) {
            throw new FlashCLIArgumentException(NO_DECK_TO_UNSELECT);
        }

        if (!arguments.trim().isEmpty()) {
            logger.warning("Unexpected arguments provided to unselect decks: " + arguments);
            throw new FlashCLIArgumentException(UNSELECT_NO_ARGUMENTS_ALLOWED);
        }

        String deckName = currentDeck.getName();
        currentDeck = null;
        return String.format(UNSELECT_DECK_SUCCESS, deckName);
    }
    /**
     * Searches for flashcards across all decks based on the provided question and/or answer arguments.
     *
     * @param arguments the search query in the format "q/QUESTION a/ANSWER"
     * @return formatted string of matching flashcards with their respective decks
     * @throws FlashCLIArgumentException if the search arguments are invalid
     * @throws EmptyListException        if there are no decks to search in / if there is no matching flashcards
     */
    //@@author ManZ9802
    public static String globalSearch(String arguments) throws FlashCLIArgumentException, EmptyListException {
        if (decks.isEmpty()) {
            throw new EmptyListException("No decks available for searching.");
        }

        StringBuilder result = new StringBuilder();
        int matchCount = 0;

        for (Map.Entry<String, Deck> deckEntry : decks.entrySet()) {
            String deckName = deckEntry.getKey();
            Deck deck = deckEntry.getValue();

            for (Flashcard flashcard : deck.searchFlashcardHelper(arguments)) {
                matchCount++;
                result.append(String.format("Deck: %s\nQuestion: %s\nAnswer: %s\n\n",
                        deckName,
                        flashcard.getQuestion(),
                        flashcard.getAnswer()));
            }
        }

        if (matchCount == 0) {
            throw new EmptyListException(SEARCH_RESULT_EMPTY);
        }

        return String.format(SEARCH_SUCCESS, result.toString().trim());
    }

}
