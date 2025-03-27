package deck;

import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static constants.ErrorMessages.VIEW_INVALID_INDEX;
import static constants.ErrorMessages.EMPTY_LIST;
import static constants.SuccessMessages.CREATE_SUCCESS;
import static constants.SuccessMessages.VIEW_ANSWER_SUCCESS;
import static constants.SuccessMessages.VIEW_QUESTION_SUCCESS;
import static constants.SuccessMessages.EDIT_SUCCESS;
import static constants.SuccessMessages.LIST_SUCCESS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import command.Command;
import command.CommandCreate;
import command.CommandDelete;
import command.CommandEdit;
import command.CommandViewQuestion;
import exceptions.EmptyListException;
import exceptions.FlashCLIillegalArgumentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlashcardListTest {
    private Deck newdeck;
    private ArrayList<Flashcard> flashcards;

    @BeforeEach
    void setUp() {
        this.newdeck = new Deck("test1");
        this.flashcards = newdeck.getFlashcards();
    }

    @AfterEach
    void tearDown() {
        this.flashcards.clear();
    }

    @Test
    void createFlashcard_validInputs_success() {
        String input = "/q What is Java? /a A programming language.";
        try {
            String output = Deck.createFlashcard(input);

            assertEquals(1, Deck.flashcards.size());
            Flashcard createdFlashcard = Deck.flashcards.get(0);
            assertEquals("What is Java?", createdFlashcard.getQuestion());
            assertEquals("A programming language.", createdFlashcard.getAnswer());
            assertEquals(String.format(CREATE_SUCCESS,
                "What is Java?", "A programming language.", 1), output);
        } catch (FlashCLIillegalArgumentException e) {
            fail("Unexpected FlashCLI_IllegalArgumentException was thrown: " + e.getMessage());
        }
    }

    @Test
    void createFlashcard_invalidQuestionField_flashCLIillegalArgumentExceptionThrown() {
        try {
            String input = "/q /a A programming language.";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/a A programming language.";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q       /a A programming language.";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }
    }


    @Test
    void createFlashcard_invalidAnswerField_illegalArgumentExceptionThrown() {
        try {
            String input = "/q What is Java? ";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q What is Java? /a";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/q What is Java? /a     ";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }
    }

    @Test
    void createFlashcard_invalidQuestionAndAnswerField_illegalArgumentExceptionThrown() {
        try {
            String input = "  ";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q /a";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/q";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/a";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/a A programming language. /q What is Java?";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_INVALID_ORDER, e.getMessage());
        }

        try {
            String input = "/a /q";
            Deck.createFlashcard(input);
        } catch (FlashCLIillegalArgumentException e) {
            assertEquals(CREATE_INVALID_ORDER, e.getMessage());
        }
    }

    @Test
    void viewFlashcardQuestion_validInputs_success() {
        String createInput = "/q What is Java? /a A programming language.";
        Command createTest = new CommandCreate(createInput);
        createTest.executeCommand();
        String viewOutput = Deck.viewFlashcardQuestion(1);
        assertEquals(1, Deck.flashcards.size());
        assertEquals(String.format(VIEW_QUESTION_SUCCESS, 1, "What is Java?"), viewOutput);
    }

    @Test
    void viewFlashcardQuestion_invalidIndex_arrayIndexOutOfBoundsExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandViewQuestion("3");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }

    @Test
    void viewFlashcard_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandViewQuestion("sjd");
        } catch (NumberFormatException e) {
            assertEquals(VIEW_INVALID_INDEX, e.getMessage());
        }
    }

    @Test
    void viewFlashcardAnswer_validInputs_success() {
        String createInput = "/q What is Java? /a A programming language.";
        Command createTest = new CommandCreate(createInput);
        createTest.executeCommand();
        String viewOutput = Deck.viewFlashcardAnswer(1);
        assertEquals(1, Deck.flashcards.size());
        assertEquals(String.format(VIEW_ANSWER_SUCCESS, 1, "A programming language."), viewOutput);
    }

    @Test
    void viewFlashcardAnswer_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandViewQuestion("sjd");
        } catch (NumberFormatException e) {
            assertEquals(VIEW_INVALID_INDEX, e.getMessage());
        }
    }

    @Test
    void viewFlashcardAnswer_invalidIndex_arrayIndexOutOfBoundsExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandViewQuestion("72");
        } catch (NumberFormatException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }

    @Test
    void editFlashcard_validInputs_success() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            String editInput = "1 /q What is Python? /a A different programming language.";
            String editOutput = Deck.editFlashcard(1, editInput);
            assertEquals(1, Deck.flashcards.size());
            Flashcard editedFlashcard = Deck.flashcards.get(0);
            assertEquals("What is Python?", editedFlashcard.getQuestion());
            assertEquals("A different programming language.", editedFlashcard.getAnswer());
            assertEquals(String.format(EDIT_SUCCESS,
                    "What is Java?", "What is Python?",
                    "A programming language.", "A different programming language."), editOutput);
        } catch (FlashCLIillegalArgumentException e) {
            fail("Unexpected FlashCLI_IllegalArgumentException was thrown: " + e.getMessage());
        }
    }

    @Test
    void editFlashcard_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandEdit("sjd /q What is Python? /a A different programming language.");
        } catch (NumberFormatException e) {
            assertEquals(VIEW_INVALID_INDEX, e.getMessage());
        }
    }

    @Test
    void editFlashcard_invalidIndex_arrayIndexOutOfBoundsExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, Deck.flashcards.size());
            new CommandEdit("4 /q What is Python? /a A different programming language.");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }

    @Test
    void listFlashcards_validInputs_success() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            createTest.executeCommand();
            createTest.executeCommand();
            String listOutput = Deck.listFlashcards();
            assertEquals(3, Deck.flashcards.size());
            assertEquals(String.format(LIST_SUCCESS,
                    "1. What is Java?\n2. What is Java?\n3. What is Java?"),
                    listOutput);
        } catch (EmptyListException e) {
            fail("Unexpected EmptyListException was thrown: " + e.getMessage());
        }
    }

    @Test
    void listFlashcards_emptyList_emptyListExceptionThrown() {
        try {
            String listOutput = Deck.listFlashcards();
            assertEquals(String.format(LIST_SUCCESS,
                            "1. What is Java?\n2. What is Java?\n3. What is Java?"),
                    listOutput);
        } catch (EmptyListException e) {
            assertEquals(EMPTY_LIST, e.getMessage());
        }
    }

    @Test
    void deleteFlashcard_validInputs_success() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            createTest.executeCommand();
            String listOutput = Deck.listFlashcards();
            assertEquals(2, Deck.flashcards.size());
            assertEquals(String.format(LIST_SUCCESS, "1. What is Java?\n2. What is Java?"), listOutput);
            Command deleteTest = new CommandDelete("1");
            deleteTest.executeCommand();
            String listAfterDeleteOutput = Deck.listFlashcards();
            assertEquals(1, Deck.flashcards.size());
            assertEquals(String.format(LIST_SUCCESS, "1. What is Java?"), listAfterDeleteOutput);
        } catch (EmptyListException e) {
            fail("Unexpected EmptyListException was thrown: " + e.getMessage());
        }
    }

    @Test
    void deleteFlashcard_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            createTest.executeCommand();
            String listOutput = Deck.listFlashcards();
            assertEquals(2, Deck.flashcards.size());
            assertEquals(String.format(LIST_SUCCESS, "1. What is Java?\n2. What is Java?"), listOutput);
            Command deleteTest = new CommandDelete("sdsd");
            deleteTest.executeCommand();
        } catch (EmptyListException e) {
            fail("Unexpected EmptyListException was thrown: " + e.getMessage());
        } catch (NumberFormatException e) {
            assertEquals(VIEW_INVALID_INDEX, e.getMessage());
        }
    }

    @Test
    void deleteFlashcard_invalidIndex_arrayIndexOutOfBoundsExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            createTest.executeCommand();
            String listOutput = Deck.listFlashcards();
            assertEquals(2, Deck.flashcards.size());
            assertEquals(String.format(LIST_SUCCESS, "1. What is Java?\n2. What is Java?"), listOutput);
            Command deleteTest = new CommandDelete("72");
            deleteTest.executeCommand();
        } catch (EmptyListException e) {
            fail("Unexpected EmptyListException was thrown: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }
}
