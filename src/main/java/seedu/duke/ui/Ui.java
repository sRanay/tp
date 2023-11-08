package seedu.duke.ui;

import seedu.duke.classes.Category;
import seedu.duke.classes.TypePrint;
import seedu.duke.classes.Goal;
import seedu.duke.classes.StateManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ui {
    public static final int COLUMN_WIDTH = 10;
    public static final int LIST_COLUMN_WIDTH = 30;
    public static final int TYPE_WIDTH = 20;
    private static final String ELLIPSIS = "...";
    private static final String PROGRAM_NAME = "FinText";
    private static final char FILLER_CHAR = ' ';
    private static final char LIST_SEPARATOR = '=';
    private static final int ID_COLUMN_PADDING = 2;

    private static final int SPACE_BETWEEN_COLS = 3;

    private static final String AMOUNT_FORMAT = "%.2f";
    private static final char LINE_DELIMITER = '\n';
    private static final Integer[] TYPE_COLUMN_WIDTHS = {TYPE_WIDTH, TYPE_WIDTH};
    private static final String EMPTY_STRING = "";

    private final Scanner scanner;
    private final OutputStream outputStream;

    public Ui() {
        outputStream = System.out;
        scanner = new Scanner(System.in);
    }

    public Ui(OutputStream outputStream) {
        this.outputStream = outputStream;
        scanner = new Scanner(System.in);
    }

    public void printTableRow(ArrayList<String> rowValues, Integer[] customWidths) {
        printTableRow(rowValues, null, customWidths);
    }

    public void printTableRow(ArrayList<String> rowValues, String[] headers) {
        printTableRow(rowValues, headers, null);
    }

    public void printTableRow(ArrayList<String> rowValues, String[] headers, Integer[] customWidths) {
        assert rowValues != null;
        ArrayList<Integer> colWidths = printTableHeader(headers, customWidths);
        if (colWidths == null) {
            colWidths = getDefaultColWidths(rowValues.size());
            colWidths = mergeColWidths(colWidths, customWidths);
        }
        print(formatColumnValues(colWidths, rowValues));
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows) {
        printTableRows(rows, null, null);
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows, String[] headers) {
        printTableRows(rows, headers, null);
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows, String[] headers, Integer[] customWidths) {
        assert rows != null;
        ArrayList<Integer> colWidths = printTableHeader(headers, customWidths);
        if (rows.isEmpty()) {
            return;
        }

        if (colWidths == null) {
            colWidths = getPrintWidths(genColWidths(rows.get(0).size(), 0), customWidths);
        }

        for (ArrayList<String> rowValues : rows) {
            print(formatColumnValues(colWidths, rowValues));
        }
    }

    public ArrayList<Integer> printTableHeader(String[] headers, Integer[] customWidths) {
        if (headers == null) {
            return null;
        }

        ArrayList<Integer> colWidths = (ArrayList<Integer>) Arrays.stream(headers)
                .parallel()
                .map(String::length)
                .collect(Collectors.toList());
        colWidths = getPrintWidths(colWidths, customWidths);
        List<String> headerList = Arrays.asList(headers);
        print(formatColumnValues(colWidths, new ArrayList<>(headerList)));
        return colWidths;
    }

    public String formatAmount(Double value) {
        return String.format(AMOUNT_FORMAT, value);
    }

    public void print(String value) {
        try {
            outputStream.write(value.getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_DELIMITER);
            outputStream.flush();
        } catch (IOException e) {
            // Fail quietly for now
        }
    }

    public void printGreeting() {
        print("Welcome to " + PROGRAM_NAME + ", your personal finance tracker.");
    }

    public void printBye() {
        print("Bye Bye!");
    }

    public String readUserInput() throws Exception {
        String userInput = EMPTY_STRING;
        try {
            userInput = scanner.nextLine();
        } catch (Exception e) {
            if (e instanceof java.util.NoSuchElementException) {
                System.exit(0);
            } else {
                throw new Exception(e.getMessage());
            }
        }
        return userInput;
    }

    public void close() {
        scanner.close();
    }

    private ArrayList<Integer> genColWidths(int length, int width) {
        return (ArrayList<Integer>) IntStream.range(0, length)
                .mapToObj(i -> width)
                .collect(Collectors.toList());
    }

    private ArrayList<Integer> getDefaultColWidths(int length) {
        return genColWidths(length, COLUMN_WIDTH);
    }

    private ArrayList<Integer> getPrintWidths(ArrayList<Integer> colWidths, Integer[] customWidths) {
        if (customWidths == null) {
            colWidths = mergeColWidths(colWidths, getDefaultColWidths(colWidths.size()));
        } else {
            colWidths = mergeColWidths(colWidths, customWidths);
        }
        return colWidths;
    }

    private ArrayList<Integer> mergeColWidths(ArrayList<Integer> colWidths, ArrayList<Integer> customWidths) {
        if (customWidths == null) {
            return mergeColWidths(colWidths, (Integer[]) null);
        }
        Integer[] customWidthArray = new Integer[customWidths.size()];
        customWidths.toArray(customWidthArray);
        return mergeColWidths(colWidths, customWidthArray);
    }

    private ArrayList<Integer> mergeColWidths(ArrayList<Integer> colWidths, Integer[] customWidths) {
        assert colWidths != null;
        if (customWidths == null) {
            return colWidths;
        }

        assert colWidths.size() <= customWidths.length;
        int colCount = colWidths.size();
        for (int i = 0; i < colCount; ++i) {
            colWidths.add(i, Math.max(colWidths.get(i), customWidths[i]));
        }

        return colWidths;
    }

    private String formatColumnValues(ArrayList<Integer> colWidths, ArrayList<String> colValues) {
        assert colWidths != null;
        assert colValues != null;
        assert colWidths.size() >= colValues.size();

        ArrayList<String> finalValues = new ArrayList<>(colValues.size());
        int lastIdx = colValues.size() - 1;
        for (int i = 0; i < colValues.size(); ++i) {
            int maxWidth = colWidths.get(i);
            String truncatedValue = colValues.get(i);
            if (truncatedValue.length() > maxWidth) {
                truncatedValue = truncatedValue.substring(0, maxWidth - ELLIPSIS.length()) + ELLIPSIS;
            } else if (i != lastIdx) {
                char[] fillerChars = new char[maxWidth - truncatedValue.length()];
                Arrays.fill(fillerChars, FILLER_CHAR);
                truncatedValue += new String(fillerChars);
            }
            finalValues.add(i, truncatedValue);
        }

        char[] spacer = new char[SPACE_BETWEEN_COLS];
        Arrays.fill(spacer, FILLER_CHAR);
        return String.join(new String(spacer), finalValues);
    }

    public void listTransactions(ArrayList<ArrayList<String>> list, String[] headers, String headerMessage) {
        String end = " transactions.";
        if (list.size() == 1) {
            end = " transaction.";
        }
        print("Alright! Displaying " + list.size() + end);
        Integer[] columnWidths = {Integer.toString(list.size()).length() + ID_COLUMN_PADDING, LIST_COLUMN_WIDTH,
            COLUMN_WIDTH, COLUMN_WIDTH, TYPE_WIDTH, COLUMN_WIDTH};
        String wrapper = createWrapper(columnWidths, headerMessage);
        print(wrapper);
        printTableRows(list, headers, columnWidths);
        print(wrapper);

    }

    private String createWrapper(Integer[] columnWidths, String headerMessage) {
        int totalSpace = Arrays.stream(columnWidths)
                .mapToInt(Integer::intValue)
                .sum();
        totalSpace = totalSpace + (SPACE_BETWEEN_COLS * columnWidths.length) - headerMessage.length();
        int leftSide = totalSpace / 2;
        int rightSide = totalSpace - leftSide;
        String leftPad = new String(new char[leftSide]).replace('\0', LIST_SEPARATOR);
        String rightPad = new String(new char[rightSide]).replace('\0', LIST_SEPARATOR);
        StringJoiner wrapper = new StringJoiner(" ");
        wrapper.add(leftPad);
        wrapper.add(headerMessage);
        wrapper.add(rightPad);
        return(wrapper.toString());
    }
    public void printGoalsStatus(HashMap<Goal, Double> goalsMap) {
        ArrayList<TypePrint> goalsToPrint = new ArrayList<>();
        TypePrint uncategorised = null;
        Goal uncategorisedGoal = StateManager.getStateManager().getUncategorisedGoal();
        if (goalsMap.containsKey(uncategorisedGoal)) {
            String description = uncategorisedGoal.getDescription();
            double currentAmount = goalsMap.get(uncategorisedGoal);
            uncategorised = new TypePrint(description, currentAmount);
            goalsMap.remove(uncategorisedGoal);
        }
        for (Map.Entry<Goal, Double> entry : goalsMap.entrySet()) {
            String description = entry.getKey().getDescription();
            double currentAmount = entry.getValue();
            double targetAmount = entry.getKey().getAmount();
            TypePrint goalEntry = new TypePrint(description, currentAmount, targetAmount);
            goalsToPrint.add(goalEntry);
        }
        Comparator<TypePrint> typeComparator = Comparator.comparing(TypePrint::getDescription);
        goalsToPrint.sort(typeComparator);
        if (uncategorised != null) {
            goalsToPrint.add(uncategorised);
        }
        String headerMessage = "Goals Status";
        String wrapper = createWrapper(TYPE_COLUMN_WIDTHS, headerMessage);
        print(wrapper);
        printStatus(goalsToPrint);
        printUnusedGoals(goalsMap);
        print(wrapper);
    }

    private void printStatus(ArrayList<TypePrint> arrayToPrint) {
        if (arrayToPrint.isEmpty()) {
            String message = "No existing transactions";
            print(message);
            return;
        }
        String[] headers = {"Name", "Amount"};
        printTableHeader(headers, TYPE_COLUMN_WIDTHS);
        for (TypePrint c : arrayToPrint) {
            ArrayList<String> entry = new ArrayList<>();
            entry.add(c.getDescription());
            entry.add(c.getAmount());
            printTableRow(entry, TYPE_COLUMN_WIDTHS);
            if (c.targetAmountExists()) {
                progressBar(c.getPercentage());
            }
        }
    }

    public void progressBar(Double percentage) {
        int maxBars = 20;
        int steps = 5;
        double barCalculation = percentage / steps;
        int barsToPrint = (int) Math.floor(barCalculation);
        String openingSeparator = "[";
        String closingSeparator = "]";
        String progressBar = new String(new char[barsToPrint]).replace('\0', '=');
        String filler = new String(new char[maxBars - barsToPrint]).replace('\0', ' ');
        String progress = "Progress: " + openingSeparator + progressBar + filler
                + closingSeparator + " " + formatAmount(percentage) + "%";
        print(progress);
    }
  
    private void printUnusedGoals(HashMap<Goal, Double> goals) {
        HashSet<Goal> keySet = new HashSet<>(goals.keySet());
        ArrayList<ArrayList<String>> unusedGoals = new ArrayList<>();
        ArrayList<Goal> goalList = StateManager.getStateManager().getAllGoals();
        for (Goal g : goalList) {
            if (!keySet.contains(g)) {
                ArrayList<String> unusedGoal = new ArrayList<>();
                unusedGoal.add(g.getDescription());
                unusedGoal.add(formatAmount(g.getAmount()));
                unusedGoals.add(unusedGoal);
            }
        }
        if (unusedGoals.isEmpty()) {
            return;
        }
        String unusedHeader = LINE_DELIMITER + "Unused Goals:";
        print(unusedHeader);
        String[] header = {"Goal", "Target Amount"};
        printTableRows(unusedGoals, header, TYPE_COLUMN_WIDTHS);
    }

    public void printCategoryStatus(HashMap<Category, Double> categoryMap) {
        ArrayList<TypePrint> categoriesToPrint = new ArrayList<>();
        Category uncategorisedCategory = StateManager.getStateManager().getUncategorisedCategory();
        TypePrint uncategorised = null;
        if (categoryMap.containsKey(uncategorisedCategory)) {
            String description = uncategorisedCategory.getName();
            double currentAmount = categoryMap.get(uncategorisedCategory);
            uncategorised = new TypePrint(description, currentAmount);
            categoryMap.remove(uncategorisedCategory);
        }

        for (Map.Entry<Category, Double> entry : categoryMap.entrySet()) {
            String description = entry.getKey().getName();
            double currentAmount = entry.getValue();
            TypePrint categoryEntry = new TypePrint(description, currentAmount);
            categoriesToPrint.add(categoryEntry);
        }
        Comparator<TypePrint> typeComparator = Comparator.comparing(TypePrint::getDescription);
        categoriesToPrint.sort(typeComparator);
        if (uncategorised != null) {
            categoriesToPrint.add(uncategorised);
        }
        String headerMessage = "Categories Status";
        String wrapper = createWrapper(TYPE_COLUMN_WIDTHS, headerMessage);
        print(wrapper);
        printStatus(categoriesToPrint);
        printUnusedCategories(categoryMap);
        print(wrapper);

    }

    private void printUnusedCategories(HashMap<Category, Double> categories) {
        HashSet<Category> keySet = new HashSet<>(categories.keySet());
        List<String> unusedCategories = new ArrayList<>();
        ArrayList<Category> categoryList = StateManager.getStateManager().getAllCategories();
        for (Category c : categoryList) {
            if (!keySet.contains(c)) {
                unusedCategories.add(c.getName());
            }
        }
        if (unusedCategories.isEmpty()) {
            return;
        }
        unusedCategories.sort(String::compareToIgnoreCase);
        String header = LINE_DELIMITER + "Unused Categories:";
        print(header);
        for (String s : unusedCategories) {
            print(s);
        }
    }
}
