/**
 * The GoalCommandTest class contains JUnit tests for the GoalCommand class,
 * which is responsible for managing goals.
 * It tests various scenarios such as adding, removing, and handling exceptions related to goals.
 */

package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.duke.classes.Goal;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GoalCommandTest {

    /**
     * Clears the StateManager after each test to ensure a clean slate for the next test.
     */
    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    /**
     * Tests handling of an invalid goal command.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void invalidGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests handling of an empty goal addition.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void emptyGoalAdd() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal /add ";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests handling of missing amount during goal addition.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void missingAmount() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal /add abc /amount ";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests handling of an invalid amount during goal addition.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void invalidAmount() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal /add ps5 /amount $500";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests successful addition of a valid goal.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void validGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal /add test /amount 500";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Successfully added test!\n", outputStream.toString());
    }

    /**
     * Tests handling of an invalid goal removal command.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void invalidRemoveGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "goal /remove goaltofail";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests successful removal of a valid goal.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void validRemoveGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        StateManager.getStateManager().addGoal(new Goal("test", 500));
        String userInput = "goal /remove test";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        GoalCommand command = new GoalCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Successfully removed test!\n", outputStream.toString());
    }

}
