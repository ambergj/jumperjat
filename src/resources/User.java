package resources;

/**
 * A Class that represents a User with an unique id as String and and IP-address.
 *
 * @author ambergj
 * @version 2.0
 * @since 1.8.0
 */
public class User {
    private String username;
    private String ipAddress;

    /**
     * Constructs User with the params.
     * @param username
     * @param ipAddress
     */
    public User(String username, String ipAddress){
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
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
