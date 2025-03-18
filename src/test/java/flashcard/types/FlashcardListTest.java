package flashcard.types;

import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static constants.ErrorMessages.VIEW_INVALID_INDEX;
import static constants.SuccessMessages.CREATE_SUCCESS;
import static constants.SuccessMessages.VIEW_ANSWER_SUCCESS;
import static constants.SuccessMessages.VIEW_QUESTION_SUCCESS;
import static constants.SuccessMessages.EDIT_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import command.Command;
import command.CommandCreate;
import command.CommandEdit;
import command.CommandViewQuestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlashcardListTest {

    @Test
    // happy path
    @BeforeEach
    void setUp() {
        FlashcardList.flashcards = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        FlashcardList.flashcards.clear();
    }

    @Test
    void createFlashcard_validInputs_success() {
        String input = "/q What is Java? /a A programming language.";
        String output = FlashcardList.createFlashcard(input);
        assertEquals(1, FlashcardList.flashcards.size());
        Flashcard createdFlashcard = FlashcardList.flashcards.get(0);
        assertEquals("What is Java?", createdFlashcard.getQuestion());
        assertEquals("A programming language.", createdFlashcard.getAnswer());
        assertEquals(String.format(CREATE_SUCCESS,
                "What is Java?", "A programming language.", 1), output);
    }

    @Test
    void createFlashcard_invalidQuestionField_illegalArgumentExceptionThrown() {
        try {
            String input = "/q /a A programming language.";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/a A programming language.";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q       /a A programming language.";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }
    }


    @Test
    void createFlashcard_invalidAnswerField_illegalArgumentExceptionThrown() {
        try {
            String input = "/q What is Java? ";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q What is Java? /a";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/q What is Java? /a     ";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }
    }

    @Test
    void createFlashcard_invalidQuestionAndAnswerField_illegalArgumentExceptionThrown() {
        try {
            String input = "  ";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/q /a";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_DESCRIPTION, e.getMessage());
        }

        try {
            String input = "/q";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/a";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_MISSING_FIELD, e.getMessage());
        }

        try {
            String input = "/a A programming language. /q What is Java?";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_INVALID_ORDER, e.getMessage());
        }

        try {
            String input = "/a /q";
            FlashcardList.createFlashcard(input);
        } catch (IllegalArgumentException e) {
            assertEquals(CREATE_INVALID_ORDER, e.getMessage());
        }
    }

    @Test
    void viewFlashcard_validInputs_success() {
        String createInput = "/q What is Java? /a A programming language.";
        Command createTest = new CommandCreate(createInput);
        createTest.executeCommand();
        String viewOutput = FlashcardList.viewFlashcardQuestion(1);
        assertEquals(1, FlashcardList.flashcards.size());
        assertEquals(String.format(VIEW_QUESTION_SUCCESS, 1, "What is Java?"), viewOutput);
    }

    @Test
    void viewFlashcard_invalidIndex_arrayIndexOutOfBoundsExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, FlashcardList.flashcards.size());
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
            assertEquals(1, FlashcardList.flashcards.size());
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
        String viewOutput = FlashcardList.viewFlashcardAnswer(1);
        assertEquals(1, FlashcardList.flashcards.size());
        assertEquals(String.format(VIEW_ANSWER_SUCCESS, 1, "A programming language."), viewOutput);
    }

    @Test
    void viewFlashcardAnswer_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, FlashcardList.flashcards.size());
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
            assertEquals(1, FlashcardList.flashcards.size());
            new CommandViewQuestion("72");
        } catch (NumberFormatException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }

    @Test
    void editFlashcard_validInputs_success() {
        String createInput = "/q What is Java? /a A programming language.";
        Command createTest = new CommandCreate(createInput);
        createTest.executeCommand();
        String editInput = "1 /q What is Python? /a A different programming language.";
        String editOutput = FlashcardList.editFlashcard(1, editInput);
        assertEquals(1, FlashcardList.flashcards.size());
        Flashcard editedFlashcard = FlashcardList.flashcards.get(0);
        assertEquals("What is Python?", editedFlashcard.getQuestion());
        assertEquals("A different programming language.", editedFlashcard.getAnswer());
        assertEquals(String.format(EDIT_SUCCESS,
                "What is Java?", "What is Python?",
                "A programming language.", "A different programming language."), editOutput);
    }

    @Test
    void editFlashcard_indexNotANumber_numberFormatExceptionThrown() {
        try {
            String createInput = "/q What is Java? /a A programming language.";
            Command createTest = new CommandCreate(createInput);
            createTest.executeCommand();
            assertEquals(1, FlashcardList.flashcards.size());
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
            assertEquals(1, FlashcardList.flashcards.size());
            new CommandEdit("4 /q What is Python? /a A different programming language.");
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals(VIEW_OUT_OF_BOUNDS, e.getMessage());
        }
    }
}
