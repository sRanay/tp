/**
 * The EditTransactionCommandTest class contains JUnit tests for the EditTransactionCommand class,
 * which is responsible for editing transactions.
 * It tests various scenarios such as missing arguments, invalid indices, too many arguments, and successful edits.
 */

package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EditTransactionCommandTest {

    private static final Parser parser = new Parser();
    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final Ui ui = new Ui(outputStream);

    /**
     * Populates the StateManager with sample transactions before each test.
     */
    @BeforeEach
    void populateStateManager() {
        try {
            parser.parse("goal /add car /amount 1000").execute(ui);
            parser.parse("goal /add ps5 /amount 1000").execute(ui);
            parser.parse("in part-time job /amount 1000 /goal car /date 18092023").execute(ui);
            parser.parse("in allowance /amount 500 /goal car").execute(ui);
            parser.parse("in sell stuff /amount 50 /goal ps5").execute(ui);
            parser.parse("out buy dinner /amount 15 /category food").execute(ui);
            parser.parse("out popmart /amount 12 /category toy").execute(ui);
            parser.parse("out grab /amount 20 /category transport").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    /**
     * Tests whether an exception is thrown when the index is missing.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_missingIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit /type in /description part-time job");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    /**
     * Tests whether an exception is thrown when the type argument is missing.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_missingTypeArgument_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit 1 ");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    /**
     * Tests whether an exception is thrown when the type value is missing.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_missingTypeValue_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit 1 /type /description part-time job");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void execute_negativeIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit -1 /type in /description part-time job");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    /**
     * Tests whether an exception is thrown when the index is out of range.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_outOfRangeIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit 1000 /type in /description part-time job");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }


    /**
     * Tests whether an exception is thrown when attempting to edit the date.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_attemptToEditDate_exceptionThrown() throws DukeException {
        Command command = parser.parse("edit 1 /type in /date 18102023");
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    /**
     * Tests whether a valid income transaction is successfully edited.
     *
     * @throws DukeException if the test fails.
     */

    @Test
    void execute_validIncomeIdx_edited() throws DukeException {
        Command command = parser.parse("edit 1 /type in /description salary");
        command.execute(ui);
        String transactionDescription = StateManager.getStateManager().getIncome(0) // 0-based indexing
                .getTransaction().getDescription();
        assertNotEquals("allowance", transactionDescription);
    }

    /**
     * Tests whether a valid expense transaction is successfully edited.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void execute_validExpenseIdx_edited() throws DukeException {
        Command command = parser.parse("edit 2 /type out /amount 10");
        command.execute(ui);
        Double transactionAmount = StateManager.getStateManager().getExpense(1) // 0-based indexing
                .getTransaction().getAmount();
        assertNotEquals(12.0, transactionAmount);
    }
}
