package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.Expense;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class AddExpenseCommand extends Command {
    private static final String AMOUNT_ARG = "amount";
    private static final String[] HEADERS = {"Description", "Amount", "Category"};

    private static final String SUCCESS_PRINT = "Nice! The following expense has been tracked:";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String MISSING_CATEGORY = "Category cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";

    public AddExpenseCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        // TODO:
        //  Add dates arg to command
        throwIfInvalidDescOrArgs();
        Transaction transaction = prepareTransaction();
        Expense expense = addNewExpense(transaction);
        printSuccess(ui, expense);
    }

    private Expense addNewExpense(Transaction transaction) {
        Category category = handleCategory();
        Expense expense = new Expense(transaction, category);
        StateManager.getStateManager().addExpense(expense);
        return expense;
    }

    private Transaction prepareTransaction() {
        String description = getDescription();
        Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG));
        return new Transaction(description, amount, null);
    }

    private void printSuccess(Ui ui, Expense expense) {
        Transaction transaction = expense.getTransaction();
        ArrayList<String> printValues = new ArrayList<>();
        printValues.add(transaction.getDescription());
        printValues.add(ui.formatAmount(transaction.getAmount()));
        printValues.add(expense.getCategory().getName());
        ui.print(SUCCESS_PRINT);
        ui.printTableRow(printValues, HEADERS);
    }

    private void throwIfInvalidDescOrArgs() throws DukeException {
        // TODO:
        //  Ensure category is non-null - after V1.0
        assert getDescription() != null;
        assert getArgs() != null;

        if (getDescription().isBlank()) {
            throw new DukeException(MISSING_DESC);
        }

        String amountArg = getArg(AMOUNT_ARG);
        if (amountArg == null) {
            throw new DukeException(MISSING_AMOUNT);
        }

        Double amount = Parser.parseNonNegativeDouble(amountArg);
        if (amount == null) {
            throw new DukeException(BAD_AMOUNT);
        }

        String category = getArg("category");
        if (category == null || category.isBlank()) {
            throw new DukeException(MISSING_CATEGORY);
        }
    }

    private Category handleCategory() {
        StateManager state = StateManager.getStateManager();
        String category = getArg("category");
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
