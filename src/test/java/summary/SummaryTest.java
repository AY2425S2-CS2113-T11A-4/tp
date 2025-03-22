package summary;

import exceptions.BudgetTrackerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SummaryTest {

    @Test
    void getAvailableFunds_emptySummary_expectZero() throws BudgetTrackerException {
        Summary summary = new Summary();
        assertEquals(0, summary.getAvailableFunds(), 0.001);
    }

    @Test
    void getAvailableFunds_withIncome_expectCorrectBalance() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(100.0);
        assertEquals(100.0, summary.getAvailableFunds(), 0.001);
    }

    @Test
    void getAvailableFunds_withIncomeAndExpenses_expectCorrectBalance() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(100.0);
        summary.addExpense(50.0);
        assertEquals(50.0, summary.getAvailableFunds(), 0.001);
    }

    @Test
    void getAvailableFunds_multipleIncomeAndExpenses_expectCorrectBalance() throws BudgetTrackerException{
        Summary summary = new Summary();
        double balance = 0;
        summary.addIncome(200);
        summary.addExpense(50);
        summary.addIncome(100);
        summary.removeIncome(50);
        summary.removeExpense(25);
        assertEquals(225, summary.getAvailableFunds(), 0.001);
    }

    @Test
    void addIncome_positiveAmount_expectIncreaseInTotalIncome() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(100.0);
        assertEquals(100.0, summary.getTotalIncome(), 0.001);
    }

    @Test
    void addIncome_negativeAmount_expectException() {
        Summary summary = new Summary();
        assertThrows(BudgetTrackerException.class, () -> summary.addIncome(-100.0));
    }

    @Test
    void addIncome_zeroAmount_expectException() throws BudgetTrackerException{
        Summary summary = new Summary();
        assertThrows(BudgetTrackerException.class, () -> summary.addIncome(0));

    }
    @Test
    void addIncome_multipleAmounts_expectCorrectTotalIncome() throws BudgetTrackerException{
        Summary summary = new Summary();
        summary.addIncome(100.50);
        summary.addIncome(200.75);
        summary.addIncome(50);
        assertEquals(351.25, summary.getTotalIncome(),0.001);

    }

    @Test
    void addExpense_positiveAmount_expectIncreaseInTotalExpense() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(100.0); 
        summary.addExpense(50.0);
        assertEquals(50.0, summary.getTotalExpense(), 0.001);
    }

    @Test
    void addExpense_negativeAmount_expectException() {
        Summary summary = new Summary();
        assertThrows(BudgetTrackerException.class, () -> summary.addExpense(-50.0));
    }

    @Test
    void addExpense_zeroAmount_expectNoChange() throws BudgetTrackerException { 
        Summary summary = new Summary();
        assertThrows(BudgetTrackerException.class, () -> summary.addExpense(0));
    }

    @Test
    void addExpense_multipleAmounts_expectCorrectTotalExpense() throws BudgetTrackerException{
        Summary summary = new Summary();
        summary.addIncome(500.0); 
        summary.addExpense(100.50);
        summary.addExpense(200.75);
        summary.addExpense(50);
        assertEquals(351.25, summary.getTotalExpense(),0.001);
    }


    @Test
    void removeExpense_negativeAmount_expectException() {
        Summary summary = new Summary();
        assertThrows(BudgetTrackerException.class, () -> summary.removeExpense(-50.0));
    }

    @Test
    void removeSavings_multipleValidAmount_expectCorrectResult() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(200);
        summary.addSavings(100);
        summary.removeSavings(50);
        summary.removeSavings(25);
        assertEquals(25, summary.getTotalSavings(), 0.001);

    }
    @Test
    void removeSavings_removeTotalSavings_expectZero() throws BudgetTrackerException {
        Summary summary = new Summary();
        summary.addIncome(5000);
        summary.addSavings(200);
        summary.removeSavings(200);
        assertEquals(0, summary.getTotalSavings(), 0.001);

    }

    @Test
    void getTotalIncome_emptySummary_expectZero() {
        Summary summary = new Summary();
        assertEquals(0, summary.getTotalIncome(), 0.001);
    }

    @Test
    void getTotalExpense_emptySummary_expectZero() {
        Summary summary = new Summary();
        assertEquals(0, summary.getTotalExpense(), 0.001);
    }

    @Test
    void getTotalSavings_emptySummary_expectZero() {
        Summary summary = new Summary();
        assertEquals(0, summary.getTotalSavings(), 0.001);
    }

}
