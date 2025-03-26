package flashcard.types;

import exceptions.EmptyListException;
import exceptions.FlashCLIillegalArgumentException;
import ui.Ui;

import static constants.ErrorMessages.CREATE_INVALID_ORDER;
import static constants.ErrorMessages.CREATE_MISSING_FIELD;
import static constants.ErrorMessages.CREATE_MISSING_DESCRIPTION;
import static constants.ErrorMessages.VIEW_OUT_OF_BOUNDS;
import static constants.ErrorMessages.EMPTY_LIST;
import static constants.QuizMessages.QUIZ_CORRECT;
import static constants.QuizMessages.QUIZ_END;
import static constants.QuizMessages.QUIZ_INCORRECT;
import static constants.QuizMessages.QUIZ_LAST_QUESTION;
import static constants.QuizMessages.QUIZ_QUESTIONS_LEFT;
import static constants.QuizMessages.QUIZ_START;
import static constants.SuccessMessages.CREATE_SUCCESS;
import static constants.SuccessMessages.DELETE_SUCCESS;
import static constants.SuccessMessages.VIEW_ANSWER_SUCCESS;
import static constants.SuccessMessages.VIEW_QUESTION_SUCCESS;
import static constants.SuccessMessages.EDIT_SUCCESS;
import static constants.SuccessMessages.LIST_SUCCESS;

import java.util.ArrayList;
import java.util.logging.Logger;

public class FlashcardList {
    public static ArrayList<Flashcard> flashcards = new ArrayList<>();

    /**
     * Creates a new flashcard
     *
     * <p>The arguments must contain both a question (denoted by "/q") and an answer (denoted by "/a").
     * The question must appear before the answer in the input string.</p>
     *
     * @param arguments A string with the flashcard details
     * @return A success message indicating the flashcard has been created.
     * @throws FlashCLIillegalArgumentException If required fields are missing,
     *         the question and answer are in the wrong order, or either field is empty.
     */
    private static final Logger logger = Logger.getLogger(FlashcardList.class.getName());

    public static String createFlashcard(String arguments) throws FlashCLIillegalArgumentException {
        logger.info("Starting to create a flashcard with arguments: " + arguments);

        boolean containsAllArguments = arguments.contains("/q") && arguments.contains("/a");
        if (!containsAllArguments) {
            logger.warning("Missing required fields: /q or /a");
            throw new FlashCLIillegalArgumentException(CREATE_MISSING_FIELD);
        }

        int questionStart = arguments.indexOf("/q");
        int answerStart = arguments.indexOf("/a");

        assert questionStart >= 0 : "Index of /q should be valid";
        assert answerStart >= 0 : "Index of /a should be valid";

        logger.fine("Index of /q: " + questionStart + ", Index of /a: " + answerStart);

        if (questionStart > answerStart) {
            logger.warning("Invalid order: /q comes after /a");
            throw new FlashCLIillegalArgumentException(CREATE_INVALID_ORDER);
        }

        assert questionStart < answerStart : "Question should come before answer in arguments";

        String question = arguments.substring(questionStart + "/q".length(), answerStart).trim();
        String answer = arguments.substring(answerStart + "/a".length()).trim();

        if (question.isEmpty() || answer.isEmpty()) {
            logger.warning("Missing description: question or answer is empty");
            throw new FlashCLIillegalArgumentException(CREATE_MISSING_DESCRIPTION);
        }

        Flashcard newFlashcard = new Flashcard(question, answer);
        flashcards.add(newFlashcard);

        logger.info("Successfully created a flashcard: Question: " + question + ", Answer: " + answer);
        return String.format(CREATE_SUCCESS,
                newFlashcard.getQuestion(), newFlashcard.getAnswer(), flashcards.size());
    }

    /**
     * Views the flashcard question
     *
     * @param index index of flashcard to view
     * @return the question in the format of VIEW_QUESTION_SUCCESS
     * @throws ArrayIndexOutOfBoundsException if the index is outside of list size
     */
    public static String viewFlashcardQuestion(int index) throws ArrayIndexOutOfBoundsException {
        if (index <= 0 || index > flashcards.size()) {
            throw new ArrayIndexOutOfBoundsException(VIEW_OUT_OF_BOUNDS);
        }
        int arrayIndex = index - 1;
        Flashcard flashcardToView = flashcards.get(arrayIndex);
        assert flashcardToView != null : "flashcard object should not be null";
        String question = flashcardToView.getQuestion();
        assert !question.isEmpty() : "Question should not be empty when viewing flashcards";
        return String.format(VIEW_QUESTION_SUCCESS, index, question);
    }

    /**
     * Views flashcard answer
     * @param index index of flashcard
     * @return the answer in the format of VIEW_ANSWER_SUCCESS
     * @throws ArrayIndexOutOfBoundsException if the index is outside of list size
     */
    public static String viewFlashcardAnswer(int index) throws ArrayIndexOutOfBoundsException {
        if (index <= 0 || index > flashcards.size()) {
            throw new ArrayIndexOutOfBoundsException(VIEW_OUT_OF_BOUNDS);
        }
        int arrayIndex = index - 1;
        Flashcard flashcardToView = flashcards.get(arrayIndex);
        assert flashcardToView != null : "flashcard object should not be null";
        String answer = flashcardToView.getAnswer();
        assert !answer.isEmpty() : "Answer should not be empty when viewing flashcards";
        return String.format(VIEW_ANSWER_SUCCESS, index, answer);
    }

