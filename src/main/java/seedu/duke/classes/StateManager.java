package seedu.duke.classes;

import java.util.ArrayList;

public class StateManager {
    private static StateManager stateManager = null;
    private final ArrayList<Goal> goals = new ArrayList<>();
    private final ArrayList<Category> categories = new ArrayList<>();
    private final ArrayList<Income> incomes = new ArrayList<>();
    private final ArrayList<Expense> expenses = new ArrayList<>();

    private StateManager() {
    }

    public static StateManager getStateManager() {
        if (stateManager == null) {
            stateManager = new StateManager();
        }
        return stateManager;
    }

    public void addGoal(Goal goal) {
        assert goal != null;
        goals.add(goal);
    }

    public Goal getGoal(int idx) {
        if (idx < 0 || idx >= goals.size()) {
            return null;
        }
        return goals.get(idx);
    }

    public boolean removeGoal(Goal goal) {
        assert goal != null;
        return goals.remove(goal);
    }

    public boolean removeGoal(int idx) {
        Goal goal = getGoal(idx);
        if (goal == null) {
            return false;
        }

        return removeGoal(goal);
    }

    public void addCategory(Category category) {
        assert category != null;
        categories.add(category);
    }

    public Category getCategory(int idx) {
        if (idx < 0 || idx >= categories.size()) {
            return null;
        }
        return categories.get(idx);
    }

    public boolean removeCategory(Category category) {
        assert category != null;
        return categories.remove(category);
    }

    public boolean removeCategory(int idx) {
        Category category = getCategory(idx);
        if (category == null) {
            return false;
        }

        return removeCategory(category);
    }

    public void addIncome(Income income) {
        assert income != null;
        incomes.add(income);
    }

    public Income getIncome(int idx) {
        if (idx < 0 || idx >= incomes.size()) {
            return null;
        }
        return incomes.get(idx);
    }

    public boolean removeIncome(Income income) {
        assert income != null;
        return incomes.remove(income);
    }

    public boolean removeIncome(int idx) {
        Income income = getIncome(idx);
        if (income == null) {
            return false;
        }

        return removeIncome(income);
    }

    public void addExpense(Expense expense) {
        assert expense != null;
        expenses.add(expense);
    }

    public Expense getExpense(int idx) {
        if (idx < 0 || idx >= expenses.size()) {
            return null;
        }
        return expenses.get(idx);
    }

    public boolean removeExpense(Expense expense) {
        assert expense != null;
        return expenses.remove(expense);
    }

    public boolean removeExpense(int idx) {
        Expense expense = getExpense(idx);
        if (expense == null) {
            return false;
        }

        return removeExpense(expense);
    }

    public ArrayList<Income> getAllIncomes(){
        return incomes;
    }
    public ArrayList<Expense> getAllExpenses(){
        return expenses;
    }
}
