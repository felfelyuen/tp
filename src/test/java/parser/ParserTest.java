package parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import command.Command;
import command.CommandCreate;

public class ParserTest {

    @Test
    void parseInput_addFlashcard_success() {
        String input = "add /q What is Java? /a A programming language.";
        parseAndAssertCommandType(input, CommandCreate.class);
    }

    @Test
    void parseInput_invalidCommands_illegalArgumentExceptionThrown() {
        String input = "bunnyhop";

        assertThrows(IllegalArgumentException.class, () -> {
            Parser.parseInput(input);
        });
    }

    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = Parser.parseInput(input);
        assertTrue(expectedCommandClass.isInstance(result),
                "Expected command of type " + expectedCommandClass.getSimpleName()
                        + " but got " + result.getClass().getSimpleName());

        if (!expectedCommandClass.isInstance(result)) {
            throw new IllegalArgumentException("Unexpected command type.");
        }

        return expectedCommandClass.cast(result);
    }

}
