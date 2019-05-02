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
    private MyOutStream outStream;

    /**
     * Constructs User with the params.
     * @param username
     * @param ipAddress
     */
    public User(String username, MyOutStream outStream){
        //TODO Was ist mit dem folgenden Block
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
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        
        if(o == null) {
            return false;
        }
        
        if(this.getClass() != o.getClass()) {
            return false;
        }
        User other = (User) o;
        if(this.getUsername().equals(other.getUsername())){
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public MyOutStream getOutStream() {
        return outStream;
    }
}
