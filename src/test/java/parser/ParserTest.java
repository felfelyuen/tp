package parser;

import static constants.ErrorMessages.DECK_EMPTY_INPUT;
import static constants.ErrorMessages.DECK_INDEX_OUT_OF_BOUNDS;
import static constants.ErrorMessages.DELETE_EMPTY_DECK_ERROR;
import static constants.ErrorMessages.INVALID_INDEX_INPUT;
import static deck.DeckManager.decks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static parser.Parser.validateDeckExistsForDelete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import command.Command;
import command.CommandCreateFlashcard;
import deck.Deck;
import deck.DeckManager;
import exceptions.FlashCLIArgumentException;

public class ParserTest {
    private Deck deck;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        decks.clear();
        if (testInfo.getDisplayName().startsWith("parseInput")) {
            deck = new Deck("test1");
            DeckManager.currentDeck = deck;
        }
    }

    @Test
    void parseInput_addFlashcard_success() throws FlashCLIArgumentException {
        String input = "add /q What is Java? /a A programming language.";
        parseAndAssertCommandType(input, CommandCreateFlashcard.class);
    }

    @Test
    void parseInput_invalidCommands_flashCLIArgumentExceptionThrown() {
        String input = "bunnyhop";

        assertThrows(FlashCLIArgumentException.class, () -> {
            Parser.parseInput(input);
        });
    }

    private <T extends Command> void parseAndAssertCommandType(String input, Class<T> expectedCommandClass)
            throws FlashCLIArgumentException {
        final Command result = Parser.parseInput(input);

        assertTrue(expectedCommandClass.isInstance(result),
                "Expected command of type " + expectedCommandClass.getSimpleName()
                        + " but got " + result.getClass().getSimpleName());

        if (!expectedCommandClass.isInstance(result)) {
            throw new IllegalArgumentException("Unexpected command type.");
        }
    }

    @Test
    void validateDeckExistsForDelete_nonIntegerInput_throwsException() {
        decks.put("Test Deck", new Deck("Test Deck"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("abc");
        });
        assertEquals(INVALID_INDEX_INPUT, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_negativeIndex_throwsException() {
        decks.put("Test Deck", new Deck("Test Deck"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("-1");
        });
        assertEquals(DECK_INDEX_OUT_OF_BOUNDS, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_emptyDeckList_throwsException() {
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("1");
        });
        assertEquals(DELETE_EMPTY_DECK_ERROR, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_nonExistentDeck_throwsException() {
        decks.put("Unit Testing", new Deck("Unit Testing"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("5");
        });
        assertEquals(DECK_INDEX_OUT_OF_BOUNDS, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_emptyDeckName_throwsException() {
        decks.put("System Testing", new Deck("System Testing"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete(" ");
        });
        assertEquals(DECK_EMPTY_INPUT, exception.getMessage());
    }

}
