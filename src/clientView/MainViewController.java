package clientView;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import resources.Chatroom;
import resources.Message;

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
    private Chatroom chatroom;

    @FXML
    private Button btnAddChatroom;
    
    @FXML
    private Button btnCloseChatroom;
    
    @FXML
    private TextField tfChatInput;
    
    @FXML
    private TabPane tpChatrooms;
    
    @FXML
    private Tab tabMain;
    
    @FXML
    private VBox vboxChat;
    
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
    
    //TODO JAVADOC!!!
    /**
     * This method serves to 
     * @param chatroom
     */
    public void init(Chatroom chatroom) {
        this.chatroom = chatroom;
        tabMain.setText(chatroom.getName());
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
    
    /**
     * calls the client method to display the input
     * @param msg new Message
     */
    public void displayNewMessage(Message msg) {
        
        Label newLabel = new Label(msg.getMessageText());
        vboxChat.getChildren().add(newLabel);
        
    }
    
    /**
     * This method is run when the user clicks on the Send-Button.
     * It retrieves the message text from the TextField and sends
     * it to the 'sendMessage()'-Method of the client.
     */
    @FXML
    private void send() {
        String message = tfChatInput.getText();
        if(!message.contentEquals("")) {
            client.sendMessage(message, Integer.toString(chatroom.getId()));
        }
    }
}
