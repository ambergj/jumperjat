package resources;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ChatroomTest {
    static Chatroom chatroom;
    static User tester1 = new User("TesterOneName", null);
    static User tester2 = new User("TesterTwoName", null);
    static User tester1equ = new User("TesterOneName", null);
    static Message message;

    @BeforeEach
    public void setupChatroom(){
        chatroom = new Chatroom("Chatroom", tester1);

        String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        message = new Message("Chatroom", "Author", dateTime,"Supertoole Message");
    }

    @Test
    void addUsers() {
        assertTrue(chatroom.addUsers(tester2));
    }

    @Test
    void addDublicateUsers() {
        assertFalse(chatroom.addUsers(tester1equ));
    }

    @Test
    void removeUser() {
        assertTrue(chatroom.removeUser(tester1));
        assertTrue(chatroom.getUserList().isEmpty());
    }

    @Test
    void addMessage() {
        chatroom.addMessage(message);
        assertNotNull(chatroom.getMessageHistory());
    }

    @Test
    void getUserList() {
        assertNotNull(chatroom.getUserList());
    }

    @Test
    void getName() {
        assertEquals("Chatroom", chatroom.getName());
    }
}