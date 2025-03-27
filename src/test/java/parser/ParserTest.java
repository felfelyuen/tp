package parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import command.Command;
import command.CommandCreate;
import deck.Deck;
import deck.DeckManager;
import exceptions.FlashCLIArgumentException;

public class ParserTest {
    private Deck deck;

    @BeforeEach
    void setUp() throws FlashCLIArgumentException {
        deck = new Deck("test1");
        DeckManager.currentDeck = deck;
    }

    @Test
    void parseInput_addFlashcard_success() throws FlashCLIArgumentException {
        String input = "add /q What is Java? /a A programming language.";
        parseAndAssertCommandType(input, CommandCreate.class);
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

}
