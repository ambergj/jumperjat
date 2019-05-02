package client;

import resources.*;
import clientView.*;

import java.net.*;
import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 * The Client-Class represents an instance of a running Client-Program
 * It is the main class for a Client; Only one instance per client is needed.
 * 
 * @author ambergj, luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class Client extends Application implements ReceiverProtocol {
    
    private Stage primaryStage;
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    private User me;
    
    /**
     * This method launches the first UI-Layout, where a User enters the
     * IP-Address and Port of the Server
     * 
     * @param primaryStage main stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        FXMLLoader loadServerConnection = new FXMLLoader(getClass().getResource("/clientView/connectToServer.fxml"));
        try {
            Parent root = loadServerConnection.load();
            ConnectToServerController ctrlToServer = loadServerConnection.getController();
            ctrlToServer.setClient(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            //TODO handle Exceptions
            e.printStackTrace();
        }
    }
    
    /**
     * This is the second logic-method in the process of startup.
     * First, a connection to the server is established using
     * the given IP-Address and Port from the preceeding UI.
     * Second, the UI to retrieve the username is loaded
     * 
     * @param ip Server-IP
     * @param port Port on which the server is listening
     */
    public void connectToServer(String ip, int port) {
        try {
            //establish connection
            clientSocket = new Socket(ip, port);
            outStream = new MyOutStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            //load UI
            LoaderContainer<RetrieveUsernameController> lc = LoaderContainer.loadUI(this, "/clientView/retrieveUsername.fxml", RetrieveUsernameController.class);
            Parent root = lc.getRoot();
            RetrieveUsernameController ctrlRetrieveUsername = lc.getCtrl();
            ctrlRetrieveUsername.setClient(this);
            primaryStage.setScene(new Scene(root));
            
        } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * This is the third logic-method in the process of  client startup.
     * First, a protocol object is created to send a New-User-Request to the server,
     * second, the answer of the server is awaited and the client reacts accordingly.
     * On success, the next UI to retrieve the Chatroom name is loaded.
     * 
     * @param username the username the user would like to have
     */
    public void requestUsername(String username) {
        Protocol protocol = new Protocol(ProtocolType.CREATEUSER, null, null, username, null, null, null, null, null);
        try {
            outStream.writeObject(protocol);
            Protocol answer = (Protocol)inStream.readObject();
            switch (answer.getAction()) {
                case ERRORUSER:
                    //TODO handle user already exists 
                    break;
                case CONFIRMUSER:
                    me = answer.getPayloadUser();
                    LoaderContainer<SelectChatroomController> lc = LoaderContainer.loadUI(this, "/clientView/selectChatroom.fxml", SelectChatroomController.class);
                    Parent root = lc.getRoot();
                    SelectChatroomController ctrlSelectChatroom = lc.getCtrl();
                    ctrlSelectChatroom.setClient(this);
                    primaryStage.setScene(new Scene(root));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * This is the fourth logic-method in the process of client startup.
     * First, a new protocol object is created to request the given Chatroom from the server,
     * secont, the answer is awaited and the client reacts accordingly.
     * On Success, the new Chatroom is saved and the next UI, the main Client-UI, is loaded.
     * 
     * @param chatroom Chatroom name of the chatroom the user wants to join.
     */
    public void requestChatroom(String chatroom) {
        Protocol protocol = new Protocol(ProtocolType.JOINCHATROOM, me, null, chatroom, null, null, null, null, null);
        try {
            outStream.writeObject(protocol);
            Protocol answer = (Protocol)inStream.readObject();
            switch (answer.getAction()) {
                case DISTRIBUTECHATROOM:
                    Chatroom newChatroom = answer.getPayloadChatroom();
                    LoaderContainer<MainViewController> lc = LoaderContainer.loadUI(this, "/clientView/clientMain.fxml", MainViewController.class);
                    Parent root = lc.getRoot();
                    MainViewController ctrlMainView = lc.getCtrl();
                    ctrlMainView.setClient(this);
                    primaryStage.setScene(new Scene(root));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * Implementation of the interface method to process server messages
     * 
     * @param protocol Protocol sent from server
     * @param outStream Output stream to send back answers if needed
     */
    @Override
    public void receiveProtocol(Protocol protocol, MyOutStream outStream) {
        //TODO Logik einfügen
        switch (protocol.getAction()){
            case CONFIRMUSER:
                me = protocol.getPayloadUser();
                try{
                    LoaderContainer<SelectChatroomController> lc = LoaderContainer.loadUI(this, "/clientView/selectChatroom.fxml", SelectChatroomController.class);
                    Parent root = lc.getRoot();
                    SelectChatroomController ctrlSelectChatroom = lc.getCtrl();
                    ctrlSelectChatroom.setClient(this);
                    primaryStage.setScene(new Scene(root));
                }catch (Exception e){
                }
                break;
            case ERRORUSER:
                //TODO handle user already exists
                break;
            case DISTRIBUTECHATROOM:
                break;
            case DISTRIBUTEMESSAGE:
                break;
            case LEAVECHATROOM:
                break;
            default:
                break;

        }
    }
    
    /**
     * Main method of the client to launch the client
     * @param args Standard Input
     */
    public static void main(String args[]) {
        Application.launch(args);
    }
}
