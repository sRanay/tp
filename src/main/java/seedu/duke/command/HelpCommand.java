package seedu.duke.command;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.duke.ui.Ui;

public class HelpCommand extends Command {
    private static final String LINE_DIVIDER = "";
    private static final String[] FULL_LIST_HEADERS = {"Command", "Description"};
    private static final String[] FLAG_DESCRIPTION_HEADERS = {"Option", "Description"};
    private static final Integer[] CUSTOM_COLUMN_WIDTH = {15, 1000};
    private static final String HELP_COMMAND = "help";
    private static final String HELP_DESCRIPTION = "Shows a list of all the commands available to the user";
    private static final String IN_COMMAND = "in";
    private static final String IN_DESCRIPTION = "Adds an income towards goal";
    private static final String IN_COMMAND_USAGE = " DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]" +
                                                   " [/recurrence RECURRENCE]";
    private static final String[] IN_COMMAND_FLAGS = {"/amount", "/goal", "/date", "/recurrence"};
    private static final String[] IN_COMMAND_FLAGS_DESCRIPTION = {"Amount to be added", 
                                                                  "The goal to classify it under", 
                                                                  "Date of the transaction",
                                                                  "Indicates whether of the income" +
                                                                  " added is recurring."};
    private static final String OUT_COMMAND = "out";
    private static final String OUT_DESCRIPTION = "Adds an expense for a category";
    private static final String OUT_COMMAND_USAGE = " DESCRIPTION /amount AMOUNT " +
                                                    "/category CATEGORY [/date DATE in DDMMYYYY]" +
                                                    " [/recurrence RECURRENCE]";
    private static final String[] OUT_COMMAND_FLAGS = {"/amount", "/category", "/date", "/recurrence"};
    private static final String[] OUT_COMMAND_FLAGS_DESCRIPTION = {"Amount to be deducted", 
                                                                   "The spending category to classify it under", 
                                                                   "Date of the transaction",
                                                                   "Indicates whether of the expense" +
                                                                   " added is recurring"};
    private static final String DELETE_COMMAND = "delete";
    private static final String DELETE_DESCRIPTION = "Delete a specific transaction based on the index in the list";
    private static final String DELETE_COMMAND_USAGE = " INDEX /type (in | out)";
    private static final String[] DELETE_COMMAND_FLAGS = {"/type"};
    private static final String[] DELETE_COMMAND_FLAGS_DESCRIPTION = {"To set whether it is a in or out transaction"};
    private static final String LIST_COMMAND = "list";
    private static final String LIST_DESCRIPTION = "Shows a list of all added transactions based on type";
    private static final String LIST_COMMAND_USAGE = " /type (in | out) [/goal GOAL] [/category CATEGORY]";
    private static final String[] LIST_COMMAND_FLAGS = {"/type", "/goal", "/category"};
    private static final String[] LIST_COMMAND_FLAGS_DESCRIPTION = {"To set whether to display \"in\" or" +
                                                                    " \"out\" transactions",
                                                                    "The goal which it is classified under",
                                                                    "The spending category which" +
                                                                    " it is classified under"};
    private static final String EXPORT_COMMAND = "export";
    private static final String EXPORT_DESCRIPTION = "Exports the transactions stored into a CSV File. " +
                                                     "By Default, it will export ALL transactions";
    private static final String EXPORT_COMMAND_USAGE = " [/type (in | out)]";
    private static final String[] EXPORT_COMMAND_FLAGS = {"/type"};
    private static final String[] EXPORT_COMMAND_FLAGS_DESCRIPTION = {"To set whether to extract all" +
                                                                      " \"in\" or \"out\" transactions"};
    private static final String GOAL_COMMAND = "goal";
    private static final String GOAL_DESCRIPTION = "Add or remove goals";
    private static final String GOAL_ADD_USAGE = " /add NAME /amount AMOUNT";
    private static final String GOAL_REMOVE_USAGE = " /remove NAME";
    private static final String[] GOAL_COMMAND_FLAGS = {"/add", "/amount", "/remove"};
    private static final String[] GOAL_COMMAND_FLAGS_DESCRIPTION = {"Name of goal to be added",
                                                                    "The amount set for the goal",
                                                                    "Name of goal to be removed"};
    private static final String CATEGORY_COMMAND = "category";
    private static final String CATEGORY_DESCRIPTION = "Create or delete a spending category";
    private static final String CATEGORY_ADD_USAGE = " /add NAME";
    private static final String CATEGORY_REMOVE_USAGE = " /remove NAME";
    private static final String[] CATEGORY_COMMAND_FLAGS = {"/add", "/remove"};
    private static final String[] CATEGORY_COMMAND_FLAGS_DESCRIPTION = {"Name of spending category to be created",
                                                                        "Name of spending cateogry to be deleted"};
    private static final String USAGE_PREFIX = "Usage: ";
    private static final String INVALID_COMMAND = "NO SUCH COMMAND";
    private ArrayList<ArrayList<String>> helpList;

    public HelpCommand(String command, HashMap<String, String> args) {
        super(command, args);
        helpList = new ArrayList<ArrayList<String>>();
    }

    public ArrayList<String> convertCommandList(String command, String description) {
        ArrayList<String> tableData = new ArrayList<String>();
        tableData.add(command);
        tableData.add(description);
        return tableData;
    }

