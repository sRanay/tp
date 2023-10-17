package seedu.duke.help;

import org.junit.jupiter.api.Test;

import seedu.duke.command.Command;
import seedu.duke.command.HelpCommand;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    
    @Test
    void helpCommand_withInvalidCommand() {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("help asdasd");
        String commandWord = parser.getDescription("help asdasd");
        HelpCommand command = new HelpCommand(commandWord, args);
        assertEquals(true, command.gethelpList().isEmpty());
    }
    
}
