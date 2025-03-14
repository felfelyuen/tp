package FlashcardTypes;

import static Constants.ErrorMessages.CREATE_USAGE;

import java.util.ArrayList;

import Exceptions.IllegalArgumentException;
import Ui.Ui;

public class FlashcardList {
    public static ArrayList<Flashcard> flashcards = new ArrayList<>();

    public static void createFlashcard(String arguments) {
        try {
            boolean containsAllArguments = arguments.contains("/q") && arguments.contains("/a");
            if (!containsAllArguments) {
                throw new IllegalArgumentException("Missing /q or /a in input.");
            }
            int questionStart = arguments.indexOf("/q");
            int answerStart = arguments.indexOf("/a");

            String question = arguments.substring(questionStart + "/q".length(), answerStart).trim();
            String answer = arguments.substring(answerStart + "/a".length()).trim();
            if (question.isEmpty() || answer.isEmpty()) {
                throw new IllegalArgumentException("Question or Answer cannot be empty.");
            }
            Flashcard newFlashcard = new Flashcard(question, answer);
            flashcards.add(newFlashcard);
            Ui.showToUser(String.format("Added a new flashcard.\n" +
                    "Question: %s\n" +
                    "Answer: %s\n" +
                    "You have %d flashcard(s) in your deck.", newFlashcard.getQuestion(), newFlashcard.getAnswer(), flashcards.size()));

        } catch (Exceptions.IllegalArgumentException e) {
            Ui.showError(e.getMessage());
            Ui.showError(CREATE_USAGE);
        }
    }
}
