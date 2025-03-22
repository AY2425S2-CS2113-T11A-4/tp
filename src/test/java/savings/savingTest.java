package savings;

import exceptions.BudgetTrackerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import summary.Summary;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SavingTest {
    private Saving saving;
    private Summary summary;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws BudgetTrackerException {
        summary = new Summary();
        saving = new Saving(summary);

        summary.addIncome(100.0);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testAddSavings() throws BudgetTrackerException {
        saving.addSavings(100.0);
        assertTrue(outputStreamCaptor.toString().contains("Sure! I have added your savings:"));
    }

    @Test
    void testDeleteSavings() throws BudgetTrackerException {
        saving.addSavings(100.0);
        saving.deleteSavings(1);
        assertTrue(outputStreamCaptor.toString().contains("Sure! I have deleted the saving:"));
    }

    @Test
    void testViewSavings() throws BudgetTrackerException {
        saving.addSavings(100.0);
        outputStreamCaptor.reset();
        saving.viewSavings();
        assertTrue(outputStreamCaptor.toString().contains("Here are the savings in your list:"));
    }

    @Test
    void testSetSavingsGoal() throws BudgetTrackerException {
        saving.addSavings(100.0);
        saving.setSavingsGoal(100.0, "Vacation");
        assertTrue(outputStreamCaptor.toString().contains("I have set your saving goal:"));
    }

    @Test
    void testUpdateSavingsGoal() throws BudgetTrackerException {
        saving.addSavings(100.0);
        saving.updateSavingsGoal(0, 150.0, "New Goal");
        assertTrue(outputStreamCaptor.toString().contains("I have updated your saving amount and saving goal:"));
    }

    @Test
    void testDeleteSavingsGoal() throws BudgetTrackerException {
        saving.addSavings(100.0);
        saving.setSavingsGoal(100.0, "Vacation");
        saving.deleteSavingsGoal(0);
        assertTrue(outputStreamCaptor.toString().contains("I have deleted the saving goal:"));
    }

    @Test
    void testTransferSavings() throws BudgetTrackerException {
        saving.addSavings(200.0);
        saving.addSavings(100.0);

        outputStreamCaptor.reset(); // 重置输出流
        saving.transferSavings(1, 2, 50.0);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Transferred 50.0 from savings 1 to savings 2."));
        assertTrue(output.contains("Updated records:"));
    }

}
