package clientView;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RetrieveUsernameController {
    
    private Client client;
    
    @FXML
    private Button btnCreateUser;
    
    @FXML
    private TextField tfUsername;
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @FXML
    private void createUser() {
        String username = tfUsername.getText();
        client.requestUsername(username);
    }
}
