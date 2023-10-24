package seedu.duke.command;

public class CommandTestCase {
    private final String commandInput;
    private final String commandOutput;
    private final Exception exception;

    public CommandTestCase(String commandInput) {
        this(commandInput, null, null);
    }

    public CommandTestCase(String commandInput, Exception exception) {
        this(commandInput, null, exception);
    }

    public CommandTestCase(String commandInput, String commandOutput) {
        this(commandInput, commandOutput, null);
    }

    public CommandTestCase(String commandInput, String commandOutput, Exception exception) {
        this.commandInput = commandInput;
        this.commandOutput = commandOutput;
        this.exception = exception;
    }

    public void evaluate() {
        CommandTestUtils.runSingleCommand(commandInput, commandOutput, exception);
    }

    public static void runTestCases(CommandTestCase[] testCases) {
        for (CommandTestCase tc : testCases) {
            tc.evaluate();
        }
    }
}
