package server;

import resources.*;

import java.net.*;
import java.io.*;

import javafx.scene.control.Alert;

/**
 * The ClientHandler class provides Thread objects which are run on the client
 * and constantly listen to incoming packets from the server and transmit them to the client.
 * 
 * @author luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class ClientHandler extends Thread{

    private Server server;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    
    public ClientHandler(Server server, ObjectInputStream inStream, MyOutStream outStream) {
        this.server = server;
        this.inStream = inStream;
        this.outStream = outStream;
    }
    
    @Override
    public void run() {
        //TODO change 'while true' to 'while not interrupted'
        while(true) {
            try {
                Protocol request = (Protocol)inStream.readObject();
                server.receiveProtocol(request, outStream);
            } catch (Exception e) {
                //TODO handle exceptions
                e.printStackTrace();
            }
        }
    }
}
