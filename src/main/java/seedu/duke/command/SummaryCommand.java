package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;

public class SummaryCommand extends Command {

    private static final String TYPE_ARG = "type";
    private static final String TYPE_IN = "in";
    private static final String TYPE_OUT = "out";
    private static final String MISSING_TYPE = "Please indicate the transaction type.";
    private static final String INVALID_TYPE = "Please indicate either /type in or /type out.";
    private static final String EMPTY_LIST = "It appears that we have come up empty. Why not try adding some" +
            " transactions first?";
    private static final String STARTING_INCOME_MSG = "Good job! Total income so far";
    private static final String STARTING_EXPENSE_MSG = "Wise spending! Total expense so far";
    private static final String DAY_ARG = "day";
    private static final String WEEK_ARG = "week";
    private static final String MONTH_ARG = "month";

    private LocalDate currentDate;
    private boolean filterByDay = false;
    private boolean filterByWeek = false;
    private boolean filterByMonth = false;
    private Ui ui;

    public SummaryCommand(String description, HashMap<String, String> args) {
        super(description, args);
        currentDate = LocalDate.now();
    }

    public SummaryCommand(String description, HashMap<String, String> args, LocalDate currentDate) {
        super(description, args);
        this.currentDate = currentDate;
    }

    /**
     * Executes the command.
     *
     * @param ui Ui class that is used to print in standardised format.
     * @throws DukeException if the file cannot be created during the exporting process.
     */
    @Override
    public void execute(Ui ui) throws DukeException {
        this.ui = ui;
        throwIfInvalidDescOrArgs();
        getFilter();
        printSummary();
    }

    private void throwIfInvalidDescOrArgs() throws DukeException {
        assert getArgs() != null;

        String typeArg = getArg(TYPE_ARG);
        if (typeArg == null) {
            throw new DukeException(MISSING_TYPE);
        }

        if (!(typeArg.equalsIgnoreCase(TYPE_IN) || typeArg.equalsIgnoreCase(TYPE_OUT))) {
            throw new DukeException(INVALID_TYPE);
        }
    }

    private void getFilter() {
        if (getArgs().containsKey(DAY_ARG)) {
            filterByDay = true;
        } else if (getArgs().containsKey(WEEK_ARG)) {
            filterByWeek = true;
        } else if (getArgs().containsKey(MONTH_ARG)) {
            filterByMonth = true;
        }
    }

    /**
     * Returns the total sum of the income transaction.
     *
     * @return double total income.
     * @throws DukeException if there is no income transaction available.
     */
    private double getIncomeSummary() throws DukeException {
        ArrayList<Income> incomeArray = StateManager.getStateManager().getAllIncomes();
        if (incomeArray == null || incomeArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }
        if (filterByDay || filterByWeek || filterByMonth) {
            incomeArray = filterIncome(incomeArray);
        }
        double totalSum = 0;
        for (Income income : incomeArray) {
            totalSum = totalSum + income.getTransaction().getAmount();
        }

        return totalSum;
    }

    /**
     * Returns filtered arraylist of income transactions.
     * Filters the income transactions based on the filter indicated.
     *
     * @param transactionsArrayList arraylist of income transaction.
     * @return ArrayList of income transaction.
     */
    private ArrayList<Income> filterIncome(ArrayList<Income> transactionsArrayList) {
        ArrayList<Income> filteredArrayList = new ArrayList<>();
        for (Income transaction : transactionsArrayList) {
            LocalDate transactionDate = transaction.getTransaction().getDate();
            if (filterByDay && isSameDay(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByWeek && isSameWeek(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByMonth && isSameMonth(transactionDate)) {
                filteredArrayList.add(transaction);
            }
        }
        return filteredArrayList;
    }

    /**
     * Returns the total sum of the expense transaction.
     *
     * @return double total expense.
     * @throws DukeException if there is no expense transaction available.
     */
    private double getExpenseSummary() throws DukeException {
        ArrayList<Expense> expenseArray = StateManager.getStateManager().getAllExpenses();
        if (expenseArray == null || expenseArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }
        if (filterByDay || filterByWeek || filterByMonth) {
            expenseArray = filterExpense(expenseArray);
        }
        double totalSum = 0;
        for (Expense expense : expenseArray) {
            totalSum = totalSum + expense.getTransaction().getAmount();
        }

        return totalSum;
    }

    /**
     * Returns filtered arraylist of expense transactions.
     * Filters the expense transactions based on the filter indicated.
     *
     * @param transactionsArrayList arraylist of expense transaction.
     * @return ArrayList of expense transaction.
     */
    private ArrayList<Expense> filterExpense(ArrayList<Expense> transactionsArrayList) {
        ArrayList<Expense> filteredArrayList = new ArrayList<>();
        for (Expense transaction : transactionsArrayList) {
            LocalDate transactionDate = transaction.getTransaction().getDate();
            if (filterByDay && isSameDay(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByWeek && isSameWeek(transactionDate)) {
                filteredArrayList.add(transaction);
            } else if (filterByMonth && isSameMonth(transactionDate)) {
                filteredArrayList.add(transaction);
            }
        }
        return filteredArrayList;
    }

    private boolean isSameDay(LocalDate transactionDate) {
        return currentDate.isEqual(transactionDate);
    }

    private boolean isSameWeek(LocalDate transactionDate) {
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        if (transactionDate.isBefore(startOfWeek) || transactionDate.isAfter(endOfWeek)) {
            return false;
        }
        return true;
    }

    private boolean isSameMonth(LocalDate transactionDate) {
        LocalDate startOfMonth = currentDate.withDayOfMonth(1);
        LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        if (transactionDate.isBefore(startOfMonth) || transactionDate.isAfter(endOfMonth)) {
            return false;
        }
        return true;
    }

    private void printSummary() throws DukeException {
        String msg = "";
        if (getArg(TYPE_ARG).equalsIgnoreCase(TYPE_IN)) {
            double totalSum = getIncomeSummary();
            msg = getSummaryMsg(TYPE_IN, totalSum);
        } else if (getArg(TYPE_ARG).equalsIgnoreCase(TYPE_OUT)) {
            double totalSum = getExpenseSummary();
            msg = getSummaryMsg(TYPE_OUT, totalSum);
        }
        ui.print(msg);
    }

    private String getSummaryMsg(String type, double totalSum) {
        String msg = "";
        if (type.equalsIgnoreCase(TYPE_IN)) {
            msg = STARTING_INCOME_MSG;
        } else {
            msg = STARTING_EXPENSE_MSG;
        }
        if (filterByDay) {
            msg = msg + " for Today: $";
        } else if (filterByWeek) {
            msg = msg + " for This Week: $";
        } else if (filterByMonth) {
            msg = msg + " for This Month: $";
        } else {
            msg = msg + ": $";
        }
        return msg + ui.formatAmount(totalSum);
    }
}
