package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;
import seedu.duke.classes.StateManager;

import java.util.HashMap;


public class RemoveTransactionCommand extends Command {

    private static final String MISSING_IDX = "Index cannot be empty...";
    private static final String INVALID_IDX = "Please enter a valid index.";
    private static final String MISSING_TYPE = "Please indicate the transaction type.";

    private static final String INVALID_TYPE = "Please indicate either /type in or /type out.";

    private static final String ERROR_MSG = "Error encountered when removing transaction.";
    private static final String TYPE_ARG = "type";
    private static final String TYPE_IN = "in";
    private static final String TYPE_OUT = "out";


    public RemoveTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    /**
     * Executes the command.
     *
     * @param ui Ui class that is used to print in standardised format.
     * @throws DukeException if the file cannot be created during the exporting process.
     */
    @Override
    public void execute(Ui ui) throws DukeException {
        throwIfInvalidDescOrArgs();
        removeTransaction(ui);
    }

    private void throwIfInvalidDescOrArgs() throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;

        if (getDescription().isBlank()) {
            throw new DukeException(MISSING_IDX);
        }
        String description = getDescription();
        if (!isInteger(description)) {
            throw new DukeException(INVALID_IDX);
        }

        String typeArg = getArg(TYPE_ARG);
        if (typeArg == null) {
            throw new DukeException(MISSING_TYPE);
        }

        if (!(typeArg.equalsIgnoreCase(TYPE_IN) || typeArg.equalsIgnoreCase(TYPE_OUT))) {
            throw new DukeException(INVALID_TYPE);
        }
    }

    /**
     * Checks if the description is a valid integer.
     *
     * @param description the description of the user input.
     * @return true if the input is a valid integer, else false.
     */
    private boolean isInteger(String description) {
        try {
            Integer.parseInt(description);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Removes the transaction from StateManager based on the type.
     * Prints success message if successful.
     *
     * @param ui Ui class that is used to print in standardised format.
     * @throws DukeException if the transaction cannot be removed.
     */
    private void removeTransaction(Ui ui) throws DukeException {
        String type = getArg(TYPE_ARG).toLowerCase();
        int maxSize = getTransactionMaxSize(type);
        int idx = parseIdx(maxSize) - 1; //-1 due to 0 based indexing for arraylist
        assert idx >= 0 : "Index should be a valid integer greater than 0";

        boolean isSuccess = false;
        String transactionDescription = "";
        if (type.equals(TYPE_IN)) {
            Income incomeEntry = StateManager.getStateManager().getIncome(idx);
            transactionDescription = incomeEntry.getTransaction().getDescription();
            isSuccess = StateManager.getStateManager().removeIncome(incomeEntry);
        } else if (type.equals(TYPE_OUT)) {
            Expense expenseEntry = StateManager.getStateManager().getExpense(idx);
            transactionDescription = expenseEntry.getTransaction().getDescription();
            isSuccess = StateManager.getStateManager().removeExpense(idx);
        }
        if (!isSuccess) {
            throw new DukeException(ERROR_MSG);
        }
        printSuccess(ui, transactionDescription, idx + 1); // idx + 1 for format to show to user
    }

    /**
     * Returns the total number of transaction based on the type.
     *
     * @param type type of transaction (in/out).
     * @return int total transactions.
     */
    private int getTransactionMaxSize(String type) {
        int maxSize = 0;
        if (type.equals(TYPE_IN)) {
            maxSize = StateManager.getStateManager().getIncomesSize();
        } else if (type.equals(TYPE_OUT)) {
            maxSize = StateManager.getStateManager().getExpensesSize();
        }
        return maxSize;
    }

    /**
     * Returns the valid index.
     *
     * @param maxSize max number of transactions based on the type.
     * @return int valid index.
     * @throws DukeException if the index is not in range of the number of transactions.
     */
    private int parseIdx(int maxSize) throws DukeException {
        int index = Integer.parseInt(getDescription());
        if (index < 1 || index > maxSize) {
            throw new DukeException(INVALID_IDX);
        }
        return index;
    }

    /**
     * Prints success message when the transaction is removed.
     *
     * @param ui Ui class that is used to print in standardised format.
     * @param description description of the transaction.
     * @param idx index of the transaction.
     */
    private void printSuccess(Ui ui, String description, int idx) {
        String type = getArg(TYPE_ARG).toLowerCase();
        String transactionType = type.equals(TYPE_IN) ? "income" : "expense";
        String msg = "Successfully removed " + transactionType + " no." + idx + ": " + description;
        ui.print(msg);
    }


}
