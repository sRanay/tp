package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.Expense;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class AddExpenseCommand extends AddTransactionCommand {
    private static final String CATEGORY_ARG = "category";
    private static final String[] HEADERS = {"Description", "Date", "Amount", "Category", "Recurrence"};

    private static final String SUCCESS_PRINT = "Nice! The following expense has been tracked:";
    private static final String MISSING_CATEGORY = "Category cannot be empty...";

    public AddExpenseCommand(String description, HashMap<String, String> args) {
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
        throwIfInvalidDescOrArgs();
        Transaction transaction = prepareTransaction();
        Expense expense = addNewExpense(transaction);
        printSuccess(ui, expense);
        StateManager.getStateManager().sortExpenses();
    }

    /**
     * Adds a new expense to the Expense arraylist in StateManager
     * @param transaction transaction to add to Expense object
     * @return Expense object to be used for printing in printSuccess
     * @throws DukeException if category is invalid, or any issue is encountered when adding expense
     */
    private Expense addNewExpense(Transaction transaction) throws DukeException {
        Category category = handleCategory();
        Expense expense = new Expense(transaction, category);
        StateManager.getStateManager().addExpense(expense);
        return expense;
    }

    /**
     * Print successful addition of expense transaction message
     * @param ui Ui class for printing
     * @param expense expense transaction to print
     */
    private void printSuccess(Ui ui, Expense expense) {
        Transaction transaction = expense.getTransaction();
        ArrayList<String> printValues = new ArrayList<>();
        printValues.add(transaction.getDescription());
        printValues.add(transaction.getDate().toString());
        printValues.add(ui.formatAmount(transaction.getAmount()));
        printValues.add(expense.getCategory().getName());
        printValues.add(expense.getTransaction().getRecurrence().toString());
        ui.print(SUCCESS_PRINT);
        ui.printTableRow(printValues, HEADERS, HEADERS_WIDTH);
    }

    /**
     * Validates user input for the /category argument and retrieves/add a category object
     * @return category of the transaction
     * @throws DukeException if category user input is invalid
     */
    private Category handleCategory() throws DukeException {
        StateManager state = StateManager.getStateManager();
        String category = getArg(CATEGORY_ARG);
        if (category == null) {
            return state.getUncategorisedCategory();
        } else if (category.equalsIgnoreCase(StateManager.UNCATEGORISED_CLASS)) {
            return state.getUncategorisedCategory();
        } else if (category.isBlank()) {
            throw new DukeException(MISSING_CATEGORY);
        }
        int index = state.getCategoryIndex(category);
        if (index == -1) {
            Category categoryToAdd = new Category(category);
            state.addCategory(categoryToAdd);
            return categoryToAdd;
        } else {
            return state.getCategory(index);
        }
    }
}
