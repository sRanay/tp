package seedu.duke.command;

import seedu.duke.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(Ui ui) {
        //Does nothing as the ShutDownHook will print the Bye message
    }
}
