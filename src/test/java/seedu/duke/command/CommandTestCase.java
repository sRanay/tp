package seedu.duke.command;

import org.junit.jupiter.api.function.Executable;

public class CommandTestCase {
    private final String commandInput;
    private final String commandOutput;
    private final Exception exception;
    private final Executable executable;

    public CommandTestCase(String commandInput) {
        this(commandInput, null, null, null);
    }

    public CommandTestCase(String commandInput, Exception exception) {
        this(commandInput, null, exception, null);
    }

    public CommandTestCase(String commandInput, String commandOutput) {
        this(commandInput, commandOutput, null, null);
    }

    public CommandTestCase(String commandInput, String commandOutput, Executable executable) {
        this(commandInput, commandOutput, null, executable);
    }

    public CommandTestCase(String commandInput, String commandOutput, Exception exception, Executable executable) {
        this.commandInput = commandInput;
        this.commandOutput = commandOutput;
        this.exception = exception;
        this.executable = executable;
    }

    public void evaluate() {
        CommandTestUtils.runSingleCommand(commandInput, commandOutput, exception, executable);
    }

    public static void runTestCases(CommandTestCase[] testCases) {
        for (CommandTestCase tc : testCases) {
            tc.evaluate();
        }
    }
}
