package seedu.duke.classes;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRecurrenceTest {
    final Goal goal = new Goal("Test goal", 10.00);
    final Category category = new Category("Test Category");

    Income generatePastIncome(int expectedRecurrenceToGenerate) {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(expectedRecurrenceToGenerate * 7L);
        Transaction transaction = new Transaction("Test Past", 10.00, transactionDate);
        transaction.setRecurrence(TransactionRecurrence.WEEKLY);
        return new Income(transaction, goal);
    }

    Income generateFutureIncome() {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(6);
        Transaction transaction = new Transaction("Test Future", 10.00, transactionDate);
        transaction.setRecurrence(TransactionRecurrence.WEEKLY);
        return new Income(transaction, goal);
    }

    void generateRecurringIncomeEntries(int firstAmt, int secondAmt) {
        ArrayList<Income> existingIncomeEntries = new ArrayList<>();
        existingIncomeEntries.add(generatePastIncome(firstAmt));
        existingIncomeEntries.add(generateFutureIncome());
        existingIncomeEntries.add(generatePastIncome(secondAmt));
        ArrayList<Income> newIncomes = TransactionRecurrence.generateRecurrentIncomes(existingIncomeEntries);
        assertEquals(newIncomes.size(), firstAmt + secondAmt);

        ArrayList<Income> emptyIncomes = TransactionRecurrence.generateRecurrentIncomes(existingIncomeEntries);
        emptyIncomes.addAll(TransactionRecurrence.generateRecurrentIncomes(newIncomes));
        assertEquals(emptyIncomes.size(), 0);
    }

    @Test
    void generateSingleRecurrentIncomeEntry() {
        generateRecurringIncomeEntries(1, 1);
    }

    @Test
    void generateMultipleRecurrentIncomeEntries() {
        generateRecurringIncomeEntries(5, 2);
    }

    Expense generatePastExpense(int expectedRecurrenceToGenerate) {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(expectedRecurrenceToGenerate * 7L);
        Transaction transaction = new Transaction("Test Past", 10.00, transactionDate);
        transaction.setRecurrence(TransactionRecurrence.WEEKLY);
        return new Expense(transaction, category);
    }

    Expense generateFutureExpense() {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(6);
        Transaction transaction = new Transaction("Test Future", 10.00, transactionDate);
        transaction.setRecurrence(TransactionRecurrence.WEEKLY);
        return new Expense(transaction, category);
    }

    void generateRecurringExpenseEntries(int firstAmt, int secondAmt) {
        ArrayList<Expense> existingExpenseEntries = new ArrayList<>();
        existingExpenseEntries.add(generatePastExpense(firstAmt));
        existingExpenseEntries.add(generateFutureExpense());
        existingExpenseEntries.add(generatePastExpense(secondAmt));
        ArrayList<Expense> newExpenses = TransactionRecurrence.generateRecurrentExpenses(existingExpenseEntries);
        assertEquals(newExpenses.size(), firstAmt + secondAmt);

        ArrayList<Expense> emptyExpenses = TransactionRecurrence.generateRecurrentExpenses(existingExpenseEntries);
        emptyExpenses.addAll(TransactionRecurrence.generateRecurrentExpenses(newExpenses));
        assertEquals(emptyExpenses.size(), 0);
    }

    @Test
    void generateSingleRecurrentExpenseEntry() {
        generateRecurringExpenseEntries(1, 1);
    }

    @Test
    void generateMultipleRecurrentExpenseEntries() {
        generateRecurringExpenseEntries(5, 2);
    }
}
