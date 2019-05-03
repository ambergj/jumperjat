package resources;

import java.io.Serializable;

/**
 * A Class that defines the Protocol for the Chat.
 * It is equipped with getters.
 *
 * @author
 * @version 2.0
 * @since 1.8.0
 */
public class Protocol implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2787184537583477608L;
    private ProtocolType action;
    private User sender;
    private User reciever;
    //payload[String text][User user][int chatroomID][Chatroom chatroom][Message message]
    private Object[] payload = new Object[6];

    /**
     * Constructor for Protocol Object.
     *
     * @param action Protocol Type that should be executed
     * @param sender who Sent the Message
     * @param reciever who should recieve the message.
     * @param text Text Payload, Username, Error Message
     * @param outStream Output Stream, to Send to
     * @param user That is created by the Server
     * @param chatroomID For the Chatroom of The action: Join/Distribute/Leave
     * @param chatroom new Chatroom Object
     * @param message new Message Object
     */
    public Protocol(ProtocolType action, User sender, User reciever,
                    String text, MyOutStream outStream, User user, Integer chatroomID, Chatroom chatroom, Message message){
        this.action = action;
        this.sender = sender;
        this.reciever = reciever;
        this.payload[0] = text;
        this.payload[1] = outStream;
        this.payload[2] = user;
        this.payload[3] = chatroomID;
        this.payload[4] = chatroom;
        this.payload[5] = message;
    }

    public ProtocolType getAction() {
        return action;
    }

    public User getSender() {
        return sender;
    }

    public User getReciever() {
        return reciever;
    }

    public String getPayloadText() {
        return (String) payload[0];
    }

    public MyOutStream getPayloadOutStream() {
        return (MyOutStream) payload[1];
    }

    public User getPayloadUser() {
        return (User) payload[2];
    }

    public int getPayloadChatroomId() {
        return (int) payload[3];
    }

    public Chatroom getPayloadChatroom() {
        return (Chatroom) payload[4];
    }

    public Message getPayloadMessage() {
        return (Message) payload[5];
    }

    public Object getPayload(int pointer) {
        return payload[pointer];
    }
}
