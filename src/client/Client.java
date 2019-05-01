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

public class Client extends Application implements ReceiverProtocol {
    
    private Stage primaryStage;
    private String serverIP;
    
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    
    private User me;
    
    /**
     * This method launches the first UI-Layout, where a User enters the
     * IP-Address and Port of the Server
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
    
    public void requestUsername(String username) {
        //Protokoll-Objekt erstellen
        String ip = null;
        try{
            //get local ip address:
            final DatagramSocket socket = new DatagramSocket();
            //find out, which adapter is able to connect to internet (8.8.8.8)
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            //retrieve ip address
            ip = socket.getLocalAddress().getHostAddress();
            socket.close();
        } catch (Exception e) {
            //TODO handle exceptions
            ip = "";
            e.printStackTrace();
        }
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
    
    public static void main(String args[]) {
        Application.launch(args);
    }
}
