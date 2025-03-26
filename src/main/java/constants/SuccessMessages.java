package constants;
/**
 * class to hold all success messages
 */
public class SuccessMessages {
    public static final String CREATE_SUCCESS = "Added a new flashcard.\n" +
            "Question: %s\n" +
            "Answer: %s\n" +
            "You have %d flashcard(s) in your deck.";

    public static final String VIEW_QUESTION_SUCCESS = "Question of flashcard: %s\n" +
            "Question: %s\n";

    public static final String VIEW_ANSWER_SUCCESS = "Answer of flashcard: %s\n" +
            "Answer: %s\n";

    public static final String EDIT_SUCCESS = "Updated flashcard.\n" +
            "Edit Question: %s\n" +
            "Updated: %s\n" +
            "Edit Answer: %s\n" +
            "Updated: %s\n";

    public static final String LIST_SUCCESS = "List of flashcards:\n%s";

    public static final String DELETE_SUCCESS = "Deleted flashcard: %s";
    public static final String CREATE_DECK_SUCCESS = "Deck \"%s\" created, no of decks %d";
    public static final String SWITCH_DECK_SUCCESS = "Switched to deck \"%s\"";
}