    /**
     * Edits the flashcard
     *
     * @param index     index of flashcard to view
     * @param arguments user inputs containing updated question and answer
     * @return the updated flashcard in the format of EDIT_SUCCESS
     * @throws ArrayIndexOutOfBoundsException if the index is outside of list size
     */
    public static String editFlashcard(int index, String arguments)
            throws ArrayIndexOutOfBoundsException,
            FlashCLIillegalArgumentException {
        boolean containsAllArguments = arguments.contains("/q") && arguments.contains("/a");
        if (!containsAllArguments) {
            throw new FlashCLIillegalArgumentException(CREATE_MISSING_FIELD);
        }
        int questionStart = arguments.indexOf("/q");
        int answerStart = arguments.indexOf("/a");

        if (questionStart > answerStart) {
            throw new FlashCLIillegalArgumentException(CREATE_INVALID_ORDER);
        }

        String updatedQuestion = arguments.substring(questionStart + "/q".length(), answerStart).trim();
        String updatedAnswer = arguments.substring(answerStart + "/a".length()).trim();
        if (updatedQuestion.isEmpty() || updatedAnswer.isEmpty()) {
            throw new FlashCLIillegalArgumentException(CREATE_MISSING_DESCRIPTION);
        }
        Flashcard updatedFlashcard = new Flashcard(updatedQuestion, updatedAnswer);

        if (index <= 0 || index > flashcards.size()) {
            throw new ArrayIndexOutOfBoundsException(VIEW_OUT_OF_BOUNDS);
        }
        int arrayIndex = index - 1;

        Flashcard oldFlashcard = flashcards.get(arrayIndex);
        String oldQuestion = oldFlashcard.getQuestion();
        String oldAnswer = oldFlashcard.getAnswer();
        flashcards.set(arrayIndex, updatedFlashcard);
        return String.format(EDIT_SUCCESS,
                oldQuestion, updatedQuestion, oldAnswer, updatedAnswer);
    }

    /**
     * lists out the questions of the flashcards
     * @return list of questions in the format of LIST_SUCCESS
     * @throws EmptyListException if the list is empty
     */
    public static String listFlashcards() throws EmptyListException {
        if (flashcards.isEmpty()) {
            throw new EmptyListException(EMPTY_LIST);
        }

        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Flashcard question : flashcards) {
            String currentQuestion = question.getQuestion();
            list.append(i).append(". ").append(currentQuestion);
            if (i != flashcards.size()) {
                list.append("\n");
            }
            i++;
        }

        return String.format(LIST_SUCCESS, list);
    }

    /**
     * Deletes the flashcard
     * @param index index of flashcard
     * @return the flashcard details in the format of DELETE_SUCCESS
     * @throws ArrayIndexOutOfBoundsException if the index is outside of list size
     */
    public static String deleteFlashcard(int index) throws ArrayIndexOutOfBoundsException {
        if (index <= 0 || index > flashcards.size()) {
            throw new ArrayIndexOutOfBoundsException(VIEW_OUT_OF_BOUNDS);
        }
        int arrayIndex = index - 1;
        Flashcard flashcardToDelete = flashcards.get(arrayIndex);
        assert flashcardToDelete != null : "flashcard object should not be null";
        flashcards.remove(arrayIndex);
        return String.format(DELETE_SUCCESS, flashcardToDelete);
    }

    public static void quizFlashcards() throws EmptyListException {
        if (flashcards.isEmpty()) {
            throw new EmptyListException(EMPTY_LIST);
        }

        //SHUFFLE THE FLASHCARDS AND PUT THEM IN QUEUE HERE

        Ui.showToUser(QUIZ_START);
        int last_index = flashcards.size() - 1;
        for (int i = 0; i < last_index; i++) {
            //handles all questions except last one
            int questions_left = flashcards.size() - i;
            Ui.showToUser(String.format(QUIZ_QUESTIONS_LEFT, questions_left));
            handleQuizForFlashcard(i);
        }
        Ui.showToUser(QUIZ_LAST_QUESTION);
        handleQuizForFlashcard(last_index);

        //HANDLE TIMER HERE
        int timer_amount = 5; //arbitrary value for now
        
        Ui.showToUser(String.format(QUIZ_END, timer_amount));
    }

    public static void handleQuizForFlashcard (int index) {
        Flashcard index_card = flashcards.get(index);
        Ui.showToUser(index_card.getQuestion());
        String userAnswer = Ui.getUserCommand();
        if (userAnswer.equals(index_card.getAnswer())) {
            Ui.showToUser(QUIZ_CORRECT);
        } else {
            Ui.showToUser(QUIZ_INCORRECT);
        }
    }
}
