package seedu.duke.command;

import seedu.duke.classes.Category;
import seedu.duke.classes.Expense;
import seedu.duke.classes.Goal;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;

public class ListCommand extends Command {
    private static final String INVALID_TYPE_FORMAT = "I'm sorry, you need to specify a type in the format " +
            "'list /type in' or 'list /type out' to view transactions, or 'list goal' and 'list category'";
    private static final String INVALID_GOAL_FORMAT = "You have entered /goal, but you have entered an invalid goal";
    private static final String INVALID_CATEGORY_FORMAT = "You have entered /category, but you have entered an " +
            "invalid category";
    private static final String EMPTY_LIST = "It appears that we have come up empty. Why not try adding some" +
            " transactions first?";
    private static final String[] IN_HEADERS = {"ID", "Description", "Date", "Amount", "Goal", "Recurrence"};
    private static final String[] OUT_HEADERS = {"ID", "Description", "Date", "Amount", "Category", "Recurrence"};
    private static final String IN = "IN TRANSACTIONS";
    private static final String OUT = "OUT TRANSACTIONS";
    private static final String GOAL = "goal";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String WEEK = "week";
    private static final String MONTH = "month";
    private static final String UNCATEGORISED = "Uncategorised";
    private static final int INVALID_VALUE = -1;
    private Ui ui;

    public ListCommand(String description, HashMap<String, String> args) {
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
        this.ui = ui;
        validateInput();
        listTypeHandler();
    }

    /**
     * Entry point for input validation
     * @throws DukeException if user input is invalid
     */
    private void validateInput() throws DukeException {
        if (validateDescriptionInput()) {
            return;
        }
        checkArgs();
    }

    /**
     * Checks if the user has specified the program arguments correctly
     * @throws DukeException if arguments specified is invalid
     */
    private void checkArgs() throws DukeException {
        if (getArgs().isEmpty()) {
            errorMessage(INVALID_TYPE_FORMAT);
        }

        if (!getArgs().containsKey(TYPE)) {
            errorMessage(INVALID_TYPE_FORMAT);
        }

        if (getArgs().containsKey(TYPE)) {
            String type = getArg(TYPE);
            if (!type.equalsIgnoreCase("in") && !type.equalsIgnoreCase("out")) {
                errorMessage(INVALID_TYPE_FORMAT);
            }
        }

        if (getArgs().containsKey(GOAL) && getArgs().containsKey(CATEGORY)) {
            String multipleTypesError = "You can't use both /goal and /category";
            errorMessage(multipleTypesError);
        }

    }

    /**
     * Checks if user has entered the correct input in the description field
     * @return true if user has specified the 'goal' or 'category' in description
     * @throws DukeException if user enters an invalid input
     */
    private boolean validateDescriptionInput() throws DukeException {
        if (getDescription() == null && getArgs().isEmpty()) {
            String emptyListCommandError = "The list command must be accompanied with additional parameters";
            errorMessage(emptyListCommandError);
        }
        String description = getDescription();
        if (description.isBlank()) {
            return false;
        }
        if (description.equalsIgnoreCase(GOAL) || description.equalsIgnoreCase(CATEGORY)) {
            if (!getArgs().isEmpty()) {
                String parametersPresentError = "There should not be any other options accompanied by " +
                        "'list goal' and 'list category'";
                errorMessage(parametersPresentError);
            }
        } else {
            String invalidDescription = "The only valid description input is 'list goal' and 'list category'";
            errorMessage(invalidDescription);
        }
        return true;
    }

    /**
     * Creates a standardised error message
     * @param message the intended message to add on
     * @throws DukeException to print the error message and not proceed further
     */

    private void errorMessage(String message) throws DukeException {
        String commonMessage = "\nFor correct usage, please refer to the UG or 'help list'";
        throw new DukeException(message + commonMessage);
    }

    /**
     * Identify what type of list the user wants
     * @throws DukeException if any of the called functions throws an exception
     */

    private void listTypeHandler() throws DukeException {
        String description = getDescription();
        if (description != null && !description.isBlank()) {
            printTypeStatus(description);
            return;
        }
        String type = getArg(TYPE);
        assert type != null;
        if (type.equalsIgnoreCase("in")) {
            checkInArgs();
            listIncome();
        } else if (type.equalsIgnoreCase("out")) {
            checkOutArgs();
            listExpenses();
        }
    }

    /**
     * Validate input when user enters 'list /type in' as input
     * @throws DukeException when subsequent arguments are incorrect
     */
    private void checkInArgs() throws DukeException {
        if (getArgs().containsKey(CATEGORY)) {
            errorMessage("'list /type in' should be used with /goal, not /category");
        }

        if (getArgs().containsKey(GOAL)) {
            String goal = getArg(GOAL);
            if (goal.isBlank()) {
                errorMessage(INVALID_GOAL_FORMAT);
            } else if (goal.equalsIgnoreCase(UNCATEGORISED)) {
                return;
            }
            int result = StateManager.getStateManager().getGoalIndex(goal);
            if (result == INVALID_VALUE) {
                errorMessage(INVALID_GOAL_FORMAT);
            }
        }
    }

