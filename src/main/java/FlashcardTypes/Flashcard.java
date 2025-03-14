package FlashcardTypes;

public class Flashcard {
    protected String question;
    protected String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return String.format("Question: %s\n" +
                "Answers: %s", this.question, this.answer);
    }
}
