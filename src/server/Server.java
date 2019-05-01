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

import static resources.ProtocolType.*;

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
                
                Thread t = new ClientHandler(this, clientSocket, inStream, outStream);
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
    public void receiveProtocol(Protocol protocol, ObjectOutputStream outStream) {
        Protocol answer;
        System.out.println("Ich bin jetzt hier!!!!");
        switch (protocol.getAction()){
            case CREATEUSER:
                String name = protocol.getPayloadText();
                String ipAddress = protocol.getPayloadIpAddress();
                User user = new User(name , ipAddress);
                if(user.equals(null)){
                    answer = new Protocol(ERRORUSER, null, null, "Username already in use.", null, null, null, null, null);
                }
                else{
                    Object[] payload = {null, null, user, null, null, null};
                    answer = new Protocol(CONFIRMUSER, null, user, null, null, user, null, null, null);
                }
                outStream.writeObject(answer);
//                answer.send(ipAddress);
                break;

            case JOINCHATROOM:
                //TODO Check if Chatroom exists: join or create
                createChatroom(protocol.getName(), protocol.getUserList);
                break;

            //not used
            case DISTRIBUTECHATROOM:
                // Send new created Chatroom, not Used in this implementation
                chatroom = protocol.getChatroom();
                distributeChatroom(protocol.getUser(), chatroom);
                break;

            case DISTRIBUTEMESSAGE:
                //TODO Send Message to Chatroom members, with distributeMessage methode
                distributeMessage(Message(protocol.getMessage));
                break;

            case LEAVECHATROOM:
                break;

            default:
                //return error
                break;
        }
    }

    public int getUserId(User user){
        for(int i = 0; i < usersList.size(); i++){
            User testuser = usersList.get(i);
            if(user.equals(testuser)){
                return i;
            }
        }
        return -1;
    }

    public boolean userExists(User user){
        for(int i = 0; i < usersList.size(); i++){
            User testuser = usersList.get(i);
            if(user.equals(testuser)){
                return true;
            }
        }
        return false;
    }

    public User createUser(String name, String ipAddress){
        for(int i = 0; i < usersList.size(); i++){
            User testuser = usersList.get(i);
            String  testname = testuser.getUsername();
            if(testname.equals(name)){
                return null;
            }
        }
        User newUser = new User(name, ipAddress);
        usersList.add(newUser);
        return newUser;
    }

    public void createChatroom(String name, ArrayList<User> userList){
        Chatroom chatroom = new Chatroom(name, userList);
        chatroomsList.add(chatroom);
    }

    // better Version maybe
    public void distributeMessage(int chatroomID, Message message){
        Chatroom chatroom = chatroomsList.get(chatroomID);
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            Protocol answer = new Protocol (null , user, "NewChatroom", chatroom);
        }
    }

    public void distributeMessage(User admin, Chatroom chatroom){
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            Protocol answer = new Protocol (null , user, "NewChatroom", chatroom);
        }
    }

    public void sendProtocol(Protocol protocol, String ip){
        //TODO Sends Protocol to target IP given in Protocol
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}