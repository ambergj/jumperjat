package serverView;

import server.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 * Controller for the 'serverMain.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class ServerMainController {
    
    private Server server;

    @FXML
    private Label lblContent;
    
    @FXML
    private Button btnOK;
    
    /**
     * This method receives the server, which was loading this controller
     * and stores it locally.
     * The server object can later be used to hand controll back to the server.
     * 
     * @param server Server, which loaded this controller
     */
    public void setServer(Server server) {
        this.server = server;
    }
    
    /**
     * This method is launched when the OK-Button on the UI is clicked.
     */
    @FXML
    private void okMethod() {
        //TODO implement this method to do something
    }
    
}