    /**
     * Validate input when user enters 'list /type out' as input
     * @throws DukeException when subsequent arguments are incorrect
     */
    private void checkOutArgs() throws DukeException {
        if (getArgs().containsKey(GOAL)) {
            errorMessage("'list /type out' should be used with /category, not /goal");
        }
        if (getArgs().containsKey(CATEGORY)) {
            String category = getArg(CATEGORY);
            if (category.isBlank()) {
                errorMessage(INVALID_CATEGORY_FORMAT);
            } else if (category.equalsIgnoreCase(UNCATEGORISED)) {
                return;
            }
            int result = StateManager.getStateManager().getCategoryIndex(category);
            if (result == INVALID_VALUE) {
                errorMessage(INVALID_CATEGORY_FORMAT);
            }
        }
    }

    /**
     * Determines whether to print a list of goals or categories
     * @param description user's input in the description field
     */
    private void printTypeStatus(String description) {
        if (description.equalsIgnoreCase(GOAL)) {
            HashMap<Goal, Double> map = StateManager.getStateManager().getGoalsStatus();
            ui.printGoalsStatus(map);
        } else if (description.equalsIgnoreCase(CATEGORY)) {
            HashMap<Category, Double> map = StateManager.getStateManager().getCategoriesStatus();
            ui.printCategoryStatus(map);
        }
    }

    /**
     * Prints list of transactions
     * @param listArray list of transactions to print
     * @param headerMessage message to print for the header
     */
    private void printList(ArrayList<ArrayList<String>> listArray, String headerMessage) {
        if (headerMessage.equals(IN)) {
            ui.listTransactions(listArray, IN_HEADERS, headerMessage);
        } else if (headerMessage.equals(OUT)) {
            ui.listTransactions(listArray, OUT_HEADERS, headerMessage);
        }

    }

    /**
     * Retrieves list of income transactions
     * @throws DukeException when list of income transactions is empty
     */
    private void listIncome() throws DukeException {
        String filterGoal = null;
        if (getArgs().containsKey(GOAL)) {
            filterGoal = getArg(GOAL).toLowerCase();
        }
        ArrayList<Income> incomeArray = StateManager.getStateManager().getAllIncomes();
        ArrayList<ArrayList<String>> printIncomes = new ArrayList<>();
        if (incomeArray == null || incomeArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }

        if (getArgs().containsKey(WEEK)) {
            incomeArray = filterIncome(incomeArray, false);
        } else if (getArgs().containsKey(MONTH)) {
            incomeArray = filterIncome(incomeArray, true);
        }

        int index = 1;
        for (Income i : incomeArray) {
            String goal = i.getGoal().getDescription();
            if (filterGoal == null || filterGoal.equalsIgnoreCase(goal)) {
                ArrayList<String> transaction = formatTransaction(i.getTransaction(), index, goal);
                printIncomes.add(transaction);
                index++;
            }
        }
        printList(printIncomes, IN);

    }

    /**
     * Prints list of expenses
     * @throws DukeException when expense transaction list is empty
     */
    private void listExpenses() throws DukeException {
        String filterCategory = null;
        if (getArgs().containsKey(CATEGORY)) {
            filterCategory = getArg(CATEGORY).toLowerCase();
        }
        ArrayList<Expense> expenseArray = StateManager.getStateManager().getAllExpenses();
        ArrayList<ArrayList<String>> printExpenses = new ArrayList<>();
        if (expenseArray == null || expenseArray.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }

        if (getArgs().containsKey(WEEK)) {
            expenseArray = filterExpense(expenseArray, false);
        } else if (getArgs().containsKey(MONTH)) {
            expenseArray = filterExpense(expenseArray, true);
        }

        int index = 1;
        for (Expense i : expenseArray) {
            String category = i.getCategory().getName();
            if (filterCategory == null || filterCategory.equalsIgnoreCase(category)) {
                ArrayList<String> transaction = formatTransaction(i.getTransaction(), index, category);
                printExpenses.add(transaction);
                index++;
            }
        }
        printList(printExpenses, OUT);
    }

    /**
     * Formats transactions into the proper format to print
     * @param transaction transaction to format
     * @param index index of transaction in the list
     * @param typeName goal/category of the transaction
     * @return The formatted transaction to print
     */
    private ArrayList<String> formatTransaction(Transaction transaction, int index, String typeName) {
        ArrayList<String> transactionStrings = new ArrayList<>();
        transactionStrings.add(String.valueOf(index));
        transactionStrings.add(transaction.getDescription());
        transactionStrings.add(transaction.getDate().toString());
        transactionStrings.add(String.valueOf(ui.formatAmount(transaction.getAmount())));
        transactionStrings.add(typeName);
        transactionStrings.add(transaction.getRecurrence().toString());
        return transactionStrings;
    }

    /**
     * Returns filtered arraylist of income transactions.
     * Filters the income transactions based on the filter indicated.
     *
     * @param transactionsArrayList arraylist of income transaction.
     * @param filterByMonth         boolean to indicate if filter by month, else filter by week.
     * @return ArrayList of income transaction.
     */
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

    /**
     * Returns filtered arraylist of expense transactions.
     * Filters the expense transactions based on the filter indicated.
     *
     * @param transactionsArrayList arraylist of expense transaction.
     * @param filterByMonth         boolean to indicate if filter by month, else filter by week.
     * @return ArrayList of expense transaction.
     */
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

    /**
     * Checks if the transaction date is in the current week
     * @param transactionDate date of the transaction
     * @return true if transaction date is within the current week, else false
     */
    private boolean isThisWeek(LocalDate transactionDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        if (transactionDate.isBefore(startOfWeek) || transactionDate.isAfter(endOfWeek)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the transaction date is in the current month
     * @param transactionDate date of the transaction
     * @return true if transaction date is within the current month, else false
     */
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
