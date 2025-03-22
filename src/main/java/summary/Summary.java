package summary;

import exceptions.BudgetTrackerException;
import alerts.FinancialObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a financial summary, storing total income, expenses, and savings.
 * Provides methods for adding and removing income, expenses, and savings.
 */
public class Summary {
    private double totalIncome;
    private double totalExpense;
    private double totalSavings;
    private List<FinancialObserver> observers = new ArrayList<>();

    /**
     * Constructs a new Summary object with all values initialized to 0.
     */
    public Summary() {
        totalIncome = 0;
        totalExpense = 0;
        totalSavings = 0;
        
        // Assertion to verify initial state
        assert totalIncome == 0 : "Initial income should be 0";
        assert totalExpense == 0 : "Initial expense should be 0";
        assert totalSavings == 0 : "Initial savings should be 0";
    }

    /**
     * Registers an observer to be notified of financial changes.
     * 
     * @param observer The observer to register
     */
    public void registerObserver(FinancialObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from notification list.
     * 
     * @param observer The observer to remove
     */
    public void removeObserver(FinancialObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about changes in financial data.
     */
    private void notifyObservers() {
        double availableFunds = getAvailableFunds();
        for (FinancialObserver observer : observers) {
            observer.update(availableFunds, totalIncome, totalExpense, totalSavings);
        }
    }

    /**
     * Gets the total income.
     *
     * @return The total income.
     */
    public double getTotalIncome() {
        assert totalIncome >= 0 : "Total income should never be negative";
        return totalIncome;
    }

    /**
     * Gets the total expenses.
     *
     * @return The total expenses.
     */
    public double getTotalExpense() {
        assert totalExpense >= 0 : "Total expense should never be negative";
        return totalExpense;
    }

    /**
     * Gets the available funds (calculated as income - expenses).
     * This represents the amount available for spending or saving.
     *
     * @return The available funds.
     */
    public double getAvailableFunds() {
        return totalIncome - totalExpense;
    }

    /**
     * Gets the total savings.
     *
     * @return The total savings.
     */
    public double getTotalSavings() {
        assert totalSavings >= 0 : "Total savings should never be negative";
        return totalSavings;
    }


    /**
     * Adds income to the total income.
     *
     * @param income The amount of income to add.
     * @throws BudgetTrackerException If the income is negative.
     */
    public void addIncome(double income) throws BudgetTrackerException {
        if (income <= 0) {
            throw new BudgetTrackerException("Income must be positive.");
        }
        double oldIncome = this.totalIncome;
        this.totalIncome += income;
        
        // Assertion to verify income was added correctly
        assert this.totalIncome == oldIncome + income : "Income was not added correctly";
        
        notifyObservers();
    }

    /**
     * Removes income from the total income.
     *
     * @param income The amount of income to remove.
     * @throws BudgetTrackerException If the income is negative or greater than the current total income.
     */
    public void removeIncome(double income) throws BudgetTrackerException {
        if (income < 0) {
            throw new BudgetTrackerException("Income must be positive.");
        }
        if (income > this.totalIncome) {
            throw new BudgetTrackerException("Cannot remove more income than the current total income.");
        }
        double oldIncome = this.totalIncome;
        this.totalIncome -= income;
        
        // Assertion to verify income was removed correctly
        assert this.totalIncome == oldIncome - income : "Income was not removed correctly";
        assert this.totalIncome >= 0 : "Total income should never be negative after removal";
        
        notifyObservers();
    }

    /**
     * Adds an expense to the total expenses.
     *
     * @param expense The amount of the expense to add.
     * @throws BudgetTrackerException If the expense is negative or would result in a negative balance.
     */
    public void addExpense(double expense) throws BudgetTrackerException {
        if (expense <= 0) {
            throw new BudgetTrackerException("Expense must be positive.");
        }
        
        // Check if adding this expense would result in a negative balance
        double availableBalance = getAvailableFunds();
        if (expense > availableBalance) {
            throw new BudgetTrackerException("Cannot add this expense as it would exceed your available funds. "
                    + "Available balance: " + availableBalance);
        }
        
        double oldExpense = this.totalExpense;
        this.totalExpense += expense;
        
        // Assertion to verify expense was added correctly
        assert this.totalExpense == oldExpense + expense : "Expense was not added correctly";
        
        notifyObservers();
    }

    /**
     * Removes an expense from the total expenses.
     *
     * @param expense The amount of the expense to remove.
     * @throws BudgetTrackerException If the expense is negative or greater than the current total expenses.
     */
    public void removeExpense(double expense) throws BudgetTrackerException {
        if (expense <= 0) {
            throw new BudgetTrackerException("Expense must be positive.");
        }
        if (expense > this.totalExpense) {
            throw new BudgetTrackerException("Cannot remove more expense than the current total expenses.");
        }
        double oldExpense = this.totalExpense;
        this.totalExpense -= expense;
        
        // Assertion to verify expense was removed correctly
        assert this.totalExpense == oldExpense - expense : "Expense was not removed correctly";
        assert this.totalExpense >= 0 : "Total expense should never be negative after removal";
        
        notifyObservers();
    }

    /**
     * Adds savings to the total savings.
     *
     * @param savings The amount of savings to add.
     * @throws BudgetTrackerException If the savings are negative or greater than available funds.
     */
    public void addSavings(double savings) throws BudgetTrackerException {
        if (savings <= 0) {
            throw new BudgetTrackerException("Savings must be positive.");
        }

        double availableFunds = getAvailableFunds();
        
        if (savings > availableFunds) {
            throw new BudgetTrackerException("Savings cannot be greater than available funds.");
        }
        double oldSavings = this.totalSavings;
        this.totalSavings += savings;
        
        // Assertion to verify savings was added correctly
        assert this.totalSavings == oldSavings + savings : "Savings was not added correctly";
        
        notifyObservers();
    }

    /**
     * Removes savings from the total savings.
     *
     * @param savings The amount of savings to remove.
     * @throws BudgetTrackerException If the savings are negative or greater than the current total savings.
     */
    public void removeSavings(double savings) throws BudgetTrackerException {
        if (savings <= 0) {
            throw new BudgetTrackerException("Savings must be positive.");
        }
        if (savings > this.totalSavings) {
            throw new BudgetTrackerException("Cannot remove more savings than the current total savings.");
        }
        double oldSavings = this.totalSavings;
        this.totalSavings -= savings;
        
        // Assertion to verify savings was removed correctly
        assert this.totalSavings == oldSavings - savings : "Savings was not removed correctly";
        assert this.totalSavings >= 0 : "Total savings should never be negative after removal";
        
        notifyObservers();
    }
}
