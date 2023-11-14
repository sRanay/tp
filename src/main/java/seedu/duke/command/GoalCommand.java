package seedu.duke.command;

import seedu.duke.classes.Goal;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class GoalCommand extends ClassificationCommand {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String AMOUNT = "amount";
    private static final String INVALID_INPUT = "Your goal input is empty/invalid :(";
    private static final String INVALID_AMOUNT = "Invalid amount value specified...";

    public GoalCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    /**
     * Executes the command.
     *
     * @param ui Ui class that is used to format output.
     * @throws DukeException if user input is invalid.
     */
    @Override
    public void execute(Ui ui) throws DukeException {
        String inputType = validateInput();
        if (inputType == null) {
            errorMessage(INVALID_INPUT);
        }
        if (inputType.equals(ADD_COMMAND)) {
            validateAmount();
            String goalName = getArg(ADD_COMMAND);
            Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT));
            addGoal(goalName, amount);
            ui.print("Successfully added " + goalName + "!");
        } else if (inputType.equals(REMOVE_COMMAND)) {
            String goalName = getArg(REMOVE_COMMAND);
            removeGoal(goalName);
            ui.print("Successfully removed " + goalName + "!");
        }
    }

    /**
     * Validates if amount specified by user is correct
     * @throws DukeException if amount is invalid
     */
    private void validateAmount() throws DukeException {
        String amount = getArg(AMOUNT);
        if (amount == null || amount.isBlank()) {
            errorMessage(INVALID_INPUT);
        }
        Double parsedAmt = Parser.parseNonNegativeDouble(amount);
        if (parsedAmt == null) {
            errorMessage(INVALID_AMOUNT);
        } else if (parsedAmt == 0) {
            errorMessage(INVALID_AMOUNT);
        }
    }

    /**
     * Adds goal to StateManager
     * @param goal name of goal to add
     * @param amount goal amount
     * @throws DukeException if goal already exists in list
     */
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

    /**
     * Removes goal in StateManager
     * @param goal name of goal to remove
     * @throws DukeException if goal does not already exist in list
     */
    private void removeGoal(String goal) throws DukeException {
        StateManager state = StateManager.getStateManager();
        int index = state.getGoalIndex(goal);
        Goal goalToRemove = state.getGoal(index);
        boolean removedGoal = false;
        if (index != -1) {
            state.unassignGoalTransactions(goalToRemove);
            removedGoal = state.removeGoal(goalToRemove);
        }
        if (!removedGoal) {
            String failedRemoval = "Failed to remove '" + goal + "' as it does not exist!";
            throw new DukeException(failedRemoval);
        }
    }

}
