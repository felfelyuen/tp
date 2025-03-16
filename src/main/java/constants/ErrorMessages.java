package constants;

public class ErrorMessages {
    public static final String CREATE_USAGE = "Usage: add /q{QUESTION} /a{ANSWER}";
    public static final String CREATE_MISSING_FIELD = "Missing /q or /a in input.";
    public static final String CREATE_MISSING_DESCRIPTION = "Question or Answer cannot be empty.";
    public static final String CREATE_INVALID_ORDER = "/a Answer first /q Question later";

    public static final String VIEW_INVALID_INDEX = "Input is not a number";
    public static final String VIEW_OUT_OF_BOUNDS = "Input is out of bounds of current list of flashcards";
}
