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
                MyOutStream outStream = new MyOutStream(clientSocket.getOutputStream());
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
    public void receiveProtocol(Protocol protocol, MyOutStream outStream) {
        Protocol answer;
        User user;
        switch (protocol.getAction()){
            case CREATEUSER:
                String name = protocol.getPayloadText();
//                MyOutStream userOutStream = protocol.getPayloadOutStream();
                user = createUser(name , outStream);
                //TODO if test is always false
                if(user.equals(null)){
                    answer = new Protocol(ERRORUSER, null, null, "Username already in use.", null, null, null, null, null);
                }
                else{
                    answer = new Protocol(CONFIRMUSER, null, user, null, null, user, null, null, null);
                }
                try {
                    outStream.writeObject(answer);
                    
                } catch (Exception e) {
                    //TODO handle exception
                    e.printStackTrace();
                }
//                answer.send(ipAddress);
                break;

            case JOINCHATROOM:
                User sender = protocol.getSender();
                int id = getUserId(sender);
                String chatroomName = protocol.getPayloadText();
                User newUser = usersList.get(id);
                if(chatroomExists(chatroomName)){
                    Chatroom modifyChatroom = getChatroom(chatroomName);
                    modifyChatroom.addUsers(newUser);
                }
                else{
                    createChatroom(chatroomName, newUser);
                    getChatroom(chatroomName).addUsers(newUser);
                }
                Chatroom returnChatroom = getChatroom(chatroomName);
                answer = new Protocol(DISTRIBUTECHATROOM, null, newUser, null, null, null, null, returnChatroom, null);
                try {
                    for(User usr : returnChatroom.getUserList()) {
                        usr.getOutStream().writeObject(answer);
                    }
                } catch (Exception e) {
                    //TODO handle exception
                    e.printStackTrace();
                }
                break;

            case DISTRIBUTEMESSAGE:
                //TODO Send Message to Chatroom members, with distributeMessage method
                break;

            case LEAVECHATROOM:
                //TODO User and Chatroom
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

    public User createUser(String name, MyOutStream outStream){
        for(int i = 0; i < usersList.size(); i++){
            User testuser = usersList.get(i);
            String  testname = testuser.getUsername();
            if(testname.equals(name)){
                return null;
            }
        }
        User newUser = new User(name, outStream);
        usersList.add(newUser);
        return newUser;
    }

    public boolean chatroomExists(String chatroomName){
        for(int i = 0; i < chatroomsList.size(); i++){
            Chatroom testChatroom = chatroomsList.get(i);
            String  testName = testChatroom.getName();
            if(testName.equals(chatroomName)){
                return true;
            }
        }
        return false;
    }

    public Chatroom getChatroom(String chatroomName){
        for(int i = 0; i < chatroomsList.size(); i++){
            Chatroom testChatroom = chatroomsList.get(i);
            String  testName = testChatroom.getName();
            if(testName.equals(chatroomName)){
                return testChatroom;
            }
        }
        return null;
    }

    private void joinChatroom(User newUser) {
        //TODO
    }

    public void createChatroom(String name, User newUser){
        Chatroom chatroom = new Chatroom(name, newUser);
        chatroomsList.add(chatroom);
    }

    public void distributeMessage(int chatroomID, Message message){
        Chatroom chatroom = chatroomsList.get(chatroomID);
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            Protocol answer = new Protocol (DISTRIBUTEMESSAGE, null, user, null, null, null, null, chatroom, message);
        }
    }

    public void sendProtocol(Protocol protocol, String ip){
        //TODO Sends Protocol to target IP given in Protocol
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}