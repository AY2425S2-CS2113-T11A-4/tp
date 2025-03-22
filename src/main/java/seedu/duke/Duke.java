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
import java.util.Scanner;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        displayWelcomeMessage();

        // Initialize components
        Scanner in = new Scanner(System.in);
        Summary summary = new Summary();
        SummaryDisplay summaryDisplay = new SummaryDisplay(summary);
        HelpDisplay helpDisplay = new HelpDisplay();
        Ui ui = new Ui();
        BudgetTracker tracker = new BudgetTracker();
        ExpenseList expenseList = new ExpenseList();
        Saving saving = new Saving(summary);

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
                    helpDisplay.displayHelp();
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

    public static void displayWelcomeMessage() {
        System.out.println("Welcome to Common Cents!");
        System.out.println("Use `help` to see available commands.");
    }
}