/**
 * The CommandTestCase class represents a test case for evaluating the behavior of a command.
 * It allows specifying the input, expected output, expected exception, and executable for a command.
 * The test case can be evaluated using the evaluate() method, and multiple test cases can be run using the
 * runTestCases() method.
 */

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

    /**
     * Constructs a CommandTestCase with the specified command input, expected output, expected exception,
     * and executable.
     *
     * @param commandInput  The input string representing the command to be tested.
     * @param commandOutput The expected output string after executing the command.
     * @param exception     The expected exception that should be thrown during command execution.
     * @param executable    The executable representing the command logic to be executed.
     */
    public CommandTestCase(String commandInput, String commandOutput, Exception exception, Executable executable) {
        this.commandInput = commandInput;
        this.commandOutput = commandOutput;
        this.exception = exception;
        this.executable = executable;
    }

    /**
     * Evaluates the current test case by running the command and asserting its behavior.
     */
    public void evaluate() {
        CommandTestUtils.runSingleCommand(commandInput, commandOutput, exception, executable);
    }

    /**
     * Runs multiple test cases by iterating through the provided array of CommandTestCase objects.
     *
     * @param testCases An array of CommandTestCase objects to be evaluated.
     */
    public static void runTestCases(CommandTestCase[] testCases) {
        for (CommandTestCase tc : testCases) {
            tc.evaluate();
        }
    }
}
