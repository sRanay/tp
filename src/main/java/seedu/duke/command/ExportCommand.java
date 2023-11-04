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
    private static final String[] HEADERS = {"Description", "Date", "Amount", "Goal", "Category", "Recurrence"};

    private static final int DESCRIPTION = 0;
    private static final int AMOUNT = 1;
    private static final int DATE = 2;
    private static final int GOAL = 3;
    private static final int CATEGORY = 4;
    private static final int RECURRENCE = 5;
    private static final String EMPTY_DATA = "empty";
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

    public void writeHeader() {
        csvFile.write(HEADERS);
    }

    public String[] extractTransactionData(Transaction transaction, String[] row) {
        String description = transaction.getDescription();
        String date = transaction.getDate().toString();
        String amount = String.valueOf(ui.formatAmount(transaction.getAmount()));
        row[DESCRIPTION] = description;
        row[DATE] = date;
        row[AMOUNT] = amount;
        return row;
    }

    public void exportIncomeData() {
        for (Income i : this.incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String[] row = new String[6];
            row[GOAL] = i.getGoal().getDescription();
            row[CATEGORY] = EMPTY_DATA;
            row[RECURRENCE] = currentTransaction.getRecurrence().toString();
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }

    public void exportExpenseData() {
        for (Expense e : this.expenseArray) {
            Transaction currentTransaction = e.getTransaction();
            String[] row = new String[6];
            row[GOAL] = EMPTY_DATA;
            row[CATEGORY] = e.getCategory().getName();
            row[RECURRENCE] = currentTransaction.getRecurrence().toString();
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }

    public TransactionType checkType() {
        String type = getArg(TYPE_ARG);
        if (type == null) {
            return TransactionType.ALL;
        }
        if (type.equals("in")) {
            return TransactionType.IN;
        }
        if (type.equals("out")) {
            return TransactionType.OUT;
        }
        return TransactionType.ERROR;
    }

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
