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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Storage {
    private static final String GOAL_STORAGE_FILENAME = "goal-store.csv";
    private static final String CATEGORY_STORAGE_FILENAME = "category-store.csv";
    private static final String INCOME_STORAGE_FILENAME = "income-store.csv";
    private static final String EXPENSE_STORAGE_FILENAME = "expense-store.csv";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String FAILED_CONVERT_TO_NON_NEG_DOUBLE = "Cannot convert amount into Double type in ";
    private static final String FAILED_CONVERT_TO_LOCALDATE = "Cannot convert date into LocalDate type in ";
    private static final String FAILED_CONVERT_BOOLEAN = "Cannot convert string into boolean type in ";
    private static final String[] GOAL_HEADER = {"Description", "Amount"};
    private static final String[] CATEGORY_HEADER = {"Name"};
    private static final String[] INCOME_HEADER = {"Description", "Amount", "Date", "Goal", "Recurrence", "Has Next Recurrence"};
    private static final String[] EXPENSE_HEADER = {"Description", "Amount", "Date", "Category", "Recurrence", "Has Next Reccurence"};



    public Storage() {
    }

    public boolean validRow(String[] row) {
        for(String column : row) {
            if (column.isBlank() || column.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public LocalDate validDate(String dateStr, String fileName) throws DukeException {
        try {
            LocalDate date = LocalDate.parse(dateStr, FORMATTER);
            return date;
        } catch (DateTimeParseException e) {
            throw new DukeException(FAILED_CONVERT_TO_LOCALDATE + fileName);
        }
    }

    public boolean validBoolean(String booleanStr) {
        if(booleanStr.toLowerCase().equals("true") || booleanStr.toLowerCase().equals("false")) {
            return true;
        }
        return false;
    }

    public Goal convertToGoal(String name) {
        int index = StateManager.getStateManager().getGoalIndex(name);
        Goal goal = StateManager.getStateManager().getGoal(index);
        return goal;
    }

    public Category convertToCategory(String name) {
        int index = StateManager.getStateManager().getCategoryIndex(name);
        Category category = StateManager.getStateManager().getCategory(index);
        return category;
    }

    public Transaction prepareTransaction(String description, Double amount, LocalDate date, String recurrence, String hasRecurrence) {
        Transaction transaction = new Transaction(description, amount, date);
        transaction.setHasGeneratedNextRecurrence(Boolean.parseBoolean(hasRecurrence));
        if (recurrence != null) {
            TransactionRecurrence transactionRecurrence = TransactionRecurrence.getRecurrence(recurrence);
            transaction.setRecurrence(transactionRecurrence);
        }
        return transaction;
    }
    public void loadGoal() throws DukeException {
        CsvReader goalCsvFile =  new CsvReader(GOAL_STORAGE_FILENAME);
        String[] row;
        double amount;
        while ((row = goalCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[0];
                if (Parser.parseNonNegativeDouble(row[1]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + GOAL_STORAGE_FILENAME);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[1]);
                Goal goal = new Goal(description, amount);
                StateManager.getStateManager().addGoal(goal);
            }
        }
        goalCsvFile.close();
    }
    public void loadCategory() throws DukeException {
        CsvReader categoryCsvFile = new CsvReader(CATEGORY_STORAGE_FILENAME);
        String[] row;
        while ((row = categoryCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[0];
                Category category = new Category(description);
                StateManager.getStateManager().addCategory(category);
            }
        }
        categoryCsvFile.close();
    }

    public void loadIncome() throws DukeException {
        CsvReader incomeCsvFile = new CsvReader(INCOME_STORAGE_FILENAME);
        String[] row;
        double amount;
        LocalDate date;
        Transaction transaction;
        while ((row = incomeCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[0];
                Goal goal = convertToGoal(row[3]);
                String recurrence = row[4];
                String hasRecurrence = row[5];
                if (!(validBoolean(hasRecurrence))) {
                    System.out.println(FAILED_CONVERT_BOOLEAN + INCOME_STORAGE_FILENAME);
                    continue;
                }
                if (Parser.parseNonNegativeDouble(row[1]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + INCOME_STORAGE_FILENAME);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[1]);
                try {
                    date = validDate(row[2], INCOME_STORAGE_FILENAME);
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

    public void loadExpense() throws DukeException {
        CsvReader expenseCsvFile = new CsvReader(EXPENSE_STORAGE_FILENAME);
        String[] row;
        double amount;
        LocalDate date;
        Transaction transaction;
        while ((row = expenseCsvFile.readLine()) != null) {
            if (validRow(row)) {
                String description = row[0];
                Category category = convertToCategory(row[3]);
                String recurrence = row[4];
                String hasRecurrence = row[5];
                if (!(validBoolean(hasRecurrence))) {
                    System.out.println(FAILED_CONVERT_BOOLEAN + EXPENSE_STORAGE_FILENAME);
                    continue;
                }
                if (Parser.parseNonNegativeDouble(row[1]) == null) {
                    System.out.println(FAILED_CONVERT_TO_NON_NEG_DOUBLE + EXPENSE_STORAGE_FILENAME);
                    continue;
                }
                amount = Parser.parseNonNegativeDouble(row[1]);
                try {
                    date = validDate(row[2], EXPENSE_STORAGE_FILENAME);
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
        loadGoal();
        loadCategory();
        loadIncome();
        loadExpense();
    }

    public void saveGoal() throws DukeException {
        CsvWriter goalStorageFile = new CsvWriter(GOAL_STORAGE_FILENAME);
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

    public void saveCategory() throws DukeException {
        CsvWriter categoryStorageFile = new CsvWriter(CATEGORY_STORAGE_FILENAME);
        ArrayList<Category> categoryList = StateManager.getStateManager().getAllCategories();
        categoryStorageFile.write(CATEGORY_HEADER);
        for (Category category : categoryList) {
            String name = category.getName();
            String[] row = {name};
            categoryStorageFile.write(row);
        }
        categoryStorageFile.close();
    }

    public void saveIncome() throws DukeException {
        CsvWriter incomeStorageFile = new CsvWriter(INCOME_STORAGE_FILENAME);
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

    public void saveExpense() throws DukeException{
        CsvWriter expenseStorageFile = new CsvWriter(EXPENSE_STORAGE_FILENAME);
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
        saveGoal();
        saveCategory();
        saveIncome();
        saveExpense();
    }

}
