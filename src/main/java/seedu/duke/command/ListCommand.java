package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
    private static final String[] IN_HEADERS = {"ID", "Description", "Date", "Amount", "Goal"};
    private static final String[] OUT_HEADERS = {"ID", "Description", "Date", "Amount", "Category"};
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
        String type = getArg("type");
        assert type != null;
        if (type.equals("in")) {
            listIncome();
        } else {
            listExpenses();
        }
    }

    private void printList(ArrayList<ArrayList<String>> listArray, String headerMessage) {
        if (headerMessage.equals(IN)) {
            ui.listTransactions(listArray, IN_HEADERS, headerMessage);
        } else {
            ui.listTransactions(listArray, OUT_HEADERS, headerMessage);
        }

    }

    private void listIncome() throws DukeException {
        ArrayList<Income> incomeArray = StateManager.getStateManager().getAllIncomes();
        ArrayList<ArrayList<String>> printIncomes = new ArrayList<>();
        if (incomeArray == null || incomeArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }

        if (getArgs().containsKey("week")) {
            incomeArray = filterIncome(incomeArray, false);
        } else if (getArgs().containsKey("month")) {
            incomeArray = filterIncome(incomeArray, true);
        }

        int index = 1;
        for (Income i : incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String description = currentTransaction.getDescription();
            String date = currentTransaction.getDate().toString();
            String amount = String.valueOf(ui.formatAmount(currentTransaction.getAmount()));
            String goal = i.getGoal().getDescription();
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

        if (getArgs().containsKey("week")) {
            expenseArray = filterExpense(expenseArray, false);
        } else if (getArgs().containsKey("month")) {
            expenseArray = filterExpense(expenseArray, true);
        }

        int index = 1;
        for (Expense i : expenseArray) {
            Transaction currentExpense = i.getTransaction();
            String description = currentExpense.getDescription();
            String date = currentExpense.getDate().toString();
            String amount = String.valueOf(ui.formatAmount(currentExpense.getAmount()));
            String category = i.getCategory().getName();
            printExpenses.add(new ArrayList<>(Arrays.asList(String.valueOf(index), description, date,
                    amount, category)));
            index++;
        }
        printList(printExpenses, OUT);
    }

    private ArrayList<Income> filterIncome(ArrayList<Income> transactionsArrayList, boolean filterByMonth) {
        ArrayList<Income> filteredArrayList = new ArrayList<>();
        for (Income transaction : transactionsArrayList) {
            LocalDate transactionDate = transaction.getTransaction().getDate();
            if (!filterByMonth && isThisWeek(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByMonth && isThisMonth(transactionDate)) {
                filteredArrayList.add(transaction);
            }
        }
        return filteredArrayList;
    }

    private ArrayList<Expense> filterExpense(ArrayList<Expense> transactionsArrayList, boolean filterByMonth) {
        ArrayList<Expense> filteredArrayList = new ArrayList<>();
        for (Expense transaction : transactionsArrayList) {
            LocalDate transactionDate = transaction.getTransaction().getDate();
            if (!filterByMonth && isThisWeek(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByMonth && isThisMonth(transactionDate)) {
                filteredArrayList.add(transaction);
            }
        }
        return filteredArrayList;
    }

    private boolean isThisWeek(LocalDate transactionDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        if (transactionDate.isBefore(startOfWeek) || transactionDate.isAfter(endOfWeek)) {
            return false;
        }
        return true;
    }

    private boolean isThisMonth(LocalDate transactionDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.withDayOfMonth(1);
        LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        if (transactionDate.isBefore(startOfMonth) || transactionDate.isAfter(endOfMonth)) {
            return false;
        }
        return true;
    }
}
