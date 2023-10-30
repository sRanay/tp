package seedu.duke.classes;

public class Expense {
    private Transaction transaction;
    private Category category;

    public Expense(Transaction transaction, Category category) {
        this.transaction = transaction;
        this.category = category;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Generate next recurrent entry for expense
     *
     * @return Generated expense if entry should be generated,
     *         otherwise returns {@code null}
     */
    public Expense generateNextRecurrence() {
        Transaction nextTransaction = transaction.generateNextRecurrence();
        if (nextTransaction == null) {
            return null;
        }

        return new Expense(nextTransaction, category);
    }
}
