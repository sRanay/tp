package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListCommandTest {

    @Test
    void invalidList() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void invalidListType() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list /type abc";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    private static void addInEntries() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("goal /add PS5 /amount 300").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car").execute(ui);
            parser.parse("in red packet money /amount 50 /goal PS5 /date 18092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addOutEntries() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("out dinner /amount 10.50 /category food").execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date 18092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void validInList() throws DukeException {
        addInEntries();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.now();
        Command command = parser.parse("list /type in");
        command.execute(ui);
        assertEquals("Alright! Displaying 2 transactions.\n"
                        + "=============================== IN TRANSACTIONS ================================\n"
                        + "ID    Description                      Date         Amount       Goal\n"
                        + "1     part-time job                    " + currentDate + "   500.00       car\n"
                        + "2     red packet money                 2023-09-18   50.00        PS5\n"
                        + "=============================== IN TRANSACTIONS ================================\n"
                , outputStream.toString());

    }

    @Test
    void validOutList() throws DukeException {
        addOutEntries();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.now();
        Command command = parser.parse("list /type out");
        command.execute(ui);
        assertEquals("Alright! Displaying 2 transactions.\n"
                        + "=============================== OUT TRANSACTIONS ===============================\n"
                        + "ID    Description                      Date         Amount       Category\n"
                        + "1     dinner                           " + currentDate + "   10.50        food\n"
                        + "2     pokemon card pack                2023-09-18   10.50        games\n"
                        + "=============================== OUT TRANSACTIONS ===============================\n"
                , outputStream.toString());

    }
}
