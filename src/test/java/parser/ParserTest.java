package parser;

import static constants.ErrorMessages.DELETE_EMPTY_DECK_ERROR;
import static constants.ErrorMessages.EMPTY_DECK_NAME;
import static constants.ErrorMessages.NO_SUCH_DECK;
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
    void validateDeckExistsForDelete_emptyDeckList_throwsException() {
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("System Testing");
        });
        assertEquals(DELETE_EMPTY_DECK_ERROR, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_nonExistentDeck_throwsException() {
        decks.put("Unit Testing", new Deck("Unit Testing"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("Test Case Design");
        });
        assertEquals(NO_SUCH_DECK, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_emptyDeckName_throwsException() {
        decks.put("System Testing", new Deck("System Testing"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete(" ");
        });
        assertEquals(EMPTY_DECK_NAME, exception.getMessage());
    }

    @Test
    void validateDeckExistsForDelete_caseSensitiveDeletion_throwsException() {
        decks.put("Partitioning", new Deck("Partitioning"));
        FlashCLIArgumentException exception = assertThrows(FlashCLIArgumentException.class, () -> {
            validateDeckExistsForDelete("partitioning");
        });
        assertEquals(NO_SUCH_DECK, exception.getMessage());
    }
}