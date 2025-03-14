package Commands;

import static FlashcardTypes.FlashcardList.createFlashcard;

public class CommandCreate extends Command{
    private final String arguments;
    public CommandCreate(String arguments) {
        this.arguments = arguments;
    }
    public void executeCommand() {
        createFlashcard(this.arguments);
    }
}
