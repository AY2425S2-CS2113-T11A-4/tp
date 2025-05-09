package commands;

import expenses.Expense;
import expenses.Ui;
import expenses.ExpenseList;
import summary.Summary;
import exceptions.BudgetTrackerException;

/**
 * Represents a command to add a new expense to the expense list.
 */
public class AddExpenseCommand extends Command {
    private double amount;
    private String description;
    private Expense.Category category;
    private Summary summary;

    /**
     * Constructs an AddExpenseCommand with the specified amount, description, and summary.
     *
     * @param amount      The amount of the expense to be added.
     * @param description The description or source of the expense.
     * @param summary     The summary object to update with the new expense.
     */
    public AddExpenseCommand(double amount, String description, Expense.Category category, Summary summary) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.summary = summary;
    }

    /**
     * Executes the command to add the expense to the given expense list and updates the summary.
     *
     * @param expenseList The list of expenses to which the new expense will be added.
     * @param ui          The UI component used to display messages to the user.
     */
    @Override
    public void execute(ExpenseList expenseList, Ui ui) {
        try {
            summary.addExpense(amount);

            Expense newExpense = new Expense(amount, description, category);
            expenseList.addExpense(newExpense);

            ui.showMessage("Added expense: [" + category + "] $" + amount + " for " + description);
        } catch (BudgetTrackerException e) {
            ui.showMessage("Error adding expense: " + e.getMessage());
        }
    }

    /**
     * Checks if the command is an exit command.
     *
     * @return {@code false} as this command does not cause the program to exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
