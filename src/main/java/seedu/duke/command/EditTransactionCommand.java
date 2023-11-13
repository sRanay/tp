package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Goal;
import seedu.duke.classes.Category;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.HashMap;

public class EditTransactionCommand extends Command {

    protected static final String AMOUNT_ARG = "amount";
    protected static final String DESCRIPTION_ARG = "description";
    protected static final String GOAL_ARG = "goal";
    protected static final String CATEGORY_ARG = "category";
    protected static final String DATE_ARG = "date";

    private static final String MISSING_IDX = "Index cannot be empty...";
    private static final String INVALID_IDX = "Please enter a valid index.";
    private static final String MISSING_TYPE = "Please indicate the transaction type.";
    private static final String INVALID_TYPE = "Please indicate either /type in or /type out.";
    private static final String MISSING_EDIT = "Please enter the attribute to edit";
    private static final String BAD_AMOUNT = "Invalid amount value specified.";
    private static final String DATE_EDIT = "Date cannot be edited.";
    private static final String TYPE_ARG = "type";
    private static final String TYPE_IN = "in";
    private static final String TYPE_OUT = "out";
    private static final String MISSING_GOAL = "Please enter the goal value.";
    private static final String MISSING_CATEGORY = "Please enter the category value.";
    private static final String MISSING_DESCRIPTION = "Please enter the description.";

