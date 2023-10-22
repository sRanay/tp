package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class CategoryCommand extends Command {
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";

    public CategoryCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        boolean addOrRemove = checkValidInputAndGetType();
        if (addOrRemove) {
            String category = getArg(ADD_COMMAND);
            addCategory(category);
            ui.print("Successfully added " + category + "!");
        } else {
            String category = getArg(REMOVE_COMMAND);
            removeCategory(category);
            ui.print("Successfully removed " + category + "!");
        }
    }

    // Returns True if it wants to add a category and returns false if it wants to remove a category.
    private boolean checkValidInputAndGetType() throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;
        if (!getDescription().isBlank()) {
            String invalidDescription = "As the category command does not use the description field, this " +
                    "input would be ignored: ";
            throw new DukeException(invalidDescription + getDescription());
        }
        String add = getArg(ADD_COMMAND);
        String remove = getArg(REMOVE_COMMAND);
        String invalidCategoryInput = "Your category input is empty/invalid :(";
        if (add != null && remove != null) {
            throw new DukeException(invalidCategoryInput);
        } else if (add == null && remove == null) {
            throw new DukeException(invalidCategoryInput);
        } else if (add != null && !add.isBlank()) {
            return true;
        } else if (remove != null && remove.isBlank()) {
            throw new DukeException(invalidCategoryInput);
        }
        return false;
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
