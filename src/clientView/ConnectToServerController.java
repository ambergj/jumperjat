package clientView;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    
    @FXML
    private void connect() {
        String ipAddress = tfServerIP.getText();
        int portNumber = Integer.parseInt(tfServerPort.getText());
        client.connectToServer(ipAddress, portNumber);
    }
}
