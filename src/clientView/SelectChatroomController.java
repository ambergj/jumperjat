package clientView;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SelectChatroomController {
    
    private Client client;
    
    @FXML
    private Button btnRequestChatroom;
    
    @FXML
    private TextField tfChatroom;
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @FXML
    private void joinChatroom() {
        String chatroom = tfChatroom.getText();
        client.requestChatroom(chatroom);
        //TODO implement method
    }
    
}
