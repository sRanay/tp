package seedu.duke.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.IntStream;

public class StateManager {
    private static StateManager stateManager = null;
    private final ArrayList<Goal> goals = new ArrayList<>();
    private final Goal uncategorisedGoal = new Goal("Uncategorised", 0);
    private final ArrayList<Category> categories = new ArrayList<>();
    private final Category uncategorisedCategory = new Category("Uncategorised");
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

    public static void clearStateManager() {
        stateManager = new StateManager();
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

    public Goal getUncategorisedGoal() {
        return uncategorisedGoal;
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

    public Category getUncategorisedCategory() {
        return uncategorisedCategory;
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

    public ArrayList<Income> getAllIncomes() {
        return incomes;
    }

    public ArrayList<Expense> getAllExpenses() {
        return expenses;
    }

    public ArrayList<Category> getAllCategories() {
        return categories;
    }

    public ArrayList<Goal> getAllGoals() {
        return goals;
    }

    public int getIncomesSize() {
        return incomes.size();
    }

    public int getExpensesSize() {
        return expenses.size();
    }

    public int getCategoryIndex(String categoryToCheck) {
        return IntStream.range(0, categories.size())
                .filter(i -> categories.get(i).getName().equalsIgnoreCase(categoryToCheck))
                .findFirst()
                .orElse(-1);
    }

    public int getGoalIndex(String goalToCheck) {
        return IntStream.range(0, goals.size())
                .filter(i -> goals.get(i).getDescription().equalsIgnoreCase(goalToCheck))
                .findFirst()
                .orElse(-1);
    }

    public ArrayList<Income> sortedIncomes() {
        Comparator<Income> dateComparator = Comparator.comparing((Income i) -> i.getTransaction().getDate(),
                Comparator.reverseOrder());
        Comparator<Income> nameComparator = Comparator.comparing((Income i) -> i.getTransaction().getDescription());
        ArrayList<Income> sortIncomes = incomes;
        sortIncomes.sort(dateComparator.thenComparing(nameComparator));
        return sortIncomes;
    }

    public ArrayList<Expense> sortedExpenses() {
        Comparator<Expense> dateComparator = Comparator.comparing((Expense e) -> e.getTransaction().getDate(),
                Comparator.reverseOrder());
        Comparator<Expense> nameComparator = Comparator.comparing((Expense e) -> e.getTransaction().getDescription());
        ArrayList<Expense> sortExpenses = expenses;
        sortExpenses.sort(dateComparator.thenComparing(nameComparator));
        return sortExpenses;
    }

    public HashMap<Goal, Double> getGoalsStatus() {
        HashMap<Goal, Double> map = new HashMap<>();
        for (Income i : incomes) {
            Goal key = i.getGoal();
            map.put(key, map.getOrDefault(key, 0.0) + i.getTransaction().getAmount());
        }
        return map;
    }

    public HashMap<Category, Double> getCategoriesStatus() {
        HashMap<Category, Double> map = new HashMap<>();
        for (Expense e: expenses) {
            Category key = e.getCategory();
            map.put(key, map.getOrDefault(key, 0.0) + e.getTransaction().getAmount());
        }
        return map;
    }

}
