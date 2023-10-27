package seedu.duke.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public enum TransactionRecurrence {
    NONE, DAILY, WEEKLY, MONTHLY;

    private static final String NONE_STR = "none";
    private static final String DAILY_STR = "daily";
    private static final String WEEKLY_STR = "weekly";
    private static final String MONTHLY_STR = "monthly";

    public static TransactionRecurrence getRecurrence(String recurrence) {
        assert recurrence != null;
        String cleanedRecurrence = recurrence.strip().toLowerCase();
        switch (cleanedRecurrence) {
        case NONE_STR:
            return TransactionRecurrence.NONE;
        case DAILY_STR:
            return TransactionRecurrence.DAILY;
        case WEEKLY_STR:
            return TransactionRecurrence.WEEKLY;
        case MONTHLY_STR:
            return TransactionRecurrence.MONTHLY;
        default:
            return null;
        }
    }

    public static LocalDate getNextRecurrenceDate(TransactionRecurrence recurrence, LocalDate current) {
        switch (recurrence) {
        case DAILY:
            return current.plusDays(1);
        case WEEKLY:
            return current.plusWeeks(1);
        case MONTHLY:
            return current.plusMonths(1);
        default:
            return current;
        }
    }

    public static ArrayList<Income> generateRecurrentIncomes(Income income) {
        ArrayList<Income> recurrentIncomes = new ArrayList<>();
        while (true) {
            Income recurrentIncome = income.generateNextRecurrence();
            if (recurrentIncome == null) {
                break;
            }

            income.getTransaction().setHasGeneratedNextRecurrence(true);
            recurrentIncomes.add(recurrentIncome);
            income = recurrentIncome;
        }
        return recurrentIncomes;
    }

    public static ArrayList<Income> generateRecurrentIncomes(ArrayList<Income> incomes) {
        ArrayList<Income> recurrentIncomes = new ArrayList<>();
        for (Income income : incomes) {
            recurrentIncomes.addAll(generateRecurrentIncomes(income));
        }

        recurrentIncomes.sort((Income a, Income b) -> {
            LocalDate aDate = a.getTransaction().getDate();
            LocalDate bDate = b.getTransaction().getDate();
            return aDate.compareTo(bDate);
        });

        return recurrentIncomes;
    }

    public static ArrayList<Expense> generateRecurrentExpenses(Expense expense) {
        ArrayList<Expense> recurrentExpenses = new ArrayList<>();
        while (true) {
            Expense recurrentExpense = expense.generateNextRecurrence();
            if (recurrentExpense == null) {
                break;
            }

            expense.getTransaction().setHasGeneratedNextRecurrence(true);
            recurrentExpenses.add(recurrentExpense);
            expense = recurrentExpense;
        }
        return recurrentExpenses;
    }

    public static ArrayList<Expense> generateRecurrentExpenses(ArrayList<Expense> expenses) {
        ArrayList<Expense> recurrentExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            recurrentExpenses.addAll(generateRecurrentExpenses(expense));
        }

        recurrentExpenses.sort((Expense a, Expense b) -> {
            LocalDate aDate = a.getTransaction().getDate();
            LocalDate bDate = b.getTransaction().getDate();
            return aDate.compareTo(bDate);
        });

        return recurrentExpenses;
    }

    public static void generateRecurrentTransactions(ArrayList<Income> incomes, ArrayList<Expense> expenses) {
        incomes.addAll(generateRecurrentIncomes(incomes));
        expenses.addAll(generateRecurrentExpenses(expenses));
    }

    @Override
    public String toString() {
        switch (this) {
        case NONE:
            return NONE_STR;
        case DAILY:
            return DAILY_STR;
        case WEEKLY:
            return WEEKLY_STR;
        case MONTHLY:
            return MONTHLY_STR;
        default:
            return null;
        }
    }
}
