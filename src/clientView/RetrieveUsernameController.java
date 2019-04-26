package clientView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RetrieveUsernameController {
    
    @FXML
    private Button btnCreateUser;
    
    @FXML
    private TextField tfUsername;
    
    @FXML
    private void createUser() {
        String username = tfUsername.getText();
        //TODO implement method
    }
}
