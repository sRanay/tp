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
     * Test if the full list is printed correctly.
     * @throws DukeException if the command does not execute.
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
                "edit              Edits an existing transaction\n" +
                "summary           Shows the summarised total of transactions\n" +
                "bye               Exits the program\n\n", outputStream.toString());
    }

    /**
     * Test if it will print the full list with empty space.
     * @throws DukeException if the command does not execute.
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
                "edit              Edits an existing transaction\n" +
                "summary           Shows the summarised total of transactions\n" +
                "bye               Exits the program\n\n", outputStream.toString());
    }

    /**
     * Test if it will print out error message if a invalid command is entered.
     * @throws DukeException if the command cannot be executed.
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
     * Test if the help for in command work as intended.
     * @throws DukeException if the command cannot be executed.
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
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT [/goal GOAL]" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether the income added is recurring\n\n", outputStream.toString());
    }

    /**
     * Test if the command enter is case-insensitive will work as intended.
     * @throws DukeException if the command does not execute.
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
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT [/goal GOAL]" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether the income added is recurring\n\n", outputStream.toString());
    }

    /**
     * Test if the command enter is in uppercase will work as intended.
     * @throws DukeException if the command does not execute.
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
        assertEquals("\nUsage: in DESCRIPTION /amount AMOUNT [/goal GOAL]" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be added\n" +
                "/goal             The goal to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether the income added is recurring\n\n", outputStream.toString());
    }

    /**
     * Test if the help for out command work as intended.
     * @throws DukeException if the command cannot be executed.
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
        assertEquals("\nUsage: out DESCRIPTION /amount AMOUNT [/category CATEGORY]" +
                " [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]\n" +
                "Option            Description\n" +
                "/amount           Amount to be deducted\n" +
                "/category         The spending category to classify it under\n" +
                "/date             Date of the transaction\n" +
                "/recurrence       Indicates whether the expense added is recurring\n\n", outputStream.toString());
    }

    /**
     * Test if the help for delete command work as intended.
     * @throws DukeException if the command cannot be executed.
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
     * Test if the help for list command work as intended.
     * @throws DukeException if the command cannot be executed.
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
        assertEquals("\nUsage: list (goal | category)\n" +
                "Usage: list /type (in | out) [/goal GOAL] [/category CATEGORY] [/week] [/month]\n" +
                "Option            Description\n" +
                "/type             To set whether to display \"in\" or \"out\" transactions\n" +
                "/goal             The goal which it is classified under\n" +
                "/category         The spending category which it is classified under\n" +
                "/week             To filter the transactions to those in the current week\n" +
                "/month            To filter the transactions to those in the current month\n\n"
                , outputStream.toString());
    }

    /**
     * Test if the help for help command work as intended.
     * @throws DukeException if the command cannot be executed.
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
     * Test if the help for bye command work as intended.
     * @throws DukeException if the command cannot be executed.
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
     * Test if the help for goal command work as intended.
     * @throws DukeException if the command cannot be executed.
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
     * Test if the help for category command work as intended.
     * @throws DukeException if the command cannot be executed.
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
                "/remove           Name of spending category to be deleted\n\n", outputStream.toString());
    }

    /**
     * Test if the help for edit command work as intended.
     * @throws DukeException if the command cannot be executed.
     */
    @Test
    void helpCommand_withValidEditCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help edit";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: edit INDEX /type (in | out) (/description DESCRIPTION" +
                " | /amount AMOUNT | /goal GOAL | /category CATEGORY)\n" +
                "Option            Description\n" +
                "/type             To specify either in or out transaction to be edited\n"+
                "/description      New description to be specified\n" +
                "/amount           New amount to be specified\n" +
                "/goal             New goal to be specified\n" +
                "/category         New category to be specified\n\n", outputStream.toString());
    }

    /**
     * Test if the help for summary command work as intended.
     * @throws DukeException if the command cannot be executed.
     */
    @Test
    void helpCommand_withValidSummaryCommand() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "help summary";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        HelpCommand command = new HelpCommand(commandWord, args);
        command.execute(ui);
        assertEquals("\nUsage: summary /type (in | out) [/day] [/week] [/month]\n" +
                "Option            Description\n" +
                "/type             To specific either in or out transaction to be listed\n"+
                "/day              To filter transactions to those of current day\n" +
                "/week             To filter the transactions to those in the current week\n" +
                "/month            To filter the transactions to " +
                "those in the current month\n\n", outputStream.toString());
    }
}
