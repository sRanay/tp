package seedu.duke.command;

import seedu.duke.classes.Goal;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class GoalCommand extends Command {
    private static final String ADD_ARG = "add";
    private static final String REMOVE_ARG = "remove";
    private static final String AMOUNT = "amount";

    public GoalCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        boolean addOrRemove = checkValidInputAndGetType();
        if (addOrRemove) {
            String goalName = getArg(ADD_ARG);
            Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT));
            addGoal(goalName, amount);
            ui.print("Successfully added " + goalName + "!");
        } else {
            String goalName = getArg(REMOVE_ARG);
            removeGoal(goalName);
            ui.print("Successfully removed " + goalName + "!");
        }
    }

    // Returns True if it wants to add a category and returns false if it wants to remove a category.
    private boolean checkValidInputAndGetType() throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;
        if (!getDescription().isBlank()) {
            String ignoreDescription = "As the goal command does not use the description field, this " +
                    "input would be ignored: ";
            throw new DukeException(ignoreDescription + getDescription());
        }
        String add = getArg(ADD_ARG);
        String remove = getArg(REMOVE_ARG);
        String invalidGoalInput = "Your goal input is empty/invalid :(";
        if (add != null && remove != null) {
            throw new DukeException(invalidGoalInput);
        } else if (add == null && remove == null) {
            throw new DukeException(invalidGoalInput);
        } else if (add != null && add.isBlank()) {
            throw new DukeException(invalidGoalInput);
        } else if (remove != null && remove.isBlank()) {
            throw new DukeException(invalidGoalInput);
        } else if (remove != null) {
            return false;
        }
        String amount = getArg(AMOUNT);
        String invalidAmount = "You have entered an invalid or empty goal amount :(";
        if (amount == null || amount.isBlank()) {
            throw new DukeException(invalidAmount);
        }
        Double goalAmount = Parser.parseNonNegativeDouble(amount);
        if (goalAmount == null) {
            throw new DukeException(invalidAmount);
        }
        return true;
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
