package serverView;

import server.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class ServerMainController {
    
    private Server server;

    @FXML
    private Label lblContent;
    
    @FXML
    private Button btnOK;
    
    public void setServer(Server server) {
        this.server = server;
    }
    
    @FXML
    private void okMethod() {
        //TODO implement this method to do something
    }
    
}
