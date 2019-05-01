package clientView;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainViewController {
    
    private Client client;

    @FXML
    private Button btnAddChatroom;
    
    @FXML
    private Button btnCloseChatroom;
    
    @FXML
    private TabPane tpChatrooms;
    
    public void setClient(Client client) {
        this.client = client;
    }
}
