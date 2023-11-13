package seedu.duke.command;

import seedu.duke.exception.DukeException;

import java.util.HashMap;

public abstract class ClassificationCommand extends Command {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String UNCATEGORISED = "uncategorised";

    public ClassificationCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    /**
     * Validates user input
     * @return type of command (add or remove)
     * @throws DukeException if user input is invalid
     */
    protected String validateInput() throws DukeException {
        String invalidInput = "";
        if (getClass() == CategoryCommand.class) {
            invalidInput = "Your category input is empty/invalid :(";
        } else if (getClass() == GoalCommand.class) {
            invalidInput = "Your goal input is empty/invalid :(";
        }

        if (getArgs().isEmpty()) {
            throw new DukeException(invalidInput);
        } else if (getArgs().containsKey(ADD_COMMAND) && getArgs().containsKey(REMOVE_COMMAND)) {
            errorMessage(invalidInput);
        }
        String arg;
        if (getArgs().containsKey(ADD_COMMAND)) {
            arg = getArg(ADD_COMMAND);
            checkArg(arg, invalidInput, ADD_COMMAND);
            return ADD_COMMAND;
        } else if (getArgs().containsKey(REMOVE_COMMAND)) {
            arg = getArg(REMOVE_COMMAND);
            checkArg(arg, invalidInput, REMOVE_COMMAND);
            return REMOVE_COMMAND;
        }
        return null;
    }

    /**
     * Validates arguments
     * @param arg argument to validate
     * @param invalidInput error message to print
     * @param type whether argument is used for add or remove
     * @throws DukeException if arg is either null, blank or called 'Uncategorised'
     */
    private void checkArg(String arg, String invalidInput, String type) throws DukeException {
        if (arg == null) {
            errorMessage(invalidInput);
        } else if (arg.isBlank()) {
            errorMessage(invalidInput);
        } else if (arg.equalsIgnoreCase(UNCATEGORISED)) {
            String uncategorisedError = "As 'Uncategorised' is a default classification, you are unable " +
                    "to " + type + " it.";
            throw new DukeException(uncategorisedError);
        }
    }

    /**
     * Prints error message depending on whether GoalCommand or CategoryCommand called it
     * @param message Error message to print
     * @throws DukeException To display error to user
     */
    protected void errorMessage(String message) throws DukeException {
        String commonMessage = "Invalid input! Please refer to UG for correct usage";
        if (getClass() == CategoryCommand.class) {
            commonMessage = "\nThe correct usage is 'category /add NAME' or 'category /remove NAME'";
        } else if (getClass() == GoalCommand.class) {
            commonMessage = "\nThe correct usage is 'goal /add NAME /amount AMOUNT' or 'goal /remove NAME'";
        }
        throw new DukeException(message + commonMessage);
    }


}
