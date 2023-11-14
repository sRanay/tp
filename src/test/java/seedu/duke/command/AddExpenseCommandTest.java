/**
 * The AddExpenseCommandTest class contains JUnit tests for the AddExpenseCommand class,
 * which is responsible for adding expense transactions.
 * It tests various scenarios such as valid inputs, missing or invalid descriptions, amounts, categories,
 * and ensures proper handling of exceptions.
 */

package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;

import java.time.LocalDate;

class AddExpenseCommandTest {
    private static final DukeException MISSING_DESC_EXCEPTION = new DukeException("Description cannot be empty...");
    private static final DukeException MISSING_AMT_EXCEPTION = new DukeException("Amount cannot be empty...");
    private static final DukeException BAD_AMOUNT_EXCEPTION = new DukeException("Invalid amount value specified...");
    private static final DukeException MISSING_CAT_EXCEPTION = new DukeException("Category cannot be empty...");
    private static final DukeException BAD_RECURRENCE = new DukeException("Invalid recurrence period specified...");

    /**
     * Clears the StateManager after each test to ensure a clean slate for the next test.
     */
    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    /**
     * Tests valid inputs for adding expense transactions.
     */
    @Test
    void validInputs() {
        LocalDate date = LocalDate.now();
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "out dinner /amount 10.50 /category food",
                    "Nice! The following expense has been tracked:\n" +
                            "Description                      Date          Amount        Category       " +
                            "        Recurrence\n" +
                            "dinner                           " + date + "    10.50         food         " +
                            "          none\n"
            ),
            new CommandTestCase(
                    "out pokemon card pack /amount 10.50 /category games",
                    "Nice! The following expense has been tracked:\n" +
                            "Description                      Date          Amount        Category        " +
                            "       Recurrence\n" +
                            "pokemon card pack                " + date + "    10.50         games         " +
                            "         none\n"
            ), new CommandTestCase(
                    "out dinner /amount 500",
                    "Nice! The following expense has been tracked:\n" +
                                "Description                      Date          Amount        Category               " +
                                "Recurrence\n" +
                                "dinner                           " + date + "    500.00        Uncategorised " +
                                "         none\n"
            ),
        };
        CommandTestCase.runTestCases(testCases);
    }

    /**
     * Tests cases with missing or invalid descriptions.
     */
    @Test
    void missingDescription() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "out",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "out     ",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "out     /amount -1",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "out /amount 500",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "out /amount 500 /goal car",
                    MISSING_DESC_EXCEPTION
            ),
        };
        CommandTestCase.runTestCases(testCases);
    }

    /**
     * Tests cases with missing or invalid amounts.
     */
    @Test
    void missingAmount() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "out dinner",
                    MISSING_AMT_EXCEPTION
            ),
            new CommandTestCase(
                    "out dinner /amount",
                    MISSING_AMT_EXCEPTION
            ),
            new CommandTestCase(
                    "out dinner /amount   ",
                    MISSING_AMT_EXCEPTION
            ),
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void badAmount() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "out dinner /amount -1",
                    BAD_AMOUNT_EXCEPTION
            ),
            new CommandTestCase(
                    "out dinner /amount -1 /category games",
                    BAD_AMOUNT_EXCEPTION
            )
        };
        CommandTestCase.runTestCases(testCases);
    }

    /**
     * Tests cases with missing or invalid categories.
     */
    @Test
    void missingClassification() {
        LocalDate date = LocalDate.now();
        CommandTestCase[] testCases = new CommandTestCase[]{

            new CommandTestCase(
                    "out dinner /category   /amount 500",
                    MISSING_CAT_EXCEPTION
            ),
            new CommandTestCase(
                    "out dinner /amount 500 /category",
                    MISSING_CAT_EXCEPTION
            ),
            new CommandTestCase(
                    "out dinner /amount 500 /category    ",
                    MISSING_CAT_EXCEPTION
            )
        };
        CommandTestCase.runTestCases(testCases);
    }

    /**
     * Tests cases with invalid recurrence.
     */
    @Test
    void badRecurrence() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "out pocket money /amount 50 /category dinner /recurrence",
                    BAD_RECURRENCE
            ),
            new CommandTestCase(
                    "out pocket money /amount 50 /recurrence   /category dinner",
                    BAD_RECURRENCE
            ),
            new CommandTestCase(
                    "out pocket money /amount 50 /category dinner /recurrence random",
                    BAD_RECURRENCE
            )
        };
        CommandTestCase.runTestCases(testCases);
    }
}
