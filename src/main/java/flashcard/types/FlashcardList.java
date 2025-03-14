package flashcard.types;

import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.SuccessMessages.CREATE_SUCCESS;

import java.util.ArrayList;

import ui.Ui;

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
}
