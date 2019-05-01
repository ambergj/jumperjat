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
//        connectToServer(primaryStage);
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientView/clientMain.fxml"));
//        try {
//            Parent root = loader.load();
//            MainViewController ctrl = loader.getController();
//        } catch (IOException e) {
//            //TODO handle Exceptions
//            e.printStackTrace();
//        }
    }
    
    public void connectToServer(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            LoaderContainer<RetrieveUsernameController> lc = LoaderContainer.loadUI(this, "/clientView/retrieveUsername.fxml", RetrieveUsernameController.class);
            Parent root = lc.getRoot();
            RetrieveUsernameController ctrlRetrieveUsername = lc.getCtrl();
            primaryStage.setScene(new Scene(root));
            
        } catch (Exception e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    @Override
    public void receiveProtocol(Protocol protocol) {
        //TODO Logik einfügen
    }
    
    public static void main(String args[]) {
        Application.launch(args);
    }
}
