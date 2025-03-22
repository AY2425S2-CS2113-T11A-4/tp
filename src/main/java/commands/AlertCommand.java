package commands;

import alerts.FundsAlert;
import exceptions.BudgetTrackerException;
import expenses.ExpenseList;
import expenses.Ui;

/**
 * Represents a command to set the funds alert warning threshold.
 */
public class AlertCommand extends Command {
    private final double threshold;
    private final FundsAlert fundsAlert;

    /**
     * Constructs an AlertCommand with the specified threshold and funds alert.
     *
     * @param threshold  The new warning threshold to set
     * @param fundsAlert The funds alert object to modify
     */
    public AlertCommand(double threshold, FundsAlert fundsAlert) {
        this.threshold = threshold;
        this.fundsAlert = fundsAlert;
    }

    /**
     * Executes the command to set the warning threshold for the funds alert.
     *
     * @param expenseList The expense list (not used in this command)
     * @param ui          The UI component used to display messages to the user
     * @throws BudgetTrackerException If the threshold is invalid
     */
    @Override
    public void execute(ExpenseList expenseList, Ui ui) throws BudgetTrackerException {
        try {
            fundsAlert.setWarningThreshold(threshold);
            ui.showMessage("Funds alert threshold set to $" + String.format("%.2f", threshold));
        } catch (BudgetTrackerException e) {
            ui.showMessage(e.getMessage());
            throw e; // Re-throw to inform the caller
        }
    }

    /**
     * Determines if this command should exit the application.
     *
     * @return False as this command does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
