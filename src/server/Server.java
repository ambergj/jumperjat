package server;

import resources.*;
import serverView.ServerMainController;
import serverView.StartServerController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.net.*;
import java.io.*;

import java.util.ArrayList;

public class Server extends Application implements ReceiverProtocol {
    private ArrayList<Chatroom> chatroomsList = new ArrayList<>();
    private ArrayList<User> usersList = new ArrayList<>();
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            LoaderContainer<StartServerController> lc = LoaderContainer.loadUI(this, "/serverView/startServer.fxml", StartServerController.class);
            Parent root = lc.getRoot();
            StartServerController ctrlStartServer = lc.getCtrl();
            ctrlStartServer.setServer(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        } catch (Exception e) {
            //TODO handle exceptions
            e.printStackTrace();
        }
    }
    
    public void launchServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            LoaderContainer<ServerMainController> lc = LoaderContainer.loadUI(this,  "/serverView/serverMain.fxml",  ServerMainController.class);
            Parent root = lc.getRoot();
            ServerMainController ctrlServerMain = lc.getCtrl();
            ctrlServerMain.setServer(this);
            primaryStage.setScene(new Scene(root));
            //TODO change 'while true' to 'while not stopped'
            while(true) {
                clientSocket = serverSocket.accept();
                ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
                
                Thread t = new ClientHandler(clientSocket, inStream, outStream);
                t.start();
            }
        } catch (IOException e) {
            //TODO handle exception
            e.printStackTrace();
        } catch (Exception e) {
            //TODO handle exceptions
            e.printStackTrace();
        }
    }

    @Override
    public void receiveProtocol(Protocol protocol) {
        //TODO split Protocol in components and safe them in a variable

        //TODO read out payload and redistribute it via createProtocol method.
        switch (protocol){
            case JoinUser:
                createUser(protocol.getName(), protocol.getIpAddress());
                break;

            case CreateChatroom:
                createChatroom(protocol.getName(), protocol.getUserList);
                break;

            case DistributeChatroom:
                chatroom = protocol.getChatroom();
                distributeChatroom(protocol.getUser(), chatroom);
                break;

            case DistributeMessage:
                distributeMessage(Message(protocol.getMessage));
                break;

            case SENDING:
                distributeMessage(chatroomID, message);
                break;

            case DISTRIBUTECHATROOM:
                break;

            case NEWUSER:
                break;
        }
    }

    public void createUser(String name, String ipAddress){
        User newUser = new User(name, ipAddress);
        usersList.add(newUser);
    }

    public void createChatroom(String name, ArrayList<User> userList){
        Chatroom chatroom = new Chatroom(name, userList);
        chatroomsList.add(chatroom);
    }

    public void distributeMessage(int chatroomID, Message message){
        Chatroom chatroom = chatroomsList.get(chatroomID);
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            createProtocol(null , user, "NewChatroom", chatroom);
        }
    }

    public void distributeMessage(User admin, Chatroom chatroom){
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            createProtocol(null , user, "NewChatroom", chatroom);
        }
    }

    public void createProtocol(User sender, User target, String Action, Object payload){
        Protocol protocol = new Protocol();
        sendProtocol(protocol);
    }

    public void sendProtocol(Protocol protocol){
        //TODO Sends Protocol to target IP given in Protocol
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}