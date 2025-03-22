package alerts;

/**
 * Interface for classes that want to observe changes in financial data.
 */
public interface FinancialObserver {
    /**
     * Called when financial data changes.
     * 
     * @param availableFunds Current available funds
     * @param totalIncome Current total income
     * @param totalExpense Current total expense
     * @param totalSavings Current total savings
     */
    void update(double availableFunds, double totalIncome, double totalExpense, double totalSavings);
}
