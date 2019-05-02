package clientView;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainViewController implements Initializable {
    
    private Client client;

    @FXML
    private Button btnAddChatroom;
    
    @FXML
    private Button btnCloseChatroom;
    
    @FXML
    private TabPane tpChatrooms;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
}