    public EditTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        throwIfInvalidDescOrArgs();
        editTransaction(ui);
    }

    private void throwIfInvalidDescOrArgs() throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;

        checkIndex();
        checkType();
        if (getArg(TYPE_ARG).equalsIgnoreCase(TYPE_IN)) {
            checkGoal();
        } else if (getArg(TYPE_ARG).equalsIgnoreCase(TYPE_OUT)) {
            checkCategory();
        }
        checkAmount();
        checkDescription();
        checkHasArgument();
        checkDate();
    }

    private void checkDate() throws DukeException {
        if (getArg(DATE_ARG) != null && !getArg(DATE_ARG).isBlank()) {
            throw new DukeException(DATE_EDIT);
        }
    }

    private void checkIndex() throws DukeException {
        if (getDescription().isBlank()) {
            throw new DukeException(MISSING_IDX);
        }
        String description = getDescription();
        if (!isInteger(description)) {
            throw new DukeException(INVALID_IDX);
        }
    }

    private void checkType() throws DukeException {
        String typeArg = getArg(TYPE_ARG);
        if (typeArg == null) {
            throw new DukeException(MISSING_TYPE);
        }

        if (!(typeArg.equalsIgnoreCase(TYPE_IN) || typeArg.equalsIgnoreCase(TYPE_OUT))) {
            throw new DukeException(INVALID_TYPE);
        }
    }

    private void checkGoal() throws DukeException {
        if (!getArgs().containsKey(GOAL_ARG)) {
            return;
        }

        if (getArg(GOAL_ARG).isBlank()) {
            throw new DukeException(MISSING_GOAL);
        }

        String newGoalName = getArg(GOAL_ARG);
        int newGoalIdx = StateManager.getStateManager().getGoalIndex(newGoalName);
        if (newGoalIdx == -1 && !newGoalName.equalsIgnoreCase(StateManager.UNCATEGORISED_CLASS)) {
            throw new DukeException("Please add " + newGoalName + " as a goal first.");
        }
    }

    private void checkCategory() throws DukeException {
        if (!getArgs().containsKey(CATEGORY_ARG)) {
            return;
        }

        if (getArg(CATEGORY_ARG).isBlank()) {
            throw new DukeException(MISSING_CATEGORY);
        }
    }

    private boolean isInteger(String description) {
        try {
            Integer.parseInt(description);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void checkAmount() throws DukeException {
        if (getArg(AMOUNT_ARG) != null) {
            if (getArg(AMOUNT_ARG).isBlank()) {
                throw new DukeException(BAD_AMOUNT);
            }
            Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG));
            if (amount == null) {
                throw new DukeException(BAD_AMOUNT);
            }
        }
    }

    private void checkDescription() throws DukeException {
        if (!getArgs().containsKey(DESCRIPTION_ARG)) {
            return;
        }
        if (getArg(DESCRIPTION_ARG).isBlank()) {
            throw new DukeException(MISSING_DESCRIPTION);
        }
    }

    private void checkHasArgument() throws DukeException {
        boolean hasDescArg = getArgs().containsKey(DESCRIPTION_ARG);
        boolean hasGoalArg = getArgs().containsKey(GOAL_ARG);
        boolean hasCategoryArg = getArgs().containsKey(CATEGORY_ARG);
        boolean hasAmountArg = getArgs().containsKey(AMOUNT_ARG);
        boolean isInType = getArg(TYPE_ARG).equalsIgnoreCase(TYPE_IN);
        boolean isOutType = getArg(TYPE_ARG).equalsIgnoreCase(TYPE_OUT);
        if (!getArgs().containsKey(DESCRIPTION_ARG)) {
            return;
        }
        if (getArg(DESCRIPTION_ARG).isBlank()) {
            throw new DukeException(MISSING_DESCRIPTION);
        }

        if (isInType && !(hasDescArg || hasAmountArg || hasGoalArg)) {
            throw new DukeException(MISSING_EDIT);
        } else if (isOutType && !(hasDescArg || hasAmountArg || hasCategoryArg)) {
            throw new DukeException(MISSING_EDIT);
        }
    }

    private void editTransaction(Ui ui) throws DukeException {
        String type = getArg(TYPE_ARG).toLowerCase();
        int maxSize = getTransactionMaxSize(type);
        int idx = parseIdx(maxSize) - 1; //-1 due to 0 based indexing for arraylist
        assert idx >= 0 : "Index should be a valid integer greater than 0";

        String transactionDescription = "";
        if (type.equals(TYPE_IN)) {
            editIncome(idx);
            transactionDescription = StateManager.getStateManager().getIncome(idx)
                    .getTransaction().getDescription();

        } else if (type.equals(TYPE_OUT)) {
            editExpense(idx);
            transactionDescription = StateManager.getStateManager().getExpense(idx)
                    .getTransaction().getDescription();
        }

        if (!transactionDescription.isBlank()) {
            printSuccess(ui, transactionDescription, idx + 1); // idx + 1 for format to show to user
        }
    }

    private int getTransactionMaxSize(String type) {
        int maxSize = 0;
        if (type.equals(TYPE_IN)) {
            maxSize = StateManager.getStateManager().getIncomesSize();
        } else if (type.equals(TYPE_OUT)) {
            maxSize = StateManager.getStateManager().getExpensesSize();
        }
        return maxSize;
    }

    private int parseIdx(int maxSize) throws DukeException {
        int index = Integer.parseInt(getDescription());
        if (index < 1 || index > maxSize) {
            throw new DukeException(INVALID_IDX);
        }
        return index;
    }

    private void printSuccess(Ui ui, String description, int idx) {
        String type = getArg(TYPE_ARG).toLowerCase();
        String transactionType = type.equals(TYPE_IN) ? "income" : "expense";
        String msg = "Successfully edited " + transactionType + " no." + idx + " " + description;
        ui.print(msg);
    }

    private void handleGoalEdit(int idx) {
        StateManager stateManager = StateManager.getStateManager();
        String newGoalDescription = getArg(GOAL_ARG);

        int newGoalIdx = stateManager.getGoalIndex(newGoalDescription);
        Goal newGoal = stateManager.getGoal(newGoalIdx);
        if (newGoal == null) {
            assert newGoalDescription.equals(StateManager.UNCATEGORISED_CLASS);
            newGoal = stateManager.getUncategorisedGoal();
        }
        stateManager.getIncome(idx).setGoal(newGoal);
    }

    private void handleCategoryEdit(int idx) {
        StateManager stateManager = StateManager.getStateManager();
        String newCategoryDescription = getArg(CATEGORY_ARG);
        Expense expense = stateManager.getExpense(idx);
        if (newCategoryDescription.equalsIgnoreCase(StateManager.UNCATEGORISED_CLASS)) {
            expense.setCategory(stateManager.getUncategorisedCategory());
            return;
        }

        int newCategoryIdx = stateManager.getCategoryIndex(newCategoryDescription);
        Category newCategory = stateManager.getCategory(newCategoryIdx);
        if (newCategory == null) {
            newCategory = new Category(newCategoryDescription);
        }
        expense.setCategory(newCategory);
    }

    private void editIncome(int idx) {
        if (getArgs().containsKey(DESCRIPTION_ARG)) {
            StateManager.getStateManager().getIncome(idx)
                    .getTransaction().setDescription(getArg(DESCRIPTION_ARG));
        }
        if (getArgs().containsKey(AMOUNT_ARG)) {
            StateManager.getStateManager().getIncome(idx)
                    .getTransaction().setAmount(Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG)));
        }
        if (getArgs().containsKey(GOAL_ARG)) {
            handleGoalEdit(idx);
        }
    }

    private void editExpense(int idx) {
        if (getArgs().containsKey(DESCRIPTION_ARG)) {
            StateManager.getStateManager().getExpense(idx)
                    .getTransaction().setDescription(getArg(DESCRIPTION_ARG));
        }
        if (getArgs().containsKey(AMOUNT_ARG)) {
            StateManager.getStateManager().getExpense(idx)
                    .getTransaction().setAmount(Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG)));
        }
        if (getArgs().containsKey(CATEGORY_ARG)) {
            handleCategoryEdit(idx);
        }
    }

}
