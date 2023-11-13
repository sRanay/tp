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
    private static final String IN_COMMAND_USAGE = " DESCRIPTION /amount AMOUNT [/goal GOAL] [/date DATE in DDMMYYYY]" +
                                                   " [/recurrence RECURRENCE]";
    private static final String[] IN_COMMAND_FLAGS = {"/amount", "/goal", "/date", "/recurrence"};
    private static final String[] IN_COMMAND_FLAGS_DESCRIPTION = {"Amount to be added", 
                                                                  "The goal to classify it under", 
                                                                  "Date of the transaction",
                                                                  "Indicates whether the income" +
                                                                  " added is recurring"};
    private static final String OUT_COMMAND = "out";
    private static final String OUT_DESCRIPTION = "Adds an expense for a category";
    private static final String OUT_COMMAND_USAGE = " DESCRIPTION /amount AMOUNT " +
                                                    "[/category CATEGORY] [/date DATE in DDMMYYYY]" +
                                                    " [/recurrence RECURRENCE]";
    private static final String[] OUT_COMMAND_FLAGS = {"/amount", "/category", "/date", "/recurrence"};
    private static final String[] OUT_COMMAND_FLAGS_DESCRIPTION = {"Amount to be deducted", 
                                                                   "The spending category to classify it under", 
                                                                   "Date of the transaction",
                                                                   "Indicates whether the expense" +
                                                                   " added is recurring"};
    private static final String DELETE_COMMAND = "delete";
    private static final String DELETE_DESCRIPTION = "Delete a specific transaction based on the index in the list";
    private static final String DELETE_COMMAND_USAGE = " INDEX /type (in | out)";
    private static final String[] DELETE_COMMAND_FLAGS = {"/type"};
    private static final String[] DELETE_COMMAND_FLAGS_DESCRIPTION = {"To set whether it is a in or out transaction"};
    private static final String LIST_COMMAND = "list";
    private static final String LIST_DESCRIPTION = "Shows a list of all added transactions based on type";
    private static final String LIST_COMMAND_USAGE_TRANSACTION = " /type (in | out) [/goal GOAL] [/category CATEGORY]" +
                                                                 " [/week] [/month]";
    private static final String LIST_COMMAND_USAGE_GOALCAT = " (goal | category)";
    private static final String[] LIST_COMMAND_FLAGS = {"/type", "/goal", "/category", "/week", "/month"};
    private static final String[] LIST_COMMAND_FLAGS_DESCRIPTION = {"To set whether to display \"in\" or" +
                                                                    " \"out\" transactions",
                                                                    "The goal which it is classified under",
                                                                    "The spending category which" +
                                                                    " it is classified under",
                                                                    "To filter the transactions to those in the " +
                                                                    "current week",
                                                                    "To filter the transactions to those in the " +
                                                                    "current month"};
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
                                                                        "Name of spending category to be deleted"};
    private static final String EDIT_COMMAND = "edit";
    private static final String EDIT_DESCRIPTION = "Edits an existing transaction";
    private static final String EDIT_COMMAND_USAGE = " INDEX /type (in | out) (/description DESCRIPTION | " +
                                                     "/amount AMOUNT | /goal GOAL | /category CATEGORY)";
    private static final String[] EDIT_COMMAND_FLAGS = {"/type", "/description", "/amount", "/goal", "/category"};
    private static final String[] EDIT_COMMAND_FLAGS_DESCRIPTION = {"To specify either in or out " +
                                                                    "transaction to be edited",
                                                                    "New description to be specified",
                                                                    "New amount to be specified",
                                                                    "New goal to be specified",
                                                                    "New category to be specified"};
    private static final String SUMMARY_COMMAND = "summary";
    private static final String SUMMARY_DESCRIPTION = "Shows the summarised total of transactions";
    private static final String SUMMARY_COMMAND_USAGE = " /type (in | out) [/day] [/week] [/month]";
    private static final String[] SUMMARY_COMMAND_FLAGS = {"/type", "/day", "/week", "/month"};
    private static final String[] SUMMARY_COMMAND_FLAGS_DESCRIPTION = {"To specific either in or out transaction to " +
                                                                       "be listed",
                                                                       "To filter transactions to those of current day",
                                                                       "To filter the transactions to those in the " +
                                                                       "current week",
                                                                       "To filter the transactions to those in the " +
                                                                       "current month"};

    private static final String BYE_COMMAND = "bye";
    private static final String BYE_DESCRIPTION = "Exits the program";
    private static final String USAGE_PREFIX = "Usage: ";
    private static final String INVALID_COMMAND = "NO SUCH COMMAND";
    private ArrayList<ArrayList<String>> helpList;

    public HelpCommand(String command, HashMap<String, String> args) {
        super(command, args);
        helpList = new ArrayList<ArrayList<String>>();
    }

    /**
     * Adds command name and its description to the ArrayList.
     *
     * @param command Command name.
     * @param description Description of the command.
     * @return ArrayList that contains both the command name and its description.
     */
    public ArrayList<String> convertCommandList(String command, String description) {
        ArrayList<String> tableData = new ArrayList<String>();
        tableData.add(command);
        tableData.add(description);
        return tableData;
    }

    /**
     * Returns the Arraylist that contains the command name and its description.
     *
     * @param commandName Name of the command.
     * @param commandDescription Description for the command.
     * @return ArrayList that contains both the command and its description.
     */
    public ArrayList<String> printCommandDescription(String commandName, String commandDescription) {
        ArrayList<String> commandDescriptionList = convertCommandList(commandName, commandDescription);
        return commandDescriptionList;
    }

    /**
     * Add all the commands into helpList to be printed out.
     */
    public void printFullList() {
        this.helpList.add(printCommandDescription(HELP_COMMAND, HELP_DESCRIPTION));
        this.helpList.add(printCommandDescription(IN_COMMAND, IN_DESCRIPTION));
        this.helpList.add(printCommandDescription(OUT_COMMAND, OUT_DESCRIPTION));
        this.helpList.add(printCommandDescription(DELETE_COMMAND, DELETE_DESCRIPTION));
        this.helpList.add(printCommandDescription(LIST_COMMAND, LIST_DESCRIPTION));
        this.helpList.add(printCommandDescription(CATEGORY_COMMAND, CATEGORY_DESCRIPTION));
        this.helpList.add(printCommandDescription(GOAL_COMMAND, GOAL_DESCRIPTION));
        this.helpList.add(printCommandDescription(EXPORT_COMMAND, EXPORT_DESCRIPTION));
        this.helpList.add(printCommandDescription(EDIT_COMMAND, EDIT_DESCRIPTION));
        this.helpList.add(printCommandDescription(SUMMARY_COMMAND, SUMMARY_DESCRIPTION));
        this.helpList.add(printCommandDescription(BYE_COMMAND, BYE_DESCRIPTION));
        assert this.helpList != null;
    }

    /**
     * Crafts the help usage string.
     *
     * @return help usage string.
     */
    public String helpUsage() {
        return USAGE_PREFIX + HELP_COMMAND;
    }

    /**
     * Crafts the bye usage string.
     *
     * @return bye usage string.
     */
    public String byeUsage() {
        return USAGE_PREFIX + BYE_COMMAND;
    }

    /**
     * Crafts the in usage string.
     *
     * @return in usage string.
     */
    public String inUsage() {
        return USAGE_PREFIX + IN_COMMAND + IN_COMMAND_USAGE;
    }

    /**
     * Crafts the out usage string.
     *
     * @return out usage string.
     */
    public String outUsage() {
        return USAGE_PREFIX + OUT_COMMAND + OUT_COMMAND_USAGE;
    }

    /**
     * Crafts the delete usage string.
     *
     * @return delete usage string.
     */
    public String deleteUsage() {
        return USAGE_PREFIX + DELETE_COMMAND + DELETE_COMMAND_USAGE;
    }

    /**
     * Crafts the list usage string for Transactions.
     *
     * @return list usage string for Transaction.
     */
    public String listTransactionUsage() {
        return USAGE_PREFIX + LIST_COMMAND + LIST_COMMAND_USAGE_TRANSACTION;
    }
    /**
     * Crafts the list usage string for Goal and Category.
     *
     * @return list usage string for Goal and Category.
     */
    public String listGoalCategoryUsage() {
        return USAGE_PREFIX + LIST_COMMAND + LIST_COMMAND_USAGE_GOALCAT;
    }

    /**
     * Crafts the export usage string.
     *
     * @return export usage string.
     */
    public String exportUsage() {
        return USAGE_PREFIX + EXPORT_COMMAND + EXPORT_COMMAND_USAGE;
    }

    /**
     * Crafts the category add string.
     *
     * @return category add usage string.
     */
    public String categoryAddUsage() {
        return USAGE_PREFIX + CATEGORY_COMMAND + CATEGORY_ADD_USAGE;
    }

    /**
     * Crafts the category remove usage string.
     *
     * @return category remove usage string.
     */
    public String categoryRemoveUsage() {
        return USAGE_PREFIX + CATEGORY_COMMAND + CATEGORY_REMOVE_USAGE;
    }

    /**
     * Crafts the goal add string.
     *
     * @return goal add usage string.
     */
    public String goalAddUsage() {
        return USAGE_PREFIX + GOAL_COMMAND + GOAL_ADD_USAGE;
    }

    /**
     * Crafts the goal remove usage string.
     *
     * @return goal remove usage string.
     */
    public String goalRemoveUsage() {
        return USAGE_PREFIX + GOAL_COMMAND + GOAL_REMOVE_USAGE;
    }

    /**
     * Crafts the edit usage string.
     *
     * @return edit usage string.
     */
    public String editUsage() {
        return USAGE_PREFIX + EDIT_COMMAND + EDIT_COMMAND_USAGE;
    }

    /**
     * Crafts the edit usage string.
     *
     * @return edit usage string.
     */
    public String summaryUsage() {
        return USAGE_PREFIX + SUMMARY_COMMAND + SUMMARY_COMMAND_USAGE;
    }

    /**
     * Converts the command flags and description into ArrayList and stores it into helpList.
     *
     * @param flags Flags for the command.
     * @param description Description for the flags.
     */
    public void convertIntoList(String[] flags, String[] description) {
        for (int i = 0; i < flags.length; i++) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(flags[i]);
            row.add(description[i]);
            this.helpList.add(row);
        }
    }

    /**
     * Prints all the commands and their description or the specific commands's flag and their description.
     *
     * @param ui Ui class that is used to print in table format.
     */
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
            ui.print(listGoalCategoryUsage());
            ui.print(listTransactionUsage());
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
        case "bye":
            ui.print(byeUsage());
            break;
        case "edit":
            ui.print(editUsage());
            convertIntoList(EDIT_COMMAND_FLAGS, EDIT_COMMAND_FLAGS_DESCRIPTION);
            break;
        case "summary":
            ui.print(summaryUsage());
            convertIntoList(SUMMARY_COMMAND_FLAGS, SUMMARY_COMMAND_FLAGS_DESCRIPTION);
            break;
        default:
            ui.print(INVALID_COMMAND);
            break;
        }
        
        if (!this.helpList.isEmpty()) {
            ui.printTableRows(this.helpList, FLAG_DESCRIPTION_HEADERS, CUSTOM_COLUMN_WIDTH);
        }
    }

    /**
     * Executes the command.
     *
     * @param ui Ui class that is used to print in table format.
     */
    @Override
    public void execute(Ui ui) {
        ui.print(LINE_DIVIDER);
        updateOutput(ui);
        ui.print(LINE_DIVIDER);
    }
}