    public ArrayList<String> printHelpDescription() {
        ArrayList<String> helpDescriptionList = convertCommandList(HELP_COMMAND, HELP_DESCRIPTION);
        return helpDescriptionList;
    }

    public ArrayList<String> printInDescription() {
        ArrayList<String> inDescriptionList = convertCommandList(IN_COMMAND, IN_DESCRIPTION);
        return inDescriptionList;
    }

    public ArrayList<String> printOutDescription() {
        ArrayList<String> outDescriptionList = convertCommandList(OUT_COMMAND, OUT_DESCRIPTION);
        return outDescriptionList;
    }

    public ArrayList<String> printDeleteDescription() {
        ArrayList<String> deleteDescriptionList = convertCommandList(DELETE_COMMAND, DELETE_DESCRIPTION);
        return deleteDescriptionList;
    }

    public ArrayList<String> printListDescription() {
        ArrayList<String> listDescriptionList = convertCommandList(LIST_COMMAND, LIST_DESCRIPTION);
        return listDescriptionList;
    }

    public ArrayList<String> printExportDescription() {
        ArrayList<String> exportDescriptionList = convertCommandList(EXPORT_COMMAND, EXPORT_DESCRIPTION);
        return exportDescriptionList;
    }

    public ArrayList<String> printCategoryDescription() {
        ArrayList<String> categoryDescriptionList = convertCommandList(CATEGORY_COMMAND, CATEGORY_DESCRIPTION);
        return categoryDescriptionList;
    }

    public ArrayList<String> printGoalDescription() {
        ArrayList<String> goalDescriptionList = convertCommandList(GOAL_COMMAND, GOAL_DESCRIPTION);
        return goalDescriptionList;
    }

    public void printFullList() {
        this.helpList.add(printHelpDescription());
        this.helpList.add(printInDescription());
        this.helpList.add(printOutDescription());
        this.helpList.add(printDeleteDescription());
        this.helpList.add(printListDescription());
        this.helpList.add(printCategoryDescription());
        this.helpList.add(printGoalDescription());
        this.helpList.add(printExportDescription());
        assert this.helpList != null;
    }

    public String helpUsage() {
        return USAGE_PREFIX + HELP_COMMAND;
    }

    public String inUsage() {
        return USAGE_PREFIX + IN_COMMAND + IN_COMMAND_USAGE;
    }

    public String outUsage() {
        return USAGE_PREFIX + OUT_COMMAND + OUT_COMMAND_USAGE;
    }

    public String deleteUsage() {
        return USAGE_PREFIX + DELETE_COMMAND + DELETE_COMMAND_USAGE;
    }

    public String listUsage() {
        return USAGE_PREFIX + LIST_COMMAND + LIST_COMMAND_USAGE;
    }

    public String exportUsage() {
        return USAGE_PREFIX + EXPORT_COMMAND + EXPORT_COMMAND_USAGE;
    }
    public String categoryAddUsage() {
        return USAGE_PREFIX + CATEGORY_COMMAND + CATEGORY_ADD_USAGE;
    }

    public String categoryRemoveUsage() {
        return USAGE_PREFIX + CATEGORY_COMMAND + CATEGORY_REMOVE_USAGE;
    }

    public String goalAddUsage() {
        return USAGE_PREFIX + GOAL_COMMAND + GOAL_ADD_USAGE;
    }

    public String goalRemoveUsage() {
        return USAGE_PREFIX + GOAL_COMMAND + GOAL_REMOVE_USAGE;
    }

    public void convertIntoList(String[] flags, String[] description) {
        for (int i = 0; i < flags.length; i++) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(flags[i]);
            row.add(description[i]);
            this.helpList.add(row);
        }
    }

    public void updateOutput(Ui ui) {
        if (getDescription().isBlank()) {
            printFullList();
            ui.printTableRows(this.helpList, FULL_LIST_HEADERS, CUSTOM_COLUMN_WIDTH);
            return;
        }

        switch (getDescription().toLowerCase()) {
        case "help":
            ui.print(helpUsage());
            break;
        case "in":
            ui.print(inUsage());
            convertIntoList(IN_COMMAND_FLAGS, IN_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "out":
            ui.print(outUsage());
            convertIntoList(OUT_COMMAND_FLAGS, OUT_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "delete":
            ui.print(deleteUsage());
            convertIntoList(DELETE_COMMAND_FLAGS, DELETE_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "list":
            ui.print(listUsage());
            convertIntoList(LIST_COMMAND_FLAGS, LIST_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "export":
            ui.print(exportUsage());
            convertIntoList(EXPORT_COMMAND_FLAGS, EXPORT_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "goal":
            ui.print(goalAddUsage());
            ui.print(goalRemoveUsage());
            convertIntoList(GOAL_COMMAND_FLAGS, GOAL_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "category":
            ui.print(categoryAddUsage());
            ui.print(categoryRemoveUsage());
            convertIntoList(CATEGORY_COMMAND_FLAGS, CATEGORY_COMMAND_FLAGS_DESCRIPTION);
            break;
        default:
            ui.print(INVALID_COMMAND);
            break;
        }
        
        if (!this.helpList.isEmpty()) {
            ui.printTableRows(this.helpList, FLAG_DESCRIPTION_HEADERS, CUSTOM_COLUMN_WIDTH);
        }
    }

    @Override
    public void execute(Ui ui) {
        ui.print(LINE_DIVIDER);
        updateOutput(ui);
        ui.print(LINE_DIVIDER);
    }
}
