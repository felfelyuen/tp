package deck;

/**
 * Represents a flashcard with a question and an answer.
 */
public class Flashcard {
    protected String question;
    protected String answer;

    /**
     * Constructs a Flashcard with the specified question and answer.
     *
     * @param question The question for the flashcard.
     * @param answer The answer to the flashcard question.
     */
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the question of the flashcard.
     *
     * @return The flashcard question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer of the flashcard.
     *
     * @return The flashcard answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns a string representation of the flashcard.
     *
     * @return A formatted string containing the question and answer.
     */
    @Override
    public String toString() {
        return String.format("Question: %s\n" +
                "Answer: %s", this.question, this.answer);
    }
}

