package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Transaction;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.csv.CsvWriter;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

import static seedu.duke.storage.Storage.exportStorageFileName;

public class ExportCommand extends Command {
    enum TransactionType {
        IN, OUT, ALL, ERROR
    }

    private static final String SUCESSFUL_MSG = "Transaction Data extracted";
    private static final String TYPE_ARG = "type";
    private static final String WRONG_TYPE_MSG = "Wrong type entered. Please enter /type in, /type out or blank";
    private static final String[] HEADERS = {"Type", "Description", "Date", "Amount", "Goal", "Category", "Recurrence"};
    private static final int TYPE = 0;
    private static final int DESCRIPTION = 1;
    private static final int DATE = 2;
    private static final int AMOUNT = 3;
    private static final int GOAL = 4;
    private static final int CATEGORY = 5;
    private static final int RECURRENCE = 6;
    private static final String EMPTY_DATA = null;
    private static final int DATA_LENGTH = 7;
    private static final String INCOME_STRING = "Income";
    private static final String EXPENSE_STRING = "Expense";
    private ArrayList<Income> incomeArray;
    private ArrayList<Expense> expenseArray;
    private CsvWriter csvFile;
    private Ui ui;

    public ExportCommand(String description, HashMap<String, String> args) throws DukeException {
        super(description, args);
        this.incomeArray = StateManager.getStateManager().getAllIncomes();
        this.expenseArray = StateManager.getStateManager().getAllExpenses();
        this.csvFile = new CsvWriter(exportStorageFileName);
    }

    /**
     * Writes the header of the export CSV File.
     */
    public void writeHeader() {
        csvFile.write(HEADERS);
    }

    /**
     * Converts the transaction object into an Array to be able to store into the CSV File.
     *
     * @param transaction Transaction object to be converted.
     * @param row Array where the data is stored in.
     * @return Array containing the data from the Transaction object.
     */
    public String[] extractTransactionData(Transaction transaction, String[] row) {
        String description = transaction.getDescription();
        String date = transaction.getDate().toString();
        String amount = ui.formatAmount(transaction.getAmount());
        row[DESCRIPTION] = description;
        row[DATE] = date;
        row[AMOUNT] = amount;
        row[RECURRENCE] = transaction.getRecurrence().toString();
        return row;
    }

    /**
     * Exports all Income Transactions and writes to the CSV File.
     */
    public void exportIncomeData() {
        for (Income i : this.incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String[] row = new String[DATA_LENGTH];
            row[TYPE] = INCOME_STRING;
            row[GOAL] = i.getGoal().getDescription();
            row[CATEGORY] = EMPTY_DATA;
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }

    /**
     * Exports all Expense Transactions and writes to the CSV File.
     */
    public void exportExpenseData() {
        for (Expense e : this.expenseArray) {
            Transaction currentTransaction = e.getTransaction();
            String[] row = new String[DATA_LENGTH];
            row[TYPE] = EXPENSE_STRING;
            row[GOAL] = EMPTY_DATA;
            row[CATEGORY] = e.getCategory().getName();
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }

    /**
     * Check which transaction to be exported.
     *
     * @return returns the correct transaction type to be exported.
     */
    public TransactionType checkType() {
        String type = getArg(TYPE_ARG);
        if (type == null) {
            return TransactionType.ALL;
        }
        if (type.equalsIgnoreCase("in")) {
            return TransactionType.IN;
        }
        if (type.equalsIgnoreCase("out")) {
            return TransactionType.OUT;
        }
        return TransactionType.ERROR;
    }

    /**
     * Export the right data to the CSV File
     *
     * @param type The type of transaction to be export.
     */
    void exportData(TransactionType type) {
        switch (type) {
        case IN:
            exportIncomeData();
            break;
        case OUT:
            exportExpenseData();
            break;
        default:
            exportIncomeData();
            exportExpenseData();
        }
    }

    /**
     * Executes the command.
     *
     * @param ui Ui class that is used to print in table format.
     * @throws DukeException If the file cannot be created during the exporting process.
     */
    @Override
    public void execute(Ui ui) throws DukeException {
        this.ui = ui;
        TransactionType transactionType = checkType();
        if (transactionType.equals(TransactionType.ERROR)) {
            ui.print(WRONG_TYPE_MSG);
            return;
        }
        writeHeader();
        exportData(transactionType);
        ui.print(SUCESSFUL_MSG);
        csvFile.close();
    }
}
