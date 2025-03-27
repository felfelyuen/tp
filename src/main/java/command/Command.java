package command;

public abstract class Command {

    /**
     * Executes the command. The specific implementation of this method is provided
     * by subclasses and defines what happens when the command is triggered.
     */
    public abstract void executeCommand();
}
