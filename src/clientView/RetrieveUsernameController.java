package clientView;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the 'retrieveUsername.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class RetrieveUsernameController {
    
    private Client client;
    
    @FXML
    private Button btnCreateUser;
    
    @FXML
    private TextField tfUsername;
    
    /**
     * This method receives the client, which was loading this controller
     * and stores it locally.
     * The client object can later be used to hand controll back to the client.
     * 
     * @param client Client, which loaded this controller
     */
    public void setClient(Client client) {
        this.client = client;
    }
    
    /**
     * This method is launched when the Create-Button on the UI is clicked.
     * It calls the next startup method on the client.
     */
    @FXML
    private void createUser() {
        String username = tfUsername.getText();
        client.requestUsername(username);
    }
}
