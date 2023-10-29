package seedu.duke.command;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExportCommandTest {
    private static Parser parser = new Parser();
    private ByteArrayOutputStream outputStream;

    @AfterEach
    void clearStateManager() {
        File file = new File("Transactions.csv");
        file.delete();
        StateManager.clearStateManager();
    }
    @BeforeEach
    void populateStateManager() {
        try {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            parser.parse("goal /add car /amount 1000").execute(ui);
            parser.parse("goal /add ps5 /amount 1000").execute(ui);
            parser.parse("in part-time job /amount 1000 /goal car /date 29102023").execute(ui);
            parser.parse("in allowance /amount 500 /goal car /date 29102023").execute(ui);
            parser.parse("in sell stuff /amount 50 /goal ps5 /date 29102023").execute(ui);
            parser.parse("out buy dinner /amount 15 /category food /date 29102023").execute(ui);
            parser.parse("out popmart /amount 12 /category toy /date 29102023").execute(ui);
            parser.parse("out grab /amount 20 /category transport /date 29102023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void exportSuccessful() throws DukeException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Transaction Data extracted\n", outputStream.toString());
    }

    @Test
    public void exportFileWhenExist() throws DukeException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Transaction Data extracted\n", outputStream.toString());
    }

    @Test
    public void exportFileAllTransactions() throws DukeException, IOException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        File output = new File("Transactions.csv");
        File testFile = new File("./TestCSV/valid/Transactions-all.csv");
        assertEquals(true, FileUtils.contentEquals(output, testFile));
    }

    @Test
    public void exportFileInTransactions() throws DukeException, IOException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export /type in";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        File output = new File("Transactions.csv");
        File testFile = new File("./TestCSV/valid/Transactions-in.csv");
        assertEquals(true, FileUtils.contentEquals(output, testFile));
    }

    @Test
    public void exportFileOutTransactions() throws DukeException, IOException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export /type out";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        File output = new File("Transactions.csv");
        File testFile = new File("./TestCSV/valid/Transactions-out.csv");
        assertEquals(true, FileUtils.contentEquals(output, testFile));
    }

    @Test
    public void exportWrongType() throws DukeException {
        outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String userInput = "export /type adsad";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ExportCommand command = new ExportCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Wrong type entered. Please enter /type in, /type out or blank\n"
                ,outputStream.toString());
    }
}
