package seedu.duke.command;

import seedu.duke.classes.Expense;
import seedu.duke.classes.Income;
import seedu.duke.classes.StateManager;
import seedu.duke.classes.Transaction;
import seedu.duke.csv.Csv;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.Ui;
import java.util.ArrayList;

public class ExportCommand extends Command{
    private Ui ui;

    private ArrayList<Income> incomeArray;
    private ArrayList<Expense> expenseArray;
    private Csv csvFile;
    private static final String EMPTY_LIST = "There is no transaction data to export";
    private static final String SUCESSFUL_MSG = "Transaction Data extracted";
    private static final String[] HEADERS = {"Description", "Date", "Amount", "Goal", "Category"};
    private void writeHeader() {
        assert csvFile != null;
        csvFile.write(HEADERS);
    }

    private String[] extractTransactionData(Transaction transaction, String[] row) {
        String description = transaction.getDescription();
        String date = transaction.getDate().toString();
        String amount = String.valueOf(ui.formatAmount(transaction.getAmount()));
        row[0] = description;
        row[1] = date;
        row[2] = amount;
        return row;
    }

    private void exportIncomeData() {
        for (Income i : this.incomeArray) {
            Transaction currentTransaction = i.getTransaction();
            String[] row = new String[5];
            row[3] = "TBC";
            row[4] = null;
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }
    private void exportExpenseData() {
        for (Expense e : this.expenseArray) {
            Transaction currentTransaction = e.getTransaction();
            String[] row = new String[5];
            row[3] = null;
            row[4] = "TBC";
            this.csvFile.write(extractTransactionData(currentTransaction, row));
        }
    }


    @Override
    public void execute(Ui ui) throws DukeException {
        this.ui = ui;
        this.incomeArray = StateManager.getStateManager().getAllIncomes();
        this.expenseArray = StateManager.getStateManager().getAllExpenses();
        this.csvFile = Csv.getInstance();
        writeHeader();
        exportIncomeData();
        exportExpenseData();
        ui.print(SUCESSFUL_MSG);
        this.csvFile.close();
    }
}
