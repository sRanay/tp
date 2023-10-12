package seedu.duke.command;

import seedu.duke.ui.Ui;

public class ExitCommand extends Command{

    @Override
    public void execute(Ui ui) {
        ui.printBye();
    }
}
