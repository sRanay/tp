package seedu.duke.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExportCommandTest {
    private static Parser parser = new Parser();
    private ByteArrayOutputStream outputStream;

    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }
    @BeforeEach
    void populateStateManager() {
        try {
            outputStream = new ByteArrayOutputStream();
            Ui ui = new Ui(outputStream);
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
