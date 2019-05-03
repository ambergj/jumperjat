package resources;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class contains helpful and useful util methods (static),
 * which can be called from a server or client instance.
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class Utils {
    
    /**
     * This method displays an alert box to inform the user about
     * occurred errors
     * 
     * @param errMessage Information about the error
     */
    public static void alertError(String information, String errMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        if(information != null) {
            alert.setHeaderText(information);
        } else {
            alert.setHeaderText("An Error occurred");
        }
        alert.setContentText(errMessage);
        alert.showAndWait();
    }
}
