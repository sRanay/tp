package seedu.duke.command;

import seedu.duke.classes.Goal;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class GoalCommand extends Command {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String AMOUNT = "amount";

    private static final String INVALID_INPUT = "Your goal input is empty/invalid :(";

    public GoalCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        String input = validateInput();
        if (input.equals(ADD_COMMAND)) {
            String goalName = getArg(ADD_COMMAND);
            Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT));
            addGoal(goalName, amount);
            ui.print("Successfully added " + goalName + "!");
        } else if (input.equals(REMOVE_COMMAND)){
            String goalName = getArg(REMOVE_COMMAND);
            removeGoal(goalName);
            ui.print("Successfully removed " + goalName + "!");
        }
    }

    public String validateInput() throws DukeException {
        if(getArgs().isEmpty()) {
            throw new DukeException(INVALID_INPUT);
        } else if (getArgs().containsKey(ADD_COMMAND) && getArgs().containsKey(REMOVE_COMMAND)) {
            errorMessage(INVALID_INPUT);
        }
        if (getArgs().containsKey(ADD_COMMAND)) {
            String add = getArg(ADD_COMMAND);
            if (add == null) {
                errorMessage(INVALID_INPUT);
            } else if (add.isBlank()) {
                errorMessage(INVALID_INPUT);
            }
            String amount = getArg(AMOUNT);
            if (amount == null || amount.isBlank()) {
                errorMessage(INVALID_INPUT);
            }
            Double parsedAmt = Parser.parseNonNegativeDouble(amount);
            if (parsedAmt == null) {
                errorMessage(INVALID_INPUT);
            }
            return ADD_COMMAND;
        }
        if (getArgs().containsKey(REMOVE_COMMAND)) {
            String remove = getArg(REMOVE_COMMAND);
            if (remove == null) {
                errorMessage(INVALID_INPUT);
            } else if (remove.isBlank()) {
                errorMessage(INVALID_INPUT);
            }
            return REMOVE_COMMAND;
        }
        return null;
    }
    private void errorMessage(String message) throws DukeException {
        String commonMessage = "\nThe correct usage is 'goal /add NAME /amount AMOUNT' or 'goal /remove NAME'";
        throw new DukeException(message + commonMessage);
    }

    private void addGoal(String goal, double amount) throws DukeException {
        StateManager state = StateManager.getStateManager();
        if (state.getGoalIndex(goal) == -1) {
            Goal goalToAdd = new Goal(goal, amount);
            state.addGoal(goalToAdd);
        } else {
            String alreadyExists = "Failed to add '" + goal + "' as it already exists!";
            throw new DukeException(alreadyExists);
        }

    }

    private void removeGoal(String goal) throws DukeException {
        StateManager state = StateManager.getStateManager();
        int index = state.getGoalIndex(goal);
        boolean removedGoal = false;
        if (index != -1) {
            removedGoal = state.removeGoal(index);
        }
        if (!removedGoal) {
            String failedRemoval = "Failed to remove '" + goal + "' as it does not exist!";
            throw new DukeException(failedRemoval);
        }
    }

}
