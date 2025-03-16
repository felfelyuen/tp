package flashcard.types;

import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.SuccessMessages.CREATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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




}
