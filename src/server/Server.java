package server;

import resources.*;

import java.util.ArrayList;

public class Server implements ReceiverProtocol {
    private ArrayList<Chatroom> chatroomsList = new ArrayList<>();
    private ArrayList<User> usersList = new ArrayList<>();

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
            if(!user.equals(admin)){
                createProtocol(null , user, "NewChatroom", chatroom);
            }
        }
    }

    public void createProtocol(User sender, User target, String ControllValue, Object payload){
        Protocol protocol = new Protocol();
        sendProtocol(protocol);
    }

    public void sendProtocol(Protocol protocol){
        //TODO Sends Protocol to target IP given in Protocol
    }
}