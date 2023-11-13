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

class RemoveTransactionCommandTest {

    private static Parser parser = new Parser();
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static Ui ui = new Ui(outputStream);

    @BeforeEach
    void populateStateManager() {
        try {
            parser.parse("goal /add car /amount 1000").execute(ui);
            parser.parse("goal /add ps5 /amount 1000").execute(ui);

            parser.parse("in part-time job /amount 1000 /goal car").execute(ui);
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

    @Test
    void execute_missingIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("delete /type in");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void execute_missingTypeArgument_exceptionThrown() throws DukeException {
        Command command = parser.parse("delete 1");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void execute_missingTypeValue_exceptionThrown() throws DukeException {
        Command command = parser.parse("delete 1 /type");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Test to ensure that Exception will be thrown when the index
     * is invalid.
     */
    @Test
    void execute_negativeIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("delete -1 /type in");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Test to ensure that Exception will be thrown when the index
     * is out or range.
     */
    @Test
    void execute_outOfRangeIdx_exceptionThrown() throws DukeException {
        Command command = parser.parse("delete 1000 /type in");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Test to ensure that income transaction is removed from the
     * StateManager when the index is valid.
     */
    @Test
    void execute_validIncomeIdx_removedFromStateManager() throws DukeException {
        Command command = parser.parse("delete 1 /type in");
        command.execute(ui);
        String transactionDescription = StateManager.getStateManager().getIncome(0) // 0-based indexing
                .getTransaction().getDescription();
        assertNotEquals("part-time-job", transactionDescription);
    }

    /**
     * Test to ensure that expense transaction is removed from the
     * StateManager when the index is valid.
     */
    @Test
    void execute_validExpenseIdx_removedFromStateManager() throws DukeException {
        Command command = parser.parse("delete 2 /type out");
        command.execute(ui);
        String transactionDescription = StateManager.getStateManager().getExpense(1) // 0-based indexing
                .getTransaction().getDescription();
        assertNotEquals("popmart", transactionDescription);
    }

}
