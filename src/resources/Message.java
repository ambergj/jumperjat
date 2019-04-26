package resources;

import java.time.LocalDateTime;

/**
 * A Class for the Message Object. It contains the Author, Timestamp and the Message Text.
 *
 * @author ambergj
 * @version 2.0
 * @since 1.8.0
 */
public class Message {
    private String chatroomID;
    private LocalDateTime timestamp;
    private String author;
    private String messageText;

    /**
     * Creates a Message Instance with these Params.
     * @param chatroomID ID of the Chatroom to where the message belongs
     * @param author who wrote the Message
     * @param timestamp of the Message Date and Time ISO-8601
     * @param messageText of Message
     */
    public Message(String chatroomID, String author, LocalDateTime timestamp, String messageText){
        this.chatroomID = chatroomID;
        this.timestamp = timestamp;
        this.author = author;
        this.messageText = messageText;
    }

    public String getChatroomID() {
        return chatroomID;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessageText() {
        return messageText;
    }
}
