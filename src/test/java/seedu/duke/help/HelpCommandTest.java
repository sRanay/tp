package seedu.duke.help;

import org.junit.jupiter.api.Test;

import seedu.duke.command.HelpCommand;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    
    @Test
    void helpCommand_printFullList() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nCommand      Description\n" +
                "help         Shows a list of all the commands available to the user\n" +
                "in           Adds an income towards goal\n" +
                "out          Adds an expense for a category\n" +
                "delete       Delete a specific transaction based on the index in the list\n" +
                "list         Shows a list of all added transactions based on type\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_withEmptyCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help    ";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nCommand      Description\n" +
                "help         Shows a list of all the commands available to the user\n" +
                "in           Adds an income towards goal\n" +
                "out          Adds an expense for a category\n" +
                "delete       Delete a specific transaction based on the index in the list\n" +
                "list         Shows a list of all added transactions based on type\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_withInvalidCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help asdasds";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nNO SUCH COMMAND\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_withValidInCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help in";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]\n" +
                "Option       Description\n" +
                "/amount      Amount to be added\n" +
                "/goal        The goal to classify it under\n" +
                "/date        Date of the transaction\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_commandCaseSensitive() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help In";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]\n" +
                "Option       Description\n" +
                "/amount      Amount to be added\n" +
                "/goal        The goal to classify it under\n" +
                "/date        Date of the transaction\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_commandAllUpperCase() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help IN";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]\n" +
                "Option       Description\n" +
                "/amount      Amount to be added\n" +
                "/goal        The goal to classify it under\n" +
                "/date        Date of the transaction\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_withValidOutCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help out";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: out DESCRIPTION /amount AMOUNT /category CATEGORY [/date DATE in DDMMYYYY]\n" +
                "Option       Description\n" +
                "/amount      Amount to be deducted\n" +
                "/category    The spending category to classify it under\n" +
                "/date        Date of the transaction\n\n", outputStream.toString());
    }

    @Test
    void helpCommand_withValidDeleteCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help delete";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: delete INDEX /type (in | out)\n" +
                "Option       Description\n" +
                "/type        To set whether it is a in or out transaction\n\n", outputStream.toString());
    }
    
    @Test
    void helpCommand_withValidListCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help list";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: list /type (in | out) [/goal GOAL] [/category CATEGORY]\n" +
                "Option       Description\n" +
                "/type        To set whether it is a in or out transaction\n" +
                "/goal        The goal which it is classify under\n" +
                "/category    The spending category it is classify under\n\n", outputStream.toString());
    }
    
    @Test
    void helpCommand_withValidHelpCommand() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help help";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: help\n\n", outputStream.toString());
    }
}
