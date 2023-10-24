package seedu.duke.command;

import org.junit.jupiter.api.function.Executable;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class CommandTestUtils {
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
