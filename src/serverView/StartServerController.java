package serverView;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import server.*;

public class StartServerController {
    
    private Server server;
    
    @FXML
    private TextField txtPort;
    
    @FXML
    private Button btnLaunch;
    
    public void setServer(Server server) {
        this.server = server;
    }
    
    @FXML
    private void launch() {
        int port = Integer.parseInt(txtPort.getText());
        server.launchServer(port);
    }
}
