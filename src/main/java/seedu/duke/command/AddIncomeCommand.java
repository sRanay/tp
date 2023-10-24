package seedu.duke.command;

import seedu.duke.classes.Goal;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class AddIncomeCommand extends Command {
    private static final String AMOUNT_ARG = "amount";
    private static final String[] HEADERS = {"Description", "Amount", "Goal"};

    private static final String SUCCESS_PRINT = "Nice! The following income has been tracked:";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String MISSING_GOAL = "Goal cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";

    public AddIncomeCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        // TODO:
        //  Add dates arg to command
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

    private Transaction prepareTransaction() {
        String description = getDescription();
        Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG));
        return new Transaction(description, amount, null);
    }

    private void printSuccess(Ui ui, Income income) {
        Transaction transaction = income.getTransaction();
        ArrayList<String> printValues = new ArrayList<>();
        printValues.add(transaction.getDescription());
        printValues.add(ui.formatAmount(transaction.getAmount()));
        printValues.add(income.getGoal().getDescription());
        ui.print(SUCCESS_PRINT);
        ui.printTableRow(printValues, HEADERS);
    }

    private void throwIfInvalidDescOrArgs() throws DukeException {
        // TODO:
        //  Ensure goal is non-null - after V1.0
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
        String goal = getArg("goal");
        if (goal == null || goal.isBlank()) {
            throw new DukeException(MISSING_GOAL);
        }
    }

    private Goal handleGoal() throws DukeException {
        StateManager state = StateManager.getStateManager();
        String goal = getArg("goal");
        int index = state.getGoalIndex(goal);
        if (index == -1) {
            String failedGoalMessage = "Please add '" + goal + "' as a goal first.";
            throw new DukeException(failedGoalMessage);
        } else {
            return state.getGoal(index);
        }
    }
}
