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

/**
 * The Server-Class represents an instance of a running Server-Program
 * It is the main class for a server; Only one instance per server is needed.
 * 
 * @author ambergj, luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class Server extends Application implements ReceiverProtocol {
    
    /**
     * Main method of the server to launch the server
     * 
     * @param args Standard Input
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    private ArrayList<Chatroom> chatroomsList = new ArrayList<>();
    private ArrayList<User> usersList = new ArrayList<>();

    private boolean acceptConnections = true;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Stage primaryStage;
    
    /**
     * This method launches the first UI-Layout, where a user enters the port
     * the server should listen on.
     * 
     * @param primaryStage main stage for the application
     */
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
    
    /**
     * This method is started by the first server UI. It opens a constantly listening socket,
     * loads a second UI and propagates incoming requests to individual thread objects.
     * 
     * @param port Port number the server listens on
     */
    public void launchServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            LoaderContainer<ServerMainController> lc = LoaderContainer.loadUI(this,  "/serverView/serverMain.fxml",  ServerMainController.class);
            Parent root = lc.getRoot();
            ServerMainController ctrlServerMain = lc.getCtrl();
            ctrlServerMain.setServer(this);
            primaryStage.setScene(new Scene(root));
            while(acceptConnections) {
                clientSocket = serverSocket.accept();
                MyOutStream outStream = new MyOutStream(clientSocket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
                Thread t = new Listener(this, inStream, outStream);
                t.start();
            }
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }
    
    /**
     * Implementation of the interface method to process client requests
     * 
     * @param protocol Protocol sent from client
     * @param outStream Output stream to send back answers if needed
     */
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
                if(user == null){
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
    
    /**
     * This method returns the user id, if the requested user object allready exists,
     * -1 otherwise.
     * 
     * @param user The user object to be tested
     * @return the user id or -1
     */
    public int getUserId(User user){
        for(int i = 0; i < usersList.size(); i++){
            User testuser = usersList.get(i);
            if(user.equals(testuser)){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * This method tests if the user object already exists
     * 
     * @param user The user object to be tested
     * @return true if the user object exists
     */
    public boolean userExists(User user){
        for (User testuser : usersList) {
            if (user.equals(testuser)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method is used to create new user objects. It first checks if the requested
     * username is already in use or not.
     * 
     * @param name desired username
     * @param outStream Output-Stream, on which replies can be sent back to the user
     * @return the newly created user object
     */
    public User createUser(String name, MyOutStream outStream){
        for (User testuser : usersList) {
            String testname = testuser.getUsername();
            if (testname.equals(name)) {
                return null;
            }
        }
        User newUser = new User(name, outStream);
        usersList.add(newUser);
        return newUser;
    }
    
    /**
     * This method checks whether a chatroom with the requested name already exists or not
     * 
     * @param chatroomName name of the requested chatroom
     * @return true if there is already a chatroom using that name
     */
    public boolean chatroomExists(String chatroomName){
        for (Chatroom testChatroom : chatroomsList) {
            String testName = testChatroom.getName();
            if (testName.equals(chatroomName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method returns a chatroom object out of the chatroom list based on its name
     * 
     * @param chatroomName name of the requested chatroom
     * @return the requested chatroom object
     */
    public Chatroom getChatroom(String chatroomName){
        for (Chatroom testChatroom : chatroomsList) {
            String testName = testChatroom.getName();
            if (testName.equals(chatroomName)) {
                return testChatroom;
            }
        }
        return null;
    }
    
    /**
     * This method creates a new chatroom and directly adds the user who requested
     * this new chatroom.
     * 
     * @param name Name of the new chatroom
     * @param newUser user who requested the new chatroom
     */
    public void createChatroom(String name, User newUser){
        Chatroom chatroom = new Chatroom(name, newUser);
        chatroomsList.add(chatroom);
    }

    /**
     * This method distributes a new message to all members of a chatroom
     * 
     * @param chatroomID chatroom id in which a new message was created
     * @param message the new message
     */
    public void distributeMessage(int chatroomID, Message message){
        Chatroom chatroom = chatroomsList.get(chatroomID);
        ArrayList<User> users = chatroom.getUserList();
        for (User user : users) {
            Protocol answer = new Protocol (DISTRIBUTEMESSAGE, null, user, null, null, null, null, chatroom, message);
            //TODO send the protocol object
        }
    }
    
    /**
     * This method adds a user to an existing chatroom
     * 
     * @param newUser User who wants to join an existing chatroom
     */
    private void joinChatroom(User newUser) {
        //TODO
    }

    public void setAcceptConnections(boolean acceptConnections) {
        this.acceptConnections = acceptConnections;
    }
}