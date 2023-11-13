package seedu.duke.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import seedu.duke.classes.StateManager;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String TEST_DIR = "./TestFiles";
    private static final String GOAL_STORAGE_FILENAME = TEST_DIR + "/goal-store.csv";
    private static final String CATEGORY_STORAGE_FILENAME = TEST_DIR + "/category-store.csv";
    private static final String INCOME_STORAGE_FILENAME = TEST_DIR + "/income-store.csv";
    private static final String EXPENSE_STORAGE_FILENAME = TEST_DIR + "/expense-store.csv";
    private static final String EXPORT_STORAGE_FILENAME = TEST_DIR + "/Transactions.csv";

    private Storage storage;

    /**
     * Before each test, initialise the storage object;
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
     * Test for validRow function if there is empty value, it will return false.
     */
    @Test
    void validRowWithEmptyValues() {
        String[] row = {"TEST1", ""};
        assertEquals(false, storage.validRow(row));
        row[0] = "";
        row[1] = "TEST";
        assertEquals(false, storage.validRow(row));
    }

    /**
     * Test for validRow function if there is blank value, it will return false.
     */
    @Test
    void validRowWithBlankValues() {
        String[] row = {"TEST1", " "};
        assertEquals(false, storage.validRow(row));
        row[0] = "    ";
        row[1] = "Test1";
        assertEquals(false, storage.validRow(row));
    }

    /**
     * Test for validRow function if value is valid, it will return true.
     */
    @Test
    void validRowWithCorrectValues() {
        String[] row = {"TEST1", "TEST2"};
        assertEquals(true, storage.validRow(row));
    }

    /**
     * Test for validDate function if value is in wrong format, it will return an error.
     */
    @Test
    void validDateWithWrongFormat() {
        String dateStr = "25-10-2023";
        String testFileName = "filename";
        assertThrows(DukeException.class, () -> {
            storage.validDate(dateStr, testFileName);
        });
    }

    /**
     * Test for validDate function if value is not a date format, it will return an error.
     */
    @Test
    void validDateWithNotDateString() {
        String dateStr = "TEST";
        String testFileName = "filename";
        assertThrows(DukeException.class, () -> {
            storage.validDate(dateStr, testFileName);
        });
    }

    /**
     * Test for validDate function if value is in correct format, it will return the date.
     */
    @Test
    void validDateWithCorrectDateString() throws DukeException {
        String dateStr = "25/10/2023";
        String testFileName = "filename";
        LocalDate date = LocalDate.parse("25/10/2023", FORMATTER);
        assertEquals(date, storage.validDate(dateStr, testFileName));
    }

    /**
     * Test for validBoolean function if value is a correct boolean string, it will return true.
     */
    @Test
    void validBooleanWithCorrectBoolString() {
        String input = "True";
        assertEquals(true, storage.validBoolean(input));
        input = "TRUE";
        assertEquals(true, storage.validBoolean(input));
        input = "true";
        assertEquals(true, storage.validBoolean(input));
        input = "False";
        assertEquals(true, storage.validBoolean(input));
        input = "FALSE";
        assertEquals(true, storage.validBoolean(input));
        input = "false";
        assertEquals(true, storage.validBoolean(input));
    }

    /**
     * Test for validBoolean function if value is a wrong boolean string, it will return false.
     */
    @Test
    void validBooleanWithWrongBoolString() {
        String input = "test";
        assertEquals(false, storage.validBoolean(input));
    }

    /**
     * Test if loading of storage file will throw an error if files cannot be found.
     */
    @Test
    void loadWithNoStorageFile() {
        assertThrows(DukeException.class, () -> {
            storage.loadIncome();
        });
        assertThrows(DukeException.class, () -> {
            storage.loadExpense();
        });
        assertThrows(DukeException.class, () -> {
            storage.loadGoal();
        });
        assertThrows(DukeException.class, () -> {
            storage.loadCategory();
        });
    }

    @Nested
    class WithValidStorage {

        /**
         * Before each test, copy file to TestFiles Directory.
         * @throws IOException if the files cannot be found.
         */
        @BeforeEach
        void copyFiles() throws IOException {
            File src = new File("./TestCSV/Windows/valid/category-store.csv");
            File dst = new File(CATEGORY_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/valid/goal-store.csv");
            dst = new File(GOAL_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/valid/expense-store.csv");
            dst = new File(EXPENSE_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/valid/income-store.csv");
            dst = new File(INCOME_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
        }

        /**
         * Restore the state back to the original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(CATEGORY_STORAGE_FILENAME);
            file.delete();
            file = new File(GOAL_STORAGE_FILENAME);
            file.delete();
            file = new File(EXPENSE_STORAGE_FILENAME);
            file.delete();
            file = new File(INCOME_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the application can load back the information given valid storage files.
         * @throws DukeException if the command cannot be executed.
         */
        @Test
        void loadWithValidStorageFile() throws DukeException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            storage.load();
            Parser parser = new Parser();
            Ui ui = new Ui(outputStream);
            String userInput = "list /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ListCommand command = new ListCommand(commandWord, args);
            command.execute(ui);
            userInput = "list /type out";
            args = parser.getArguments(userInput);
            commandWord = parser.getDescription(userInput);
            command = new ListCommand(commandWord, args);
            command.execute(ui);
            assertEquals("Alright! Displaying 3 transactions.\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "ID    Description                      Date         Amount       Goal" +
                            "                   Recurrence\n" +
                            "1     part-time job                    2023-10-29   1000.00      car " +
                            "                   none\n" +
                            "2     allowance                        2023-10-29   500.00       car " +
                            "                   monthly\n" +
                            "3     sell stuff                       2023-10-29   50.00        ps5 " +
                            "                   none\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "Alright! Displaying 3 transactions.\n" +
                            "========================================== OUT TRANSACTIONS ==========" +
                            "=================================\n" +
                            "ID    Description                      Date         Amount       Category " +
                            "              Recurrence\n" +
                            "1     buy dinner                       2023-10-29   15.00        food     " +
                            "              monthly\n" +
                            "2     popmart                          2023-10-29   12.00        toy      " +
                            "              none\n" +
                            "3     grab                             2023-10-29   20.00        transport " +
                            "             none\n" +
                            "========================================== OUT TRANSACTIONS ==============" +
                            "=============================\n"
                    , outputStream.toString());


        }
    }

    /**
     * Test for loading storage files with empty columns
     * Tests is split depending on the OS.
     */
    @Nested
    class WithEmptyColumns {

        /**
         * Before each test, copy file to TestFiles Directory.
         * @throws IOException if the files cannot be found.
         */
        @BeforeEach
        void copyFiles() throws IOException {
            File src = new File("./TestCSV/Windows/empty/category-store.csv");
            File dst = new File(CATEGORY_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/empty/goal-store.csv");
            dst = new File(GOAL_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/empty/expense-store.csv");
            dst = new File(EXPENSE_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/empty/income-store.csv");
            dst = new File(INCOME_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
        }

        /**
         * Restore the state back to the original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(CATEGORY_STORAGE_FILENAME);
            file.delete();
            file = new File(GOAL_STORAGE_FILENAME);
            file.delete();
            file = new File(EXPENSE_STORAGE_FILENAME);
            file.delete();
            file = new File(INCOME_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the application can load back the information given storage files with empty column.
         * @throws DukeException if the command cannot be executed.
         */
        @Test
        void loadWithEmptyColumns() throws DukeException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            storage.load();
            Parser parser = new Parser();
            Ui ui = new Ui(outputStream);
            String userInput = "list /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ListCommand command = new ListCommand(commandWord, args);
            command.execute(ui);
            userInput = "list /type out";
            args = parser.getArguments(userInput);
            commandWord = parser.getDescription(userInput);
            command = new ListCommand(commandWord, args);
            command.execute(ui);
            assertEquals("Alright! Displaying 3 transactions.\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "ID    Description                      Date         Amount       Goal" +
                            "                   Recurrence\n" +
                            "1     part-time job                    2023-10-29   1000.00      car " +
                            "                   none\n" +
                            "2     allowance                        2023-10-29   500.00       car " +
                            "                   monthly\n" +
                            "3     sell stuff                       2023-10-29   50.00        ps5 " +
                            "                   none\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "Alright! Displaying 3 transactions.\n" +
                            "========================================== OUT TRANSACTIONS ==========" +
                            "=================================\n" +
                            "ID    Description                      Date         Amount       Category " +
                            "              Recurrence\n" +
                            "1     buy dinner                       2023-10-29   15.00        food     " +
                            "              daily\n" +
                            "2     popmart                          2023-10-29   12.00        toy      " +
                            "              none\n" +
                            "3     grab                             2023-10-29   20.00        transport " +
                            "             none\n" +
                            "========================================== OUT TRANSACTIONS ==============" +
                            "=============================\n"
                    , outputStream.toString());
        }
    }

    @Nested
    class WithErrorColumns {

        /**
         * Before each test, copy file to TestFiles Directory.
         * @throws IOException if the files cannot be found.
         */
        @BeforeEach
        void copyFiles() throws IOException {
            File src = new File("./TestCSV/Windows/error/category-store.csv");
            File dst = new File(CATEGORY_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/error/goal-store.csv");
            dst = new File(GOAL_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/error/expense-store.csv");
            dst = new File(EXPENSE_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
            src = new File("./TestCSV/Windows/error/income-store.csv");
            dst = new File(INCOME_STORAGE_FILENAME);
            Files.copy(src.toPath(), dst.toPath());
        }

        /**
         * Restore the state back to the original after each test.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(CATEGORY_STORAGE_FILENAME);
            file.delete();
            file = new File(GOAL_STORAGE_FILENAME);
            file.delete();
            file = new File(EXPENSE_STORAGE_FILENAME);
            file.delete();
            file = new File(INCOME_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if the application can load back the information given storage files with error columns.
         * @throws DukeException if the command cannot be executed.
         */
        @Test
        void loadWithErrorColumns() throws DukeException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            storage.load();
            Parser parser = new Parser();
            Ui ui = new Ui(outputStream);
            String userInput = "list /type in";
            HashMap<String, String> args = parser.getArguments(userInput);
            String commandWord = parser.getDescription(userInput);
            ListCommand command = new ListCommand(commandWord, args);
            command.execute(ui);
            userInput = "list /type out";
            args = parser.getArguments(userInput);
            commandWord = parser.getDescription(userInput);
            command = new ListCommand(commandWord, args);
            command.execute(ui);
            assertEquals("Alright! Displaying 3 transactions.\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "ID    Description                      Date         Amount       Goal" +
                            "                   Recurrence\n" +
                            "1     part-time job                    2023-10-29   1000.00      car " +
                            "                   none\n" +
                            "2     allowance                        2023-10-29   500.00       car " +
                            "                   monthly\n" +
                            "3     sell stuff                       2023-10-29   50.00        ps5 " +
                            "                   none\n" +
                            "=========================================== IN TRANSACTIONS =========" +
                            "==================================\n" +
                            "Alright! Displaying 3 transactions.\n" +
                            "========================================== OUT TRANSACTIONS ==========" +
                            "=================================\n" +
                            "ID    Description                      Date         Amount       Category " +
                            "              Recurrence\n" +
                            "1     buy dinner                       2023-10-29   15.00        food     " +
                            "              daily\n" +
                            "2     popmart                          2023-10-29   12.00        toy      " +
                            "              none\n" +
                            "3     grab                             2023-10-29   20.00        transport " +
                            "             none\n" +
                            "========================================== OUT TRANSACTIONS ==============" +
                            "=============================\n"
                    , outputStream.toString());
        }
    }

    @Nested
    class SaveToFile {

        /**
         * Before each test, populate the state manager.
         */
        @BeforeEach
        void populateStateManager() {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Ui ui = new Ui(outputStream);
                Parser parser = new Parser();
                parser.parse("goal /add car /amount 1000").execute(ui);
                parser.parse("goal /add ps5 /amount 1000").execute(ui);
                parser.parse("in part-time job /amount 1000 /goal car /date 29102023").execute(ui);
                parser.parse("in allowance /amount 500 /goal car /date 29102023 /recurrence monthly")
                        .execute(ui);
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
         * Restore the state back to the original.
         */
        @AfterEach
        void clearStateManager() {
            File file = new File(CATEGORY_STORAGE_FILENAME);
            file.delete();
            file = new File(GOAL_STORAGE_FILENAME);
            file.delete();
            file = new File(EXPENSE_STORAGE_FILENAME);
            file.delete();
            file = new File(INCOME_STORAGE_FILENAME);
            file.delete();
            StateManager.clearStateManager();
        }

        /**
         * Test if data saved is saved correctly.
         * This test is for Windows OS.
         * @throws DukeException if command cannot execute.
         * @throws IOException if file cannot be found.
         */
        @Test
        @EnabledOnOs({OS.WINDOWS})
        void saveDataWorkingWindows() throws DukeException, IOException {
            storage.save();
            File output = new File(CATEGORY_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Windows/valid/category-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(GOAL_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Windows/valid/goal-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(INCOME_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Windows/valid/income-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(EXPENSE_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Windows/valid/expense-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if data saved is saved correctly.
         * This test is for MacOS.
         * @throws DukeException if command cannot execute.
         * @throws IOException if file cannot be found.
         */
        @Test
        @EnabledOnOs({OS.MAC})
        void saveDataWorkingMac() throws DukeException, IOException {
            storage.save();
            File output = new File(CATEGORY_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/MacOS/valid/category-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(GOAL_STORAGE_FILENAME);
            testFile = new File("./TestCSV/MacOS/valid/goal-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(INCOME_STORAGE_FILENAME);
            testFile = new File("./TestCSV/MacOS/valid/income-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(EXPENSE_STORAGE_FILENAME);
            testFile = new File("./TestCSV/MacOS/valid/expense-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }

        /**
         * Test if data saved is saved correctly.
         * This test is for Linux.
         * @throws DukeException if command cannot execute.
         * @throws IOException if file cannot be found.
         */
        @Test
        @EnabledOnOs({OS.LINUX})
        void saveDataWorkingLinux() throws DukeException, IOException {
            storage.save();
            File output = new File(CATEGORY_STORAGE_FILENAME);
            File testFile = new File("./TestCSV/Linux/valid/category-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(GOAL_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Linux/valid/goal-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(INCOME_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Linux/valid/income-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
            output = new File(EXPENSE_STORAGE_FILENAME);
            testFile = new File("./TestCSV/Linux/valid/expense-store.csv");
            assertEquals(true, FileUtils.contentEquals(output, testFile));
        }
    }
}
