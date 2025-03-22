package expenses;

public class Ui {

    public void showMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Displays an alert message with emphasis.
     * 
     * @param alertMessage The alert message to display
     */
    public void showAlert(String alertMessage) {
        System.out.println("\n====== ALERT ======");
        System.out.println(alertMessage);
        System.out.println("===================\n");
    }
}
