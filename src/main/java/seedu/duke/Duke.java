package seedu.duke;

import seedu.duke.classes.StateManager;
import seedu.duke.classes.TransactionRecurrence;
import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.Ui;

/**
 * The type Duke.
 */
public class Duke {

    private static Ui ui;
    private static Storage storage;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
    }

    public void load() {
        try {
            storage.load();
            syncTransactions();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() throws DukeException {
        syncTransactions();
        storage.save();
    }

    public void syncTransactions() {
        TransactionRecurrence.generateRecurrentTransactions(
                StateManager.getStateManager().getAllIncomes(),
                StateManager.getStateManager().getAllExpenses()
        );
    }


    /**
     * Gets the user input and execute the command based on the input.
     */
    public void run() {
        ui.printGreeting();
        String userInput;
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.print("> User: ");

            try {
                userInput = ui.readUserInput();
                Command command = new Parser().parse(userInput);
                command.execute(ui);

                if (command instanceof ExitCommand) {
                    continueRunning = false;
                }
                save();

            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Oops unexpected error occurred.");
            }
        }
        ui.close();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print("\n");
            ui.printBye();
        }));
        Duke duke = new Duke();
        duke.load();
        duke.run();
    }
}
