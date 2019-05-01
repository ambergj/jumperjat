package resources;

import java.io.Serializable;
import java.io.ObjectOutputStream;

/**
 * A Class that represents a User with an unique id as String and and IP-address.
 *
 * @author ambergj
 * @version 2.0
 * @since 1.8.0
 */
public class User implements Serializable{

    private static int counter = 0;
    private int id;
    private String username;
    private ObjectOutputStream outStream;

    /**
     * Constructs User with the params.
     * @param username
     * @param ipAddress
     */
    public User(String username, ObjectOutputStream outStream){
        /*
        //eventual checking of Objects and throwing exception if already existing with that username
        //Probably much better to do it in Parent Class
        if (username == null || ipAddress == null) {
            throw new NullPointerException("No Username or Ip Address specified.");
        }
        for(int i = 0; i < userList.size(); i++){
            if (User in List){
                throw new IllegalArgumentException("User with that Username already exists.");
            }
        }
        */

        this.username = username;
        this.outStream = outStream;
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public ObjectOutputStream getOutStream() {
        return outStream;
    }
}
