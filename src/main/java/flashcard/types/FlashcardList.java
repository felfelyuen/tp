package flashcard.types;

import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static constants.SuccessMessages.CREATE_SUCCESS;
import static constants.SuccessMessages.VIEW_SUCCESS;

import java.util.ArrayList;

public class FlashcardList {
    public static ArrayList<Flashcard> flashcards = new ArrayList<>();

    public static String createFlashcard(String arguments) throws IllegalArgumentException {
        boolean containsAllArguments = arguments.contains("/q") && arguments.contains("/a");
        if (!containsAllArguments) {
            throw new IllegalArgumentException(CREATE_MISSING_FIELD);
        }
        int questionStart = arguments.indexOf("/q");
        int answerStart = arguments.indexOf("/a");

        if (questionStart > answerStart) {
            throw new IllegalArgumentException(CREATE_INVALID_ORDER);
        }

        String question = arguments.substring(questionStart + "/q".length(), answerStart).trim();
        String answer = arguments.substring(answerStart + "/a".length()).trim();
        if (question.isEmpty() || answer.isEmpty()) {
            throw new IllegalArgumentException(CREATE_MISSING_DESCRIPTION);
        }
        Flashcard newFlashcard = new Flashcard(question, answer);
        flashcards.add(newFlashcard);
        return String.format(CREATE_SUCCESS,
                newFlashcard.getQuestion(), newFlashcard.getAnswer(), flashcards.size());
    }

    public static String viewFlashcardQuestion (int index) throws ArrayIndexOutOfBoundsException{
        if (index <= 0 || index > flashcards.size()) {
            throw new ArrayIndexOutOfBoundsException(VIEW_OUT_OF_BOUNDS);
        }
        int arrayIndex = index - 1;
        Flashcard flashcardToView = flashcards.get(arrayIndex);
        String question = flashcardToView.getQuestion();
        return String.format(VIEW_SUCCESS, index, question);
    }
}
