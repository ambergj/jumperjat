package server;

import resources.*;

import java.net.*;
import java.io.*;

import javafx.scene.control.Alert;


public class ClientHandler extends Thread{

    private Server server;
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    
    public ClientHandler(Server server, Socket clientSocket, ObjectInputStream inStream, MyOutStream outStream) {
        this.server = server;
        this.clientSocket = clientSocket;
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
