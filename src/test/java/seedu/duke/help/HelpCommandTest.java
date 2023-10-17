package seedu.duke.help;

import org.junit.jupiter.api.Test;

import seedu.duke.command.HelpCommand;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    
    @Test
    void helpCommand_withInvalidCommand() {
        Parser parser = new Parser();
        Ui ui = new Ui();
        String userInput = "help asdasds";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.updateOutput(ui);
        assertEquals(true, command.gethelpList().isEmpty());
    }

    @Test
    void helpCommand_withValidCommand() {
        Parser parser = new Parser();
        Ui ui = new Ui();
        String userInput = "help in";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.updateOutput(ui);
        assertEquals(false, command.gethelpList().isEmpty());
    }
    
}
