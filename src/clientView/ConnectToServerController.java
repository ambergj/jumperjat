package clientView;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the 'connectToServer.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class ConnectToServerController {
    
    private Client client;
    
    @FXML
    private Button btnConnect;
    
    @FXML
    private TextField tfServerIP;
    
    @FXML
    private TextField tfServerPort;
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    /**
     * This method is launched when the Connect-Button on the UI is clicked.
     * It calls the next startup method on the client.
     */
    @FXML
    private void connect() {
        String ipAddress = tfServerIP.getText();
        int portNumber = Integer.parseInt(tfServerPort.getText());
        client.connectToServer(ipAddress, portNumber);
    }
}
