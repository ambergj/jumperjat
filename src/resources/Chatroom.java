package resources;

import java.util.ArrayList;

/**
 * The Chatroom as resource to save Users and Messages.
 *
 * @author ambergj
 * @version 2.0
 * @since 1.8.0
 */
public class Chatroom {
    private String name;
    // ArrayList<User> or ArrayList<String>
    // Maybe Map instead of ArrayList for no dublicates
    private ArrayList<User> userList;
    private ArrayList<Message> messageHistory;

    /**
     * chechs if a user is in this Instancechat.
     *
     * @param firstUser
     * @return boolean
     */
    private boolean userInChat(User firstUser){
        String firstUserName = firstUser.getUsername();
        for(int i = 0; i < userList.size(); i++){
            User secondUser = userList.get(i);
            String secondUserName = secondUser.getUsername();
            if(firstUserName.equals(secondUserName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the User isn't already in the List and adds the User.
     *
     * @param inputUsers Array of Users to input in Chat
     */
    public void addUsers(ArrayList<User> inputUsers){
        for(int i = 0; i < inputUsers.size(); i++){
            User movingUser = inputUsers.get(i);
            if(!userInChat(movingUser)){
                userList.add(movingUser);
            }
        }
    }

    /**
     * removes the User if there is a User with the same Username
     * @param firstUser specify User for deletion
     * @return true if User is deleted
     */
    public boolean removeUser(User firstUser){
        String firstUserName = firstUser.getUsername();
        for(int i = 0; i < userList.size(); i++){
            User secondUser = userList.get(i);
            String secondUserName = secondUser.getUsername();
            if(firstUserName.equals(secondUserName)){
                userList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a Message to the messageHistory
     * @param message to add
     */
    public void addMessage(Message message){
        messageHistory.add(message);
    }
}
