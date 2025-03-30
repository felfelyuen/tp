package command;

import ui.Ui;

import static constants.UserGuideMessages.USER_GUIDE_INFORMATION;

public class CommandUserGuide extends Command{

    @Override
    public void executeCommand() {
        Ui.showToUser(USER_GUIDE_INFORMATION);
    }
}
