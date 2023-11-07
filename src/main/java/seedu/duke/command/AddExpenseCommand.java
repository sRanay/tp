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

    @Override
    public void execute(Ui ui) throws DukeException {
        throwIfInvalidDescOrArgs();
        Transaction transaction = prepareTransaction();
        Expense expense = addNewExpense(transaction);
        printSuccess(ui, expense);
    }

    private Expense addNewExpense(Transaction transaction) throws DukeException {
        Category category = handleCategory();
        Expense expense = new Expense(transaction, category);
        StateManager.getStateManager().addExpense(expense);
        return expense;
    }

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

    private Category handleCategory() throws DukeException {
        StateManager state = StateManager.getStateManager();
        String category = getArg(CATEGORY_ARG);
        if (category == null) {
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
