package seedu.duke.classes;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRecurrenceTest {
    final Goal goal = new Goal("Test goal", 10.00);
    final Category category = new Category("Test Category");

    Income generatePastIncome() {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(8);
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

    @Test
    void generateRecurringIncomeEntries() {
        ArrayList<Income> existingIncomeEntries = new ArrayList<>();
        existingIncomeEntries.add(generatePastIncome());
        existingIncomeEntries.add(generateFutureIncome());
        existingIncomeEntries.add(generatePastIncome());
        ArrayList<Income> newIncomes = TransactionRecurrence.generateRecurrentIncomes(existingIncomeEntries);
        assertEquals(newIncomes.size(), 2);

        ArrayList<Income> emptyIncomes = TransactionRecurrence.generateRecurrentIncomes(existingIncomeEntries);
        emptyIncomes.addAll(TransactionRecurrence.generateRecurrentIncomes(newIncomes));
        assertEquals(emptyIncomes.size(), 0);
    }

    Expense generatePastExpense() {
        LocalDate timeNow = LocalDate.now();
        LocalDate transactionDate = timeNow.minusDays(8);
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

    @Test
    void generateRecurringExpenseEntries() {
        ArrayList<Expense> existingExpenseEntries = new ArrayList<>();
        existingExpenseEntries.add(generatePastExpense());
        existingExpenseEntries.add(generateFutureExpense());
        existingExpenseEntries.add(generatePastExpense());
        ArrayList<Expense> newExpenses = TransactionRecurrence.generateRecurrentExpenses(existingExpenseEntries);
        assertEquals(newExpenses.size(), 2);

        ArrayList<Expense> emptyExpenses = TransactionRecurrence.generateRecurrentExpenses(existingExpenseEntries);
        emptyExpenses.addAll(TransactionRecurrence.generateRecurrentExpenses(newExpenses));
        assertEquals(emptyExpenses.size(), 0);
    }
}
