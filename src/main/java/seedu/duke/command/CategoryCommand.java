package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class CategoryCommand extends ClassificationCommand {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String INVALID_INPUT = "Your category input is empty/invalid :(";

    public CategoryCommand(String description, HashMap<String, String> args) {
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
            String category = getArg(ADD_COMMAND);
            addCategory(category);
            ui.print("Successfully added " + category + "!");
        } else if (inputType.equals(REMOVE_COMMAND)) {
            String category = getArg(REMOVE_COMMAND);
            removeCategory(category);
            ui.print("Successfully removed " + category + "!");
        }
    }


    /**
     * Adds a category to the category ArrayList in StateManager
     * @param category category to add
     * @throws DukeException if category already exists
     */
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

    /**
     * Removes a category to the category ArrayList in StateManager
     * @param  category to remove
     * @throws DukeException if category does not already exist
     */
    private void removeCategory(String category) throws DukeException {
        StateManager state = StateManager.getStateManager();
        int index = state.getCategoryIndex(category);
        Category categoryToRemove = state.getCategory(index);
        boolean removedClassification = false;
        if (index != -1) {
            state.unassignCategoryTransactions(categoryToRemove);
            removedClassification = state.removeCategory(categoryToRemove);
        }
        if (!removedClassification) {
            String failedRemoval = "Failed to remove '" + category + "' as it does not exist!";
            throw new DukeException(failedRemoval);
        }
    }

}
