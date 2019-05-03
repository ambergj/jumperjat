package client;

import resources.*;
import clientView.*;

import java.net.*;
import java.time.LocalDateTime;
import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Client-Class represents an instance of a running Client-Program
 * It is the main class for a Client; Only one instance per client is needed.
 * 
 * @author ambergj, luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class Client extends Application implements ReceiverProtocol {
    
    /**
     * Main method of the client to launch the client
     * @param args Standard Input
     */
    public static void main(String args[]) {
        Application.launch(args);
    }
    
    private Stage primaryStage;
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    private User me;
    
    private MainViewController mainViewController;
    private Chatroom myChatroom = null;
    
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
            Utils.alertError("Das GUI konnte nicht geladen werden!", e.getMessage());
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
            
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            Utils.alertError("Das GUI konnte nicht geladen werden!", e.getMessage());
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
            receiveProtocol(answer, null);
        } catch (IOException | ClassNotFoundException e) {
            Utils.alertError("Das Erstellen eines neuen Users ist fehlgeschlagen!", e.getMessage());
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
            receiveProtocol(answer, outStream);
        } catch (IOException | ClassNotFoundException e) {
            Utils.alertError("Das Laden des gegebenen Chatrooms ist fehlgeschlagen!" , e.getMessage());
        }
    }
    
    /**
     * This method serves to send the newly written message
     * to the server (and let it distribute the new message)
     * 
     * @param message Message to be sent to the server
     */
    public void sendMessage(String message, String chatroomID) {
        Message msg = new Message(chatroomID, me.getUsername(), LocalDateTime.now(), message);
        Protocol protocol = new Protocol(ProtocolType.DISTRIBUTEMESSAGE, me, null, null, null, null, null, null, msg);
        try {
            outStream.writeObject(protocol);
        } catch (IOException e) {
            Utils.alertError("Das Versenden der Nachricht ist fehlgeschlagen!", e.getMessage());
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
                }catch (IOException | IllegalAccessException | InstantiationException e){
                    Utils.alertError("Das GUI konnte nicht geladen werden!" , e.getMessage());
                }
                break;
            case ERRORUSER:
                //TODO handle user already exists
                break;
            case DISTRIBUTECHATROOM:
                Chatroom newChatroom = protocol.getPayloadChatroom();
                if(this.myChatroom != null && this.myChatroom.getId() == newChatroom.getId()) {
                    Message msg = new Message(Integer.toString(newChatroom.getId()), "Server", LocalDateTime.now(), "New User joined");
                    mainViewController.displayNewMessage(msg);
                } else {
                    this.myChatroom = newChatroom;
                    try {
                        LoaderContainer<MainViewController> lc = LoaderContainer.loadUI(this, "/clientView/clientMain.fxml", MainViewController.class);
                        Parent root = lc.getRoot();
                        this.mainViewController = lc.getCtrl();
                        mainViewController.setClient(this);
                        mainViewController.init(newChatroom);
                        primaryStage.setScene(new Scene(root));
                        Thread t = new Listener(this, clientSocket, inStream, outStream);
                        t.setDaemon(true);
                        t.start();
                    } catch (IOException | IllegalAccessException | InstantiationException e) {
                        Utils.alertError("Das GUI konnte nicht geladen werden!" , e.getMessage());
                    }
                }
                break;
            case DISTRIBUTEMESSAGE:
                Message msg = protocol.getPayloadMessage();
                mainViewController.displayNewMessage(msg);
                break;
            case LEAVECHATROOM:
                break;
            default:
                break;
        }
    }
    
    /**
     * This method exsists so that the Listener Thread is able to display
     * error messages. It calls the corresponding utils method
     * 
     * @param information Information
     * @param errMessage Error Message
     */
    public void alertError(String information, String errMessage) {
        Utils.alertError(information, errMessage);
    }
}
