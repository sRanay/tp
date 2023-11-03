package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
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
    private static final String[] HEADERS = {"Description", "Date", "Amount", "Goal", "Category"};

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
        row[0] = description;
        row[1] = date;
        row[2] = amount;
        return row;
    }

    public void exportIncomeData() {
        for (Income i : this.incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String[] row = new String[5];
            row[3] = i.getGoal().getDescription();
            row[4] = null;
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }

    public void exportExpenseData() {
        for (Expense e : this.expenseArray) {
            Transaction currentTransaction = e.getTransaction();
            String[] row = new String[5];
            row[3] = null;
            row[4] = e.getCategory().getName();
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
