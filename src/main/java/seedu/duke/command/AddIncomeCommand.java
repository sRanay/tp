package seedu.duke.command;

import seedu.duke.classes.Goal;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class AddIncomeCommand extends AddTransactionCommand {
    private static final String GOAL_ARG = "goal";
    private static final String[] HEADERS = {"Description", "Date", "Amount", "Goal", "Recurrence"};

    private static final String SUCCESS_PRINT = "Nice! The following income has been tracked:";
    private static final String MISSING_GOAL = "Goal cannot be empty...";

    public AddIncomeCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    /**
     * Executes the command
     * @param ui Ui class that is used to format output
     * @throws DukeException if user input is invalid
     */
    @Override
    public void execute(Ui ui) throws DukeException {
        throwIfInvalidDescOrArgs();
        Transaction transaction = prepareTransaction();
        Income income = addNewIncome(transaction);
        printSuccess(ui, income);
        StateManager.getStateManager().sortIncomes();
    }

    /**
     * Adds a new Income to the Income arraylist in StateManager
     * @param transaction transaction to add to Income object
     * @return Income object to be used for printing in printSuccess
     * @throws DukeException if income is invalid, or any issue is encountered when adding income
     */
    private Income addNewIncome(Transaction transaction) throws DukeException {
        Goal goal = handleGoal();
        Income income = new Income(transaction, goal);
        StateManager.getStateManager().addIncome(income);
        return income;
    }

    /**
     * Print successful addition of income transaction message
     * @param ui Ui class for printing
     * @param income income transaction to print
     */
    private void printSuccess(Ui ui, Income income) {
        Transaction transaction = income.getTransaction();
        ArrayList<String> printValues = new ArrayList<>();
        printValues.add(transaction.getDescription());
        printValues.add(transaction.getDate().toString());
        printValues.add(ui.formatAmount(transaction.getAmount()));
        printValues.add(income.getGoal().getDescription());
        printValues.add(income.getTransaction().getRecurrence().toString());
        ui.print(SUCCESS_PRINT);
        ui.printTableRow(printValues, HEADERS, HEADERS_WIDTH);
    }

    /**
     * Validates user input for the /category argument and retrieves an income object
     * @return goal of the transaction
     * @throws DukeException if goal user input is invalid
     */
    private Goal handleGoal() throws DukeException {
        StateManager state = StateManager.getStateManager();
        String goal = getArg(GOAL_ARG);
        if (goal == null) {
            return state.getUncategorisedGoal();
        } else if (goal.equalsIgnoreCase(StateManager.UNCATEGORISED_CLASS)) {
            return state.getUncategorisedGoal();
        } else if (goal.isBlank()) {
            throw new DukeException(MISSING_GOAL);
        }
        int index = state.getGoalIndex(goal);
        if (index == -1) {
            String failedGoalMessage = "Please add '" + goal + "' as a goal first.";
            throw new DukeException(failedGoalMessage);
        } else {
            return state.getGoal(index);
        }
    }
}
