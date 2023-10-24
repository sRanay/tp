package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AddIncomeCommandTest {
    private static final DukeException MISSING_DESC_EXCEPTION = new DukeException("Description cannot be empty...");
    private static final DukeException MISSING_AMT_EXCEPTION = new DukeException("Amount cannot be empty...");
    private static final DukeException BAD_AMOUNT_EXCEPTION = new DukeException("Invalid amount value specified...");
    private static final DukeException MISSING_GOAL_EXCEPTION = new DukeException("Goal cannot be empty...");
    private static final DukeException BAD_RECURRENCE = new DukeException("Invalid recurrence period specified...");

    @BeforeEach
    void addCategories() {
        assertDoesNotThrow(() -> {
            new CommandTestCase("goal /add car /amount 5000").evaluate();
            new CommandTestCase("goal /add PS5 /amount 300").evaluate();
        });
    }

    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    @Test
    void validInputs() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in part-time job /amount 500 /goal car",
                    "Nice! The following income has been tracked:\n" +
                            "Description   Amount        Goal\n" +
                            "part-tim...   500.00        car\n"
            ),
            new CommandTestCase(
                    "in red packet money /amount 50 /goal PS5",
                    "Nice! The following income has been tracked:\n" +
                            "Description   Amount        Goal\n" +
                            "red pack...   50.00         PS5\n"
            ),
            new CommandTestCase(
                    "in pocket money /amount 50 /goal PS5 /recurrence weekly",
                    "Nice! The following income has been tracked:\n" +
                            "Description   Amount        Goal\n" +
                            "pocket m...   50.00         PS5\n"
            )
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void missingDescription() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "in     ",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "in     /amount -1",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "in /amount 500",
                    MISSING_DESC_EXCEPTION
            ),
            new CommandTestCase(
                    "in /amount 500 /goal car",
                    MISSING_DESC_EXCEPTION
            ),
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void missingAmount() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in part-time job",
                    MISSING_AMT_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /amount",
                    MISSING_AMT_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /amount   ",
                    MISSING_AMT_EXCEPTION
            ),
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void badAmount() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in part-time job /amount -1",
                    BAD_AMOUNT_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /amount -1 /goal car",
                    BAD_AMOUNT_EXCEPTION
            )
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void missingClassification() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in part-time job /amount 500",
                    MISSING_GOAL_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /goal   /amount 500",
                    MISSING_GOAL_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /amount 500 /goal",
                    MISSING_GOAL_EXCEPTION
            ),
            new CommandTestCase(
                    "in part-time job /amount 500 /goal    ",
                    MISSING_GOAL_EXCEPTION
            )
        };
        CommandTestCase.runTestCases(testCases);
    }

    @Test
    void badRecurrence() {
        CommandTestCase[] testCases = new CommandTestCase[]{
            new CommandTestCase(
                    "in pocket money /amount 50 /recurrence   /goal PS5",
                    BAD_RECURRENCE
            ),
            new CommandTestCase(
                    "in pocket money /amount 50 /goal PS5 /recurrence random",
                    BAD_RECURRENCE
            )
        };
        CommandTestCase.runTestCases(testCases);
    }
}
