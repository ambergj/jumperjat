package clientView;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
/**
 * Controller for the 'clientMain.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class MainViewController implements Initializable {
    
    private Client client;

    @FXML
    private Button btnAddChatroom;
    
    @FXML
    private Button btnCloseChatroom;
    
    @FXML
    private TabPane tpChatrooms;
    
    /**
     * This method serves to load values from the Chatroom object and display
     * depending information on GUI initialization.
     * It overrides a method from an interface.
     * 
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
     * This method receives the client, which was loading this controller
     * and stores it locally.
     * The client object can later be used to hand controll back to the client.
     * 
     * @param client Client, which loaded this controller
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
