/**
 * The HelpCommandTest class contains JUnit tests for the HelpCommand class,
 * which is responsible for displaying information about various commands.
 * It tests different scenarios, including displaying general help, command-specific help,
 * and handling invalid commands.
 */

package seedu.duke.command;

import org.junit.jupiter.api.Test;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    /**
     * Tests the HelpCommand for printing the full list of commands.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_printFullList() throws DukeException  {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nCommand           Description\n" +
                "help              Shows a list of all the commands available to the user\n" +
                "in                Adds an income towards goal\n" +
                "out               Adds an expense for a category\n" +
                "delete            Delete a specific transaction based on the index in the list\n" +
                "list              Shows a list of all added transactions based on type\n" +
                "category          Create or delete a spending category\n" +
                "goal              Add or remove goals\n" +
                "export            Exports the transactions stored into a CSV File. " +
                "By Default, it will export ALL transactions\n" +
                "bye               Exits the program\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with an empty command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withEmptyCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help    ";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nCommand           Description\n" +
                "help              Shows a list of all the commands available to the user\n" +
                "in                Adds an income towards goal\n" +
                "out               Adds an expense for a category\n" +
                "delete            Delete a specific transaction based on the index in the list\n" +
                "list              Shows a list of all added transactions based on type\n" +
                "category          Create or delete a spending category\n" +
                "goal              Add or remove goals\n" +
                "export            Exports the transactions stored into a CSV File. " +
                "By Default, it will export ALL transactions\n" +
                "bye               Exits the program\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with an invalid command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withInvalidCommand() throws DukeException {
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

    /**
     * Tests the HelpCommand with a valid "in" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidInCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help in";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether of the income added is recurring.\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a case-sensitive command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_commandCaseSensitive() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help In";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether of the income added is recurring.\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with an all-uppercase command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_commandAllUpperCase() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help IN";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT /goal GOAL" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether of the income added is recurring.\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "out" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidOutCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help out";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: out DESCRIPTION /amount AMOUNT /category CATEGORY" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be deducted\n" +
                "/category         The spending category to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether of the expense added is recurring\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "delete" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidDeleteCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help delete";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: delete INDEX /type (in | out)\n" +
                "Option            Description\n" +
                "/type             To set whether it is a in or out transaction\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "list" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidListCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help list";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: list /type (in | out) [/goal GOAL] [/category CATEGORY]\n" +
                "Option            Description\n" +
                "/type             To set whether to display \"in\" or \"out\" transactions\n" +
                "/goal             The goal which it is classified under\n" +
                "/category         The spending category which it is classified under\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "help" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidHelpCommand() throws DukeException {
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

    /**
     * Tests the HelpCommand with a valid "bye" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidByeCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help bye";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: bye\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "goal" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidGoalCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help goal";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: goal /add NAME /amount AMOUNT\n" +
                "Usage: goal /remove NAME\n" +
                "Option            Description\n" +
                "/add              Name of goal to be added\n" +
                "/amount           The amount set for the goal\n" +
                "/remove           Name of goal to be removed\n\n", outputStream.toString());
    }

    /**
     * Tests the HelpCommand with a valid "category" command.
     *
     * @throws DukeException If an error occurs during command execution.
     */
    @Test
    void helpCommand_withValidCategoryCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help category";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: category /add NAME\n" +
                "Usage: category /remove NAME\n" +
                "Option            Description\n" +
                "/add              Name of spending category to be created\n" +
                "/remove           Name of spending cateogry to be deleted\n\n", outputStream.toString());
    }
}
