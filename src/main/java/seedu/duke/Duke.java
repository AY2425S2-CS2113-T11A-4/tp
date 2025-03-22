package seedu.duke;

import commands.Command;
import commands.IncomeCommand;
import commands.ListIncomeCommand;
import commands.ExitCommand;
import commands.ViewExpenseCommand;
import exceptions.BudgetTrackerException;
import expenses.Ui;
import income.IncomeParser;
import summary.Summary;
import summary.ui.SummaryDisplay;
import ui.HelpDisplay;
import expenses.BudgetTracker;
import expenses.ExpenseParser;
import expenses.ExpenseList;
import savings.Saving;
import alerts.FundsAlert;
import alerts.AlertParser;
import java.util.Scanner;

public class Duke {
    private final Scanner in;
    private final Summary summary;
    private final SummaryDisplay summaryDisplay;
    private final HelpDisplay helpDisplay;
    private final Ui ui;
    private final BudgetTracker tracker;
    private final ExpenseList expenseList;
    private final Saving saving;
    private final FundsAlert fundsAlert;
    
    /**
     * Constructs a new Duke application with all necessary components initialized.
     */
    public Duke() {
        in = new Scanner(System.in);
        summary = new Summary();
        summaryDisplay = new SummaryDisplay(summary);
        helpDisplay = new HelpDisplay();
        ui = new Ui();
        tracker = new BudgetTracker();
        expenseList = new ExpenseList();
        saving = new Saving(summary);
        fundsAlert = new FundsAlert(ui);
        summary.registerObserver(fundsAlert);
    }
    
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        displayWelcomeMessage();
        new Duke().runDuke();
    }
    
    /**
     * Displays the welcome message for the application.
     */
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to Common Cents!");
        System.out.println("Use `help` to see available commands.");
    }
    
    /**
     * Runs the main program loop, processing user commands until exit.
     */
    public void runDuke() {
        fundsAlert.displayInitialNotification();

        // Main program loop
        while (true) {
            try {
                // Check if there's input available before reading
                if (!in.hasNextLine()) {
                    System.out.println("No input available. Exiting program.");
                    break;
                }

                String fullCommand = in.nextLine();
                Command command = null;
                IncomeCommand incomeCommand = null;
                boolean commandRecognized = false;

                // Handle help command
                if (fullCommand.equals("help")) {
                    helpDisplay.display();
                    commandRecognized = true;
                    continue;
                }

                // Handle exit command
                if (fullCommand.startsWith("bye")) {
                    Command exitCommand = new ExitCommand();
                    exitCommand.execute(expenseList, ui);
                    break;
                }

                // Handle view commands
                if (fullCommand.equals("view income")) {
                    ListIncomeCommand listIncomeCommand = new ListIncomeCommand(summary);
                    listIncomeCommand.execute();
                    commandRecognized = true;
                    continue;
                } else if (fullCommand.equals("view expense")) {
                    ViewExpenseCommand viewExpenseCommand = new ViewExpenseCommand(expenseList);
                    viewExpenseCommand.execute(expenseList, ui);
                    commandRecognized = true;
                    continue;
                } else if (fullCommand.equals("view summary")) {
                    summaryDisplay.displaySummary();
                    commandRecognized = true;
                    continue;
                }
                
                // Handle alert commands
                if (fullCommand.startsWith("alert")) {
                    try {
                        Command alertCommand = AlertParser.parse(fullCommand, fundsAlert);
                        alertCommand.execute(expenseList, ui);
                        commandRecognized = true;
                        continue;
                    } catch (BudgetTrackerException e) {
                        ui.showMessage(e.getMessage());
                        commandRecognized = true;
                        continue;
                    }
                }

                // Handle income-related commands
                if (fullCommand.startsWith("add income")) {
                    incomeCommand = IncomeParser.parseAddIncomeCommand(fullCommand, summary);
                    commandRecognized = true;
                } else if (fullCommand.startsWith("delete income")) {
                    incomeCommand = IncomeParser.parseDeleteIncomeCommand(fullCommand, summary);
                    commandRecognized = true;
                }

                // Handle expense-related commands
                if (fullCommand.startsWith("add expense")) {
                    command = ExpenseParser.parse(fullCommand, summary, expenseList);
                    commandRecognized = true;
                } else if (fullCommand.startsWith("delete expense")) {
                    command = ExpenseParser.parse(fullCommand, summary, expenseList);
                    commandRecognized = true;
                }

                // Handle savings commands
                if (fullCommand.contains("savings")) {
                    saving.run(fullCommand);
                    commandRecognized = true;
                }

                // Execute income commands if any
                if (incomeCommand != null) {
                    tracker.executeincomeCommand(incomeCommand, ui);
                    if (incomeCommand.isExit()) {
                        break;
                    }
                }

                // Execute expense commands if any
                if (command != null) {
                    command.execute(expenseList, ui);  // Pass the correct expenseList here
                    if (command.isExit()) {
                        break;
                    }
                }

                // Handle unrecognized commands
                if (!commandRecognized && !fullCommand.trim().isEmpty()) {
                    System.out.println("Oops! I don't recognize that command. Type 'help' to see available commands.");
                }

            } catch (BudgetTrackerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
