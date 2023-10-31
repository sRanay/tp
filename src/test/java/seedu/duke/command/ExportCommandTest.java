package seedu.duke.command;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
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
    void removeFile() {
        File file = new File("Transactions.csv");
        file.delete();
    }
    @Nested
    class ExportCommandOutput {
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
    }

    @Nested
    class TypeIn {
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

        @AfterEach
        void clearStateManager() {
            File file = new File("Transactions.csv");
            file.delete();
            StateManager.clearStateManager();
        }

        @Test
        @EnabledOnOs({OS.WINDOWS})
        public void exportFileInTransactionsWindows() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Windows/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.MAC})
        public void exportFileInTransactionsMac() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.LINUX})
        public void exportFileInTransactionsLinux() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Linux/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeAll {
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

        @AfterEach
        void clearStateManager() {
            File file = new File("Transactions.csv");
            file.delete();
            StateManager.clearStateManager();
        }
        @Test
        @EnabledOnOs({OS.WINDOWS})
        public void  exportFileAllTransactionsWindows() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Windows/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.MAC})
        public void exportFileAllTransactionsMac() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.LINUX})
        public void exportFileAllTransactionsLinux() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Linux/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeOut {
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

        @AfterEach
        void clearStateManager() {
            File file = new File("Transactions.csv");
            file.delete();
            StateManager.clearStateManager();
        }

        @Test
        @EnabledOnOs({OS.WINDOWS})
        public void exportFileOutTransactionsWindows() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type out";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Windows/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.MAC})
        public void exportFileOutTransactionsMac() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type out";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        @Test
        @EnabledOnOs({OS.LINUX})
        public void exportFileOutTransactionsLinux() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export /type out";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File("Transactions.csv");
            File testFile = new File("./TestCSV/Linux/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeError {
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

        @AfterEach
        void clearStateManager() {
            StateManager.clearStateManager();
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
}
