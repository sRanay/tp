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
    private static final String UNCATEGORISED = "Uncategorised";

    public AddIncomeCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        throwIfInvalidDescOrArgs();
        Transaction transaction = prepareTransaction();
        Income income = addNewIncome(transaction);
        printSuccess(ui, income);
    }

    private Income addNewIncome(Transaction transaction) throws DukeException {
        Goal goal = handleGoal();
        Income income = new Income(transaction, goal);
        StateManager.getStateManager().addIncome(income);
        return income;
    }

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

    private Goal handleGoal() throws DukeException {
        StateManager state = StateManager.getStateManager();
        String goal = getArg(GOAL_ARG);
        if (goal == null) {
            return state.getUncategorisedGoal();
        } else if (goal.equalsIgnoreCase(UNCATEGORISED)) {
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
