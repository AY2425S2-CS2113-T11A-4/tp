package alerts;

import expenses.Ui;
import exceptions.BudgetTrackerException;

/**
 * Alerts when funds fall below a warning threshold.
 * This class observes financial data without being tightly coupled to Summary.
 */
public class FundsAlert implements FinancialObserver {
    private double warningThreshold;
    private final Ui ui;
    
    /**
     * Creates a new FundsAlert with the default threshold of $5.
     * 
     * @param ui UI handler for displaying alerts
     */
    public FundsAlert(Ui ui) {
        this.warningThreshold = 5.0;
        this.ui = ui;
        assert warningThreshold > 0 : "Warning threshold must be positive";
        assert ui != null : "UI handler cannot be null";
    }
    
    /**
     * Changes the warning threshold.
     * 
     * @param newThreshold The new warning threshold
     * @return true if the threshold was changed successfully
     * @throws BudgetTrackerException if the threshold is negative
     */
    public boolean setWarningThreshold(double newThreshold) throws BudgetTrackerException {
        if (newThreshold < 0) {
            throw new BudgetTrackerException("Warning threshold cannot be negative");
        }
        
        assert newThreshold >= 0 : "Warning threshold must be non-negative";
        
        double oldThreshold = this.warningThreshold;
        this.warningThreshold = newThreshold;
        
        assert this.warningThreshold == newThreshold : "Threshold was not properly updated";
        assert this.warningThreshold != oldThreshold || newThreshold == oldThreshold : 
                "Threshold did not change when it should have";
        
        return true;
    }
    
    /**
     * Gets the current warning threshold.
     * 
     * @return The current warning threshold
     */
    public double getWarningThreshold() {
        assert warningThreshold >= 0 : "Warning threshold should never be negative";
        return warningThreshold;
    }
    
    @Override
    public void update(double availableFunds, double totalIncome, double totalExpense, double totalSavings) {
        assert availableFunds == totalIncome - totalExpense - totalSavings : 
                "Available funds calculation is inconsistent";
        checkAndDisplayAlert(availableFunds);
    }
    
    /**
     * Checks funds against threshold and displays alert if needed.
     * 
     * @param availableFunds Current available funds
     */
    private void checkAndDisplayAlert(double availableFunds) {
        if (availableFunds < warningThreshold) {
            String message = "WARNING: Available funds ($" + String.format("%.2f", availableFunds) + 
                    ") are below warning threshold ($" + String.format("%.2f", warningThreshold) + ")";
            ui.showAlert(message);
        }
    }
    
    /**
     * Displays the initial notification about the funds alert feature.
     */
    public void displayInitialNotification() {
        assert ui != null : "UI handler cannot be null when displaying notifications";
        assert warningThreshold >= 0 : "Warning threshold should be non-negative";
        
        String message = "Funds Alert feature is active. You will be warned when available funds fall below $" 
                + String.format("%.2f", warningThreshold) + ".";
        ui.showMessage(message);
        ui.showMessage("Use 'alert set <amount>' to change this threshold.");
    }
}
