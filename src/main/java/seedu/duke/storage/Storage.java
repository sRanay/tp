package seedu.duke.storage;

import seedu.duke.classes.Income;
import seedu.duke.classes.Expense;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Goal;
import seedu.duke.classes.Category;
import seedu.duke.classes.Transaction;
import seedu.duke.classes.TransactionRecurrence;
import seedu.duke.csv.CsvWriter;
import seedu.duke.exception.DukeException;
import seedu.duke.csv.CsvReader;
import seedu.duke.parser.Parser;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Storage {

    public static String exportStorageFileName;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String FAILED_CONVERT_TO_NON_NEG_DOUBLE = "Cannot convert amount into Double type in ";
    private static final String FAILED_CONVERT_TO_LOCALDATE = "Cannot convert date into LocalDate type in ";
    private static final String FAILED_CONVERT_BOOLEAN = "Cannot convert string into boolean type in ";
    private static final String STORAGE_DIR = "./data";
    private static final String GOAL_STORAGE_FILE_NAME = STORAGE_DIR + "/goal-store.csv";
    private static final String CATEGORY_STORAGE_FILE_NAME = STORAGE_DIR + "/category-store.csv";
    private static final String INCOME_STORAGE_FILE_NAME = STORAGE_DIR + "/income-store.csv";
    private static final String EXPENSE_STORAGE_FILE_NAME = STORAGE_DIR + "/expense-store.csv";
    private static final String EXPORT_STORAGE_FILE_NAME = "./Transactions.csv";
    private static final String[] GOAL_HEADER = {"Description", "Amount"};
    private static final String[] CATEGORY_HEADER = {"Name"};
    private static final String[] INCOME_HEADER = {"Description", "Amount", "Date", "Goal",
                                                   "Recurrence", "Has Next Recurrence"};
    private static final String[] EXPENSE_HEADER = {"Description", "Amount", "Date", "Category",
                                                    "Recurrence", "Has Next Reccurence"};
    private static final int DESCRIPTION = 0;
    private static final int AMOUNT = 1;
    private static final int DATE = 2;
    private static final int GOAL = 3;
    private static final int CATEGORY = 3;
    private static final int RECURRENCE = 4;
    private static final int HAS_NEXT_RECURRENCE = 5;

    private static String goalStorageFileName;
    private static String categoryStorageFileName;
    private static String incomeStorageFileName;
    private static String expenseStorageFileName;

    public Storage() {
        goalStorageFileName = GOAL_STORAGE_FILE_NAME;
        categoryStorageFileName = CATEGORY_STORAGE_FILE_NAME;
        incomeStorageFileName = INCOME_STORAGE_FILE_NAME;
        expenseStorageFileName = EXPENSE_STORAGE_FILE_NAME;
        exportStorageFileName = EXPORT_STORAGE_FILE_NAME;
    }

    public Storage(String goalFileName, String categoryFileName, String incomeFileName, String expenseFileName,
                   String exportFileName) {
        goalStorageFileName = goalFileName;
        categoryStorageFileName = categoryFileName;
        incomeStorageFileName = incomeFileName;
        expenseStorageFileName = expenseFileName;
        exportStorageFileName = exportFileName;
    }

    public boolean checkDirExist() {
        File directory = new File(STORAGE_DIR);
        if (!directory.exists()) {
            directory.mkdir();
            return false;
        }
        return true;
    }

    /**
     * Check if the columns in each row is it blank or empty.
     *
     * @param row Array of String from a row in the CSV File.
     * @return true if there is no empty or blank column, false if there is empty or blank column.
     */
    public boolean validRow(String[] row) {
        for (String column : row) {
            if (column.isBlank() || column.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether dateStr can be parsed into a LocalDate type and returns if possible.
     *
     * @param dateStr  String to be parsed into a LocalDate type.
     * @param fileName Current File that is using this function.
     * @return date after parsing successful.
     * @throws DukeException if unable to parse into a LocalDate type.
     */
    public LocalDate validDate(String dateStr, String fileName) throws DukeException {
        try {
            LocalDate date = LocalDate.parse(dateStr, FORMATTER);
            return date;
        } catch (DateTimeParseException e) {
            throw new DukeException(FAILED_CONVERT_TO_LOCALDATE + fileName);
        }
    }

    /**
     * Check if the string can be converted into boolean.
     *
     * @param booleanStr String to be converted into boolean.
     * @return true if can be converted, else return false.
     */
    public boolean validBoolean(String booleanStr) {
        if (booleanStr.toLowerCase().equals("true") || booleanStr.toLowerCase().equals("false")) {
            return true;
        }
        return false;
    }

    /**
     * Get the goal based on the name provided.
     *
     * @param name goal name.
     * @return Goal object that has that name.
     */
    public Goal convertToGoal(String name) {
        int index = StateManager.getStateManager().getGoalIndex(name);
        Goal goal = StateManager.getStateManager().getGoal(index);
        if (goal == null) {
            goal = StateManager.getStateManager().getUncategorisedGoal();
        }
        return goal;
    }

    /**
     * Get the category based on the name provided.
     *
     * @param name category name.
     * @return Category object that has that name.
     */
    public Category convertToCategory(String name) {
        int index = StateManager.getStateManager().getCategoryIndex(name);
        Category category = StateManager.getStateManager().getCategory(index);
        if (category == null) {
            category = StateManager.getStateManager().getUncategorisedCategory();
        }
        return category;
    }

    /**
     * Convert all the data required into a Transaction Object.
     *
     * @param description   Description of the Transaction.
     * @param amount        Amount that is stated in the Transaction.
     * @param date          Date of the Transaction that it occurred.
     * @param recurrence    Recurrence type.
     * @param hasRecurrence String of boolean if it has recurring transaction.
     * @return Transaction object created.
     */
    public Transaction prepareTransaction(String description, Double amount, LocalDate date,
                                          String recurrence, String hasRecurrence) {
        Transaction transaction = new Transaction(description, amount, date);
        transaction.setHasGeneratedNextRecurrence(Boolean.parseBoolean(hasRecurrence));
        if (recurrence != null) {
            TransactionRecurrence transactionRecurrence = TransactionRecurrence.getRecurrence(recurrence);
            transaction.setRecurrence(transactionRecurrence);
        }
        return transaction;
    }

    /**
     * Loads all Goals objects from the CSV File.
     *
     * @throws DukeException if GOAL_STORAGE_FILENAME cannot be opened.
     */
    public void loadGoal() throws DukeException {
        CsvReader goalCsvFile = new CsvReader(goalStorageFileName);
        String[] row;
        double amount;
        while ((row = goalCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[DESCRIPTION];
                if (Parser.parseNonNegativeDouble(row[AMOUNT]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + goalStorageFileName);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[AMOUNT]);
                Goal goal = new Goal(description, amount);
                StateManager.getStateManager().addGoal(goal);
            }
        }
        goalCsvFile.close();
    }

    /**
     * Loads all the Category objects from the CSV File.
     *
     * @throws DukeException if CATEGORY_STORAGE_FILENAME cannot be opened.
     */
    public void loadCategory() throws DukeException {
        CsvReader categoryCsvFile = new CsvReader(categoryStorageFileName);
        String[] row;
        while ((row = categoryCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[DESCRIPTION];
                Category category = new Category(description);
                StateManager.getStateManager().addCategory(category);
            }
        }
        categoryCsvFile.close();
    }

    /**
     * Loads all the Income objects from the CSV File.
     *
     * @throws DukeException if INCOME_STORAGE_FILENAME cannot be opened.
     */
    public void loadIncome() throws DukeException {
        CsvReader incomeCsvFile = new CsvReader(incomeStorageFileName);
        String[] row;
        double amount;
        LocalDate date;
        Transaction transaction;
        while ((row = incomeCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[DESCRIPTION];
                Goal goal = convertToGoal(row[GOAL]);
                String recurrence = row[RECURRENCE];
                String hasRecurrence = row[HAS_NEXT_RECURRENCE];
                if (!(validBoolean(hasRecurrence))) {
                    System.out.println(FAILED_CONVERT_BOOLEAN + incomeStorageFileName);
                    continue;
                }
                if (Parser.parseNonNegativeDouble(row[AMOUNT]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + incomeStorageFileName);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[AMOUNT]);
                try {
                    date = validDate(row[DATE], incomeStorageFileName);
                    transaction = prepareTransaction(description, amount, date, recurrence, hasRecurrence);
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                Income income = new Income(transaction, goal);
                StateManager.getStateManager().addIncome(income);
            }
        }
        incomeCsvFile.close();
    }

    /**
     * Loads all Expense Objects from the CSV File.
     *
     * @throws DukeException if EXPENSE_STORAGE_FILENAME cannot be opened.
     */
    public void loadExpense() throws DukeException {
        CsvReader expenseCsvFile = new CsvReader(expenseStorageFileName);
        String[] row;
        double amount;
        LocalDate date;
        Transaction transaction;
        while ((row = expenseCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[DESCRIPTION];
                Category category = convertToCategory(row[CATEGORY]);
                String recurrence = row[RECURRENCE];
                String hasRecurrence = row[HAS_NEXT_RECURRENCE];
                if (!(validBoolean(hasRecurrence))) {
                    System.out.println(FAILED_CONVERT_BOOLEAN + expenseStorageFileName);
                    continue;
                }
                if (Parser.parseNonNegativeDouble(row[AMOUNT]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + expenseStorageFileName);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[AMOUNT]);
                try {
                    date = validDate(row[DATE], expenseStorageFileName);
                    transaction = prepareTransaction(description, amount, date, recurrence, hasRecurrence);
                } catch (DukeException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                Expense expense = new Expense(transaction, category);
                StateManager.getStateManager().addExpense(expense);
            }
        }
        expenseCsvFile.close();
    }

    public void load() throws DukeException {
        if (checkDirExist()) {
            loadGoal();
            loadCategory();
            loadIncome();
            loadExpense();
        }
    }

    /**
     * Save the current state of Goal objects into the CSV File.
     *
     * @throws DukeException if GOAL_STORAGE_FILENAME cannot be opened.
     */
    public void saveGoal() throws DukeException {
        CsvWriter goalStorageFile = new CsvWriter(goalStorageFileName);
        ArrayList<Goal> goalList = StateManager.getStateManager().getAllGoals();
        goalStorageFile.write(GOAL_HEADER);
        for (Goal goal : goalList) {
            String description = goal.getDescription();
            String amount = Double.toString(goal.getAmount());
            String[] row = {description, amount};
            goalStorageFile.write(row);
        }
        goalStorageFile.close();
    }

    /**
     * Save the current state of Category objects into the CSV File.
     *
     * @throws DukeException if CATEGORY_STORAGE_FILENAME cannot be opened.
     */
    public void saveCategory() throws DukeException {
        CsvWriter categoryStorageFile = new CsvWriter(categoryStorageFileName);
        ArrayList<Category> categoryList = StateManager.getStateManager().getAllCategories();
        categoryStorageFile.write(CATEGORY_HEADER);
        for (Category category : categoryList) {
            String name = category.getName();
            String[] row = {name};
            categoryStorageFile.write(row);
        }
        categoryStorageFile.close();
    }

    /**
     * Saves the current state of Income objects into the CSV File.
     *
     * @throws DukeException if INCOME_STORAGE_FILENAME cannot be opened.
     */
    public void saveIncome() throws DukeException {
        CsvWriter incomeStorageFile = new CsvWriter(incomeStorageFileName);
        ArrayList<Income> incomesList = StateManager.getStateManager().getAllIncomes();
        incomeStorageFile.write(INCOME_HEADER);
        for (Income income : incomesList) {
            Transaction transaction = income.getTransaction();
            String description = transaction.getDescription();
            String amount = Double.toString(transaction.getAmount());
            String date = transaction.getDate().format(FORMATTER);
            String goal = income.getGoal().getDescription();
            String recurrence = transaction.getRecurrence().toString();
            String hasNextRecurrence = Boolean.toString(transaction.getHasGeneratedNextRecurrence());
            String[] row = {description, amount, date, goal, recurrence, hasNextRecurrence};
            incomeStorageFile.write(row);
        }
        incomeStorageFile.close();
    }

    /**
     * Saves the current state of Expense objects into the CSV File.
     *
     * @throws DukeException if EXPENSE_STORAGE_FILENAME cannot be opened.
     */
    public void saveExpense() throws DukeException {
        CsvWriter expenseStorageFile = new CsvWriter(expenseStorageFileName);
        ArrayList<Expense> expensesList = StateManager.getStateManager().getAllExpenses();
        expenseStorageFile.write(EXPENSE_HEADER);
        for (Expense expense : expensesList) {
            Transaction transaction = expense.getTransaction();
            String description = transaction.getDescription();
            String amount = Double.toString(transaction.getAmount());
            String date = transaction.getDate().format(FORMATTER);
            String category = expense.getCategory().getName();
            String recurrence = transaction.getRecurrence().toString();
            String hasNextRecurrence = Boolean.toString(transaction.getHasGeneratedNextRecurrence());
            String[] row = {description, amount, date, category, recurrence, hasNextRecurrence};
            expenseStorageFile.write(row);
        }
        expenseStorageFile.close();
    }

    public void save() throws DukeException {
        checkDirExist();
        saveGoal();
        saveCategory();
        saveIncome();
        saveExpense();
    }

}
