package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class CategoryCommand extends Command {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String INVALID_INPUT = "Your category input is empty/invalid :(";

    public CategoryCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        String inputType = validateInput();
        if (inputType == null) {
            errorMessage(INVALID_INPUT);
        }
        if (inputType.equals(ADD_COMMAND)) {
            String category = getArg(ADD_COMMAND);
            addCategory(category);
            ui.print("Successfully added " + category + "!");
        } else if (inputType.equals(REMOVE_COMMAND)) {
            String category = getArg(REMOVE_COMMAND);
            removeCategory(category);
            ui.print("Successfully removed " + category + "!");
        }
    }

    public String validateInput() throws DukeException {
        if (getArgs().isEmpty()) {
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
        String commonMessage = "\nThe correct usage is 'category /add NAME' or 'category /remove NAME'";
        throw new DukeException(message + commonMessage);
    }

    private void addCategory(String category) throws DukeException {
        StateManager state = StateManager.getStateManager();
        if (state.getCategoryIndex(category) == -1) {
            Category newCategory = new Category(category);
            state.addCategory(newCategory);
        } else {
            String alreadyExists = "Failed to add '" + category + "' as it already exists!";
            throw new DukeException(alreadyExists);
        }

    }

    private void removeCategory(String category) throws DukeException {
        StateManager state = StateManager.getStateManager();
        int index = state.getCategoryIndex(category);
        boolean removedCategory = false;
        if (index != -1) {
            removedCategory = state.removeCategory(index);
        }
        if (!removedCategory) {
            String failedRemoval = "Failed to remove '" + category + "' as it does not exist!";
            throw new DukeException(failedRemoval);
        }
    }

}
