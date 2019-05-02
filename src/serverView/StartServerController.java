package serverView;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import server.*;

/**
 * Controller for the 'startServer.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class StartServerController {
    
    private Server server;
    
    @FXML
    private TextField txtPort;
    
    @FXML
    private Button btnLaunch;
    
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
     * This method is launched when the Launch-Button on the UI is clicked.
     * It calls the next startup method on the server.
     */
    @FXML
    private void launch() {
        int port = Integer.parseInt(txtPort.getText());
        server.launchServer(port);
    }
}
