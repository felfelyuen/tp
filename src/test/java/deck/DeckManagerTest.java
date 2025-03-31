package deck;

import static constants.ErrorMessages.DUPLICATE_DECK_NAME;
import static constants.ErrorMessages.EMPTY_DECK_NAME;
import static constants.ErrorMessages.MISSING_DECK_NAME;
import static constants.ErrorMessages.NO_DECK_TO_SWITCH;
import static constants.ErrorMessages.NO_DECK_TO_VIEW;
import static constants.ErrorMessages.NO_SUCH_DECK;
import static constants.ErrorMessages.UNCHANGED_DECK_NAME;
import static constants.SuccessMessages.CREATE_DECK_SUCCESS;
import static constants.SuccessMessages.RENAME_DECK_SUCCESS;
import static constants.SuccessMessages.SWITCH_DECK_SUCCESS;
import static constants.SuccessMessages.VIEW_DECKS_SUCCESS;
import static deck.DeckManager.createDeck;
import static deck.DeckManager.currentDeck;
import static deck.DeckManager.decks;
import static deck.DeckManager.renameDeck;

import static deck.DeckManager.switchDeck;
import static deck.DeckManager.viewDecks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.FlashCLIArgumentException;

public class DeckManagerTest {

    @BeforeEach
    void setUp() {
        decks.clear();
    }

    /*
     * Tests for create deck command ==============================================================================
     */

    @Test
    void createDeck_simpleDeckName_success() throws FlashCLIArgumentException {
        String result = createDeck("test1");
        assertEquals(String.format(CREATE_DECK_SUCCESS, "test1", 1), result);
    }

    @Test
    void createDeck_emptyDeckName_throwsException() {
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            createDeck("");
        });
        assertEquals(MISSING_DECK_NAME, exception.getMessage());
    }

    @Test
    void createDeck_duplicateDeckName_throwsException() throws FlashCLIArgumentException {
        createDeck("test1");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            createDeck("test1");
        });
        assertEquals(DUPLICATE_DECK_NAME, exception.getMessage());
    }

    /*
     * Tests for rename deck command ==============================================================================
     */

    @Test
    void renameDeck_validNewName_success() throws FlashCLIArgumentException {
        createDeck("Coding Quality Concepts");
        currentDeck = decks.get("Coding Quality Concepts");
        String result = renameDeck("Code Quality");
        assertEquals(String.format(RENAME_DECK_SUCCESS, "Coding Quality Concepts", "Code Quality"), result);
    }

    @Test
    void renameDeck_emptyNewName_throwsException() throws FlashCLIArgumentException {
        createDeck("Coding Quality Concepts");
        currentDeck = decks.get("Coding Quality Concepts");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            renameDeck("");
        });
        assertEquals(EMPTY_DECK_NAME, exception.getMessage());
    }

    @Test
    void renameDeck_sameName_noChange() throws FlashCLIArgumentException {
        createDeck("Coding Quality Concepts");
        currentDeck = decks.get("Coding Quality Concepts");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            renameDeck("Coding Quality Concepts");
        });
        assertEquals(UNCHANGED_DECK_NAME, exception.getMessage());
    }

    @Test
    void renameDeck_duplicateName_throwsException() throws FlashCLIArgumentException {
        createDeck("Coding Quality Concepts");
        createDeck("Abstraction");
        currentDeck = decks.get("Abstraction");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            renameDeck("Coding Quality Concepts");
        });
        assertEquals(DUPLICATE_DECK_NAME, exception.getMessage());
    }

    /*
     * Tests for view decks command ==============================================================================
     */

    @Test
    void viewDecks_multipleDecks_success() throws FlashCLIArgumentException {
        createDeck("Class Diagrams");
        createDeck("Object Diagrams");
        createDeck("Sequence Diagrams");
        String expectedOutput = "1. Class Diagrams\n2. Object Diagrams\n3. Sequence Diagrams";
        String result = viewDecks();
        assertEquals(String.format(VIEW_DECKS_SUCCESS, expectedOutput), result);
    }

    @Test
    void viewDecks_noDecks_throwsException() {
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, DeckManager::viewDecks);
        assertEquals(NO_DECK_TO_VIEW, exception.getMessage());
    }

    /*
     * Tests for switch decks command ==============================================================================
     */

    @Test
    void switchDeck_validDeckName_success() throws FlashCLIArgumentException {
        createDeck("Quality Assurance");
        String result = switchDeck("Quality Assurance");
        assertEquals(String.format(SWITCH_DECK_SUCCESS, "Quality Assurance"), result);
    }

    @Test
    void switchDeck_noDecksAvailable_throwsException() {
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            switchDeck("Design Patterns");
        });
        assertEquals(NO_DECK_TO_SWITCH, exception.getMessage());
    }

    @Test
    void switchDeck_emptyDeckName_throwsException() throws FlashCLIArgumentException {
        createDeck("Test Coverage");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            switchDeck("");
        });
        assertEquals(NO_SUCH_DECK, exception.getMessage());
    }

    @Test
    void switchDeck_nonExistentDeck_throwsException() throws FlashCLIArgumentException {
        createDeck("Architecture");
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            switchDeck("Mona Lisa");
        });
        assertEquals(NO_SUCH_DECK, exception.getMessage());
    }

}
