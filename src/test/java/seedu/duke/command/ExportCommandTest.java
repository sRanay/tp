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
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExportCommandTest {

    private static final String TEST_DIR = "./TestFiles";
    private static final String GOAL_STORAGE_FILENAME = TEST_DIR + "/goal-store.csv";
    private static final String CATEGORY_STORAGE_FILENAME = TEST_DIR + "/category-store.csv";
    private static final String INCOME_STORAGE_FILENAME = TEST_DIR + "/income-store.csv";
    private static final String EXPENSE_STORAGE_FILENAME = TEST_DIR + "/expense-store.csv";
    private static final String EXPORT_STORAGE_FILENAME = TEST_DIR + "/Transactions.csv";
    private static Parser parser = new Parser();
    private ByteArrayOutputStream outputStream;
    private Storage storage;

    /**
     * Initialise the storage object before each test.
     */
    @BeforeEach
    void initialise() {
        File directory = new File(TEST_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
        storage = new Storage(GOAL_STORAGE_FILENAME, CATEGORY_STORAGE_FILENAME, INCOME_STORAGE_FILENAME,
                EXPENSE_STORAGE_FILENAME, EXPORT_STORAGE_FILENAME);
    }

    /**
     * Remove the Transaction file after each test
     */
    @AfterEach
    void removeFile() {
        File file = new File(EXPORT_STORAGE_FILENAME);
        file.delete();
    }

    @Nested
    class ExportCommandOutput {
        /**
         * Reset the state to original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(EXPORT_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Populate the state manager with values before the test.
         */
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
                parser.parse("out buy dinner /amount 15 /category food /date 29102023 /recurrence monthly")
                        .execute(ui);
                parser.parse("out popmart /amount 12 /category toy /date 29102023").execute(ui);
                parser.parse("out grab /amount 20 /category transport /date 29102023").execute(ui);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * Test if it prints the export sucessful message after finish exporting without error.
         * @throws DukeException if command cannot be executed.
         */
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

        /**
         * Test if export command still work if there is an existing export file.
         * @throws DukeException if command fails to execute.
         */
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

        /**
         * Populate the state manager with values before the test.
         */
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
                parser.parse("out buy dinner /amount 15 /category food /date 29102023 /recurrence monthly")
                        .execute(ui);
                parser.parse("out popmart /amount 12 /category toy /date 29102023").execute(ui);
                parser.parse("out grab /amount 20 /category transport /date 29102023").execute(ui);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * Reset the state to original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(EXPORT_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the export command successfully export all income transactions.
         * This test is for Windows OS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Windows/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export all income transactions.
         * This test is for MacOS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export all income transactions.
         * This test is for Linux.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Linux/valid/Transactions-in.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeAll {

        /**
         * Populate the state manager with values before the test.
         */
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
                parser.parse("out buy dinner /amount 15 /category food /date 29102023 /recurrence monthly")
                        .execute(ui);
                parser.parse("out popmart /amount 12 /category toy /date 29102023").execute(ui);
                parser.parse("out grab /amount 20 /category transport /date 29102023").execute(ui);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * Reset the state to original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(EXPORT_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the export command successfully export all transactions.
         * This test is for Windows OS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
        @Test
        @EnabledOnOs({OS.WINDOWS})
        public void exportFileAllTransactionsWindows() throws DukeException, IOException {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
            String userInput = "export";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ExportCommand command = new ExportCommand(commandWord, args);
            command.execute(ui);
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Windows/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export all transactions.
         * This test is for MacOS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export all transactions.
         * This test is for Linux.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Linux/valid/Transactions-all.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeOut {

        /**
         * Populate the state manager with values before the test.
         */
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
                parser.parse("out buy dinner /amount 15 /category food /date 29102023 /recurrence monthly")
                        .execute(ui);
                parser.parse("out popmart /amount 12 /category toy /date 29102023").execute(ui);
                parser.parse("out grab /amount 20 /category transport /date 29102023").execute(ui);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * Reset the state to original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(EXPORT_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the export command successfully export expense transactions.
         * This test is for Windows OS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Windows/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export expense transactions.
         * This test is for MacOS.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/MacOS/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if the export command successfully export expense transactions.
         * This test is for Linux.
         * @throws DukeException if command fails to execute.
         * @throws IOException if file cannot be found.
         */
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
            File output = new File(EXPORT_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Linux/valid/Transactions-out.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }

    @Nested
    class TypeError {
        /**
         * Populate the state manager with values before the test.
         */
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

        /**
         * Reset the state to original after each test.
         */
        @AfterEach
        void clearStateManager() {
            StateManager.clearStateManager();
        }

        /**
         * Test if the error message is thrown if the type enter is a wrong format.
         * @throws DukeException if the type is not correctly specified.
         */
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
                    , outputStream.toString());
        }
    }
}
