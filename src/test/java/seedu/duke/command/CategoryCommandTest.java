/**
 * The CategoryCommandTest class contains JUnit tests for the CategoryCommand class,
 * which is responsible for handling category-related commands.
 * It tests various scenarios such as invalid categories, empty category names, adding and removing categories,
 * and ensures proper handling of exceptions.
 */

package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.classes.Category;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryCommandTest {

    /**
     * Tests whether an exception is thrown for an invalid category command.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void invalidCategory() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "category";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        CategoryCommand command = new CategoryCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests whether an exception is thrown for an empty category name during addition.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void emptyCategoryAdd() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "category /add ";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        CategoryCommand command = new CategoryCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests whether a valid category is successfully added.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void validCategory() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "category /add test";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        CategoryCommand command = new CategoryCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Successfully added test!\n", outputStream.toString());
    }

    /**
     * Tests whether an exception is thrown for an invalid category during removal.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void invalidRemoveCategory() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "category /remove categorytofail";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        CategoryCommand command = new CategoryCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    /**
     * Tests whether a valid category is successfully removed.
     *
     * @throws DukeException if the test fails.
     */
    @Test
    void validRemoveCategory() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        StateManager.getStateManager().addCategory(new Category("test"));
        String userInput = "category /remove test";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        CategoryCommand command = new CategoryCommand(commandWord, args);
        command.execute(ui);
        assertEquals("Successfully removed test!\n", outputStream.toString());
    }

}
