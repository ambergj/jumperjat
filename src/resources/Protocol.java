package resources;

/**
 * A Class that defines the Protocol for the Chat.
 * It is equipped with getters.
 *
 * @author
 * @version 2.0
 * @since 1.8.0
 */
public class Protocol {
    private ProtocolType action;
    private User sender;
    private User reciever;
    //payload[String text][User user][int chatroomID][Chatroom chatroom][Message message]
    private Object[] payload = new Object[5];

    public Protocol(ProtocolType action, User sender, User reciever,
                    String text, User user, Integer chatroomID, Chatroom chatroom, Message message){
        this.action = action;
        this.sender = sender;
        this.reciever = reciever;
        this.payload[0] = text;
        this.payload[1] = user;
        this.payload[2] = chatroomID;
        this.payload[3] = chatroom;
        this.payload[4] = message;
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

    public Object getPayload(int pointer) {
        return payload[pointer];
    }
}
