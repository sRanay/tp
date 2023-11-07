package seedu.duke.command;

import seedu.duke.exception.DukeException;

import java.util.HashMap;

public abstract class ClassificationCommand extends Command {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String UNCATEGORISED = "uncategorised";
    private static final String UNCATEGORISED_ERROR = "As 'Uncategorised' is a default classification, you are unable" +
            "to delete it.";

    public ClassificationCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

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
            checkArg(arg, invalidInput);
            return ADD_COMMAND;
        } else if (getArgs().containsKey(REMOVE_COMMAND)) {
            arg = getArg(REMOVE_COMMAND);
            checkArg(arg, invalidInput);
            return REMOVE_COMMAND;
        }
        return null;
    }

    private void checkArg(String arg, String invalidInput) throws DukeException {
        if (arg == null) {
            errorMessage(invalidInput);
        } else if (arg.isBlank()) {
            errorMessage(invalidInput);
        } else if (arg.equalsIgnoreCase(UNCATEGORISED)) {
            throw new DukeException(UNCATEGORISED_ERROR);
        }
    }

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
