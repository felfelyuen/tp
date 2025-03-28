package deck;

/**
 * Represents a flashcard with a question and an answer.
 */
public class Flashcard {
    protected int index;
    protected String question;
    protected String answer;

    /**
     * Constructs a Flashcard with the specified question and answer.
     *
     * @param question The question for the flashcard.
     * @param answer The answer to the flashcard question.
     */
    public Flashcard(int index, String question, String answer) {
        this.index = index;
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the index of the flashcard.
     *
     * @return The flashcard index in the deck
     */
    public int getIndex() {
        return index;
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

