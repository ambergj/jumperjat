package server;

import java.net.*;
import java.io.*;

public class ClientHandler extends Thread{

    private Socket clientSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    
    public ClientHandler(Socket clientSocket, ObjectInputStream inStream, ObjectOutputStream outStream) {
        this.clientSocket = clientSocket;
        this.inStream = inStream;
        this.outStream = outStream;
    }
    
    @Override
    public void run() {
        //TODO listen on port
        System.out.println("Server is listening!");
    }
    
}
