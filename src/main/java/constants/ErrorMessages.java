package constants;

public class ErrorMessages {
    public static final String CREATE_USAGE = "Usage: add q/{QUESTION} a/{ANSWER}";
    public static final String CREATE_MISSING_FIELD = "Missing /q or /a in input.";
    public static final String CREATE_MISSING_DESCRIPTION = "Question or Answer cannot be empty.";
    public static final String CREATE_INVALID_ORDER = "/a Answer first /q Question later";
}
