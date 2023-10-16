package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListCommand extends Command {
    private static final String INVALID_TYPE_FORMAT = "I'm sorry, you need to specify a type in the format " +
            "'/type in' or '/type out'";
    private static final String INVALID_GOAL_FORMAT = "You have entered /goal, but you did not enter anything" +
            " after that";
    private static final String INVALID_CATEGORY_FORMAT = "You have entered /category, but you did not enter" +
            " anything after that";
    private static final String EMPTY_LIST = "It appears that we have came up empty. Why not try adding some" +
            " transactions first?";
    private static final String[] HEADERS = {"ID", "Description", "Date", "Amount", "Goal"};
    private static final String IN = "IN TRANSACTIONS";
    private static final String OUT = "OUT TRANSACTIONS";
    private Ui ui;

    public ListCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    @Override
    public void execute(Ui ui) throws DukeException {
        this.ui = ui;
        validateArgs();
        listTypeHandler();
    }

    // Description gets ignored for list
    private void validateArgs() throws DukeException {
        if (!getArgs().containsKey("type")) {
            throw new DukeException(INVALID_TYPE_FORMAT);
        }

        String type = getArg("type");
        if (!type.equals("in") && !type.equals("out")) {
            throw new DukeException(INVALID_TYPE_FORMAT);
        }

        if (getArgs().containsKey("goal") && getArg("goal").isBlank()) {
            throw new DukeException(INVALID_GOAL_FORMAT);
        }

        if (getArgs().containsKey("category") && getArg("goal").isBlank()) {
            throw new DukeException(INVALID_CATEGORY_FORMAT);
        }
    }

    private void listTypeHandler() throws DukeException {
        ArrayList<ArrayList<String>> returnArray;
        String type = getArg("type");
        assert type != null;
        if (type.equals("in")) {
            listIncome();
        } else {
            listExpenses();
        }
    }

    private void printList(ArrayList<ArrayList<String>> listArray, String headerMessage) {
        ui.listTransactions(listArray, HEADERS, headerMessage);
    }

    private void listIncome() throws DukeException {
        ArrayList<Income> incomeArray = StateManager.getStateManager().getAllIncomes();
        ArrayList<ArrayList<String>> printIncomes = new ArrayList<>();
        if (incomeArray == null || incomeArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }
        int index = 1;
        for (Income i : incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String description = currentTransaction.getDescription();
            String date = currentTransaction.getDate().toString();
            String amount = String.valueOf(ui.formatAmount(currentTransaction.getAmount()));
            String goal = "TBC";
            // TODO uncomment once goal is implemented
            // String goal = i.getGoal().getDescription();
            printIncomes.add(new ArrayList<>(Arrays.asList(String.valueOf(index), description, date, amount, goal)));
            index++;
        }
        printList(printIncomes, IN);

    }

    private void listExpenses() throws DukeException {
        ArrayList<Expense> expenseArray = StateManager.getStateManager().getAllExpenses();
        ArrayList<ArrayList<String>> printExpenses = new ArrayList<>();
        if (expenseArray == null || expenseArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }
        int index = 1;
        for (Expense i : expenseArray) {
            Transaction currentExpense = i.getTransaction();
            String description = currentExpense.getDescription();
            String date = currentExpense.getDate().toString();
            String amount = String.valueOf(currentExpense.getAmount());
            String category = "TBC";
            // TODO uncomment once category is implemented
            // String category = i.getCategory().getName();
            printExpenses.add(new ArrayList<>(Arrays.asList(String.valueOf(index), description, date,
                    amount, category)));
            index++;
        }
        printList(printExpenses, OUT);
    }
}
