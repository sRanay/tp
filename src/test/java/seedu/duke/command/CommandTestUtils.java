/**
 * The CommandTestUtils class provides utility methods for testing command execution.
 * It includes a method to run a single command, specifying the command input, expected output, expected exception,
 * and an optional executable for additional assertions.
 */

package seedu.duke.command;

import org.junit.jupiter.api.function.Executable;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class CommandTestUtils {

    /**
     * Runs a single command test case by executing the provided command and asserting its behavior.
     *
     * @param command           The input string representing the command to be tested.
     * @param expectedOutput    The expected output string after executing the command.
     * @param expectedException The expected exception that should be thrown during command execution.
     * @param executable        The optional executable representing additional assertions on the command logic.
     */
    public static void runSingleCommand(String command, String expectedOutput, Exception expectedException,
                                        Executable executable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        Parser parser = new Parser();
        if (expectedException != null) {
            Exception thrownException = assertThrowsExactly(expectedException.getClass(), () -> {
                parser.parse(command).execute(ui);
            }, command);
            assertEquals(expectedException.getMessage(), thrownException.getMessage(), command);
        } else {
            assertDoesNotThrow(() -> {
                parser.parse(command).execute(ui);
            });
        }

        if (expectedOutput != null) {
            assertEquals(expectedOutput, outputStream.toString(), command);
        }

        if (executable != null) {
            assertDoesNotThrow(executable);
        }
    }
}
