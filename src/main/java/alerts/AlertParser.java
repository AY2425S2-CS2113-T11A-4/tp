package alerts;

import commands.AlertCommand;
import commands.Command;
import exceptions.BudgetTrackerException;

/**
 * Parser for alert-related commands.
 */
public class AlertParser {
    
    /**
     * Parses alert commands and creates appropriate Command objects.
     * 
     * @param fullCommand The full command string
     * @param fundsAlert The funds alert instance to interact with
     * @return A Command object corresponding to the parsed command
     * @throws BudgetTrackerException If the command format is invalid
     */
    public static Command parse(String fullCommand, FundsAlert fundsAlert) throws BudgetTrackerException {
        String[] parts = fullCommand.split(" ", 3);
        
        if (parts.length < 3) {
            throw new BudgetTrackerException("Invalid alert command format. Use: alert set <amount>");
        }
        
        if (parts[0].equalsIgnoreCase("alert") && parts[1].equalsIgnoreCase("set")) {
            try {
                double threshold = Double.parseDouble(parts[2]);
                return new AlertCommand(threshold, fundsAlert);
            } catch (NumberFormatException e) {
                throw new BudgetTrackerException("Please provide a valid number for the threshold.");
            }
        }
        
        throw new BudgetTrackerException("Unrecognized alert command: " + fullCommand);
    }
}
