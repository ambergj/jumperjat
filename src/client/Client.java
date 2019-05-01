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
    private ObjectOutputStream outStream;
    
//    private void connectToServer(Stage primaryStage) {
//        
//    }
    
    public void retrieveUsername() {
        
    }
    
    
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
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            //load UI
            LoaderContainer<RetrieveUsernameController> lc = LoaderContainer.loadUI(this, "/clientView/retrieveUsername.fxml", RetrieveUsernameController.class);
            Parent root = lc.getRoot();
            RetrieveUsernameController ctrlRetrieveUsername = lc.getCtrl();
            primaryStage.setScene(new Scene(root));
            
        } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    public void requestUsername(String username) {
        
    }
    
    @Override
    public void receiveProtocol(Protocol protocol) {
        //TODO Logik einfügen
    }
    
    public static void main(String args[]) {
        Application.launch(args);
    }
}
