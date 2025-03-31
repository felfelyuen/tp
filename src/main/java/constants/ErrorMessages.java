package constants;
/**
 * class to hold all error messages
 */
public class ErrorMessages {
    public static final String CREATE_USAGE = "Usage: add /q {QUESTION} /a {ANSWER}";
    public static final String CREATE_MISSING_FIELD = "Missing /q or /a in input.";
    public static final String CREATE_MISSING_DESCRIPTION = "Question or Answer cannot be empty.";
    public static final String CREATE_INVALID_ORDER = "/a Answer first /q Question later";
    public static final String INVALID_INDEX_INPUT = "Input is not a number";
    public static final String INDEX_OUT_OF_BOUNDS = "Input is out of bounds of current list of flashcards";
    public static final String EDIT_USAGE = "Usage: edit {INDEX} /q {QUESTION} /a {ANSWER}";
    public static final String EMPTY_LIST = "Invalid: List is empty.";
    public static final String MISSING_DECK_NAME = "Deck must have a name.";
    public static final String NO_DECK_TO_SWITCH = "No decks available. Create a deck before selecting.";
    public static final String POSSIBLE_COMMANDS =
            "Possible commands are: user_guide, add, view_qn, view_ans, edit, list, delete, insert_code, quiz" +
                    "new, select, rename, decks, search and exit";
    public static final String NO_DECK_ERROR = "Select a deck first!";
    public static final String EMPTY_DECK_NAME = "Deck name must not be empty!";
    public static final String DUPLICATE_DECK_NAME = "Deck name already exists!";
    public static final String UNCHANGED_DECK_NAME = "Deck name is unchanged!";
    public static final String NO_SUCH_DECK = "Deck does not exist!";
    public static final String NO_DECK_TO_VIEW = "No decks available. Create a deck to start.";
    public static final String DELETE_EMPTY_DECK_ERROR = "No decks available. Unable to delete.";

    public static final String INSERT_MISSING_FIELD = "Missing /c in input.";
    public static final String INSERT_MISSING_CODE = "Code snippet cannot be empty.";
    public static final String INSERT_USAGE = "Usage: insert_code {INDEX} /c {CODE_SNIPPET}";
    public static final String CHANGE_IS_LEARNED_MISSING_INDEX = "No input index detected.";

    public static final String SEARCH_MISSING_FIELD = "Missing /q or /a in input.";
    public static final String SEARCH_RESULT_EMPTY = "No matching flashcards found.";
    public static final String SEARCH_EMPTY_DECK = "Deck is empty!";
}
