package clientView;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the 'selectChatroom.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class SelectChatroomController {
    
    private Client client;
    
    @FXML
    private Button btnRequestChatroom;
    
    @FXML
    private TextField tfChatroom;
    
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
    
    /**
     * This method is launched when the Join-Button on the UI is clicked.
     * It calls the next startup method on the client.
     */
    @FXML
    private void joinChatroom() {
        String chatroom = tfChatroom.getText();
        client.requestChatroom(chatroom);
    }
}
