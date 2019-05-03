package resources;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

import server.Server;

import client.Client;

/**
 * When the Thread is started, the object constantly listens on the given InputStream for
 * incoming Protocol objects, which are immediately transfered to the subscribing Server or Client
 * by calling its 'receiveProtocol' method.
 *
 * @author luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class Listener extends Thread{
    
    private ReceiverProtocol subscriber;
    private Protocol protocol;
    private ObjectInputStream inStream;
    private MyOutStream outStream;
    private Socket clientSocket;
    private boolean run = true;
    
    public Listener(ReceiverProtocol subscriber, Socket clientSocket, ObjectInputStream inStream, MyOutStream outStream) {
        this.clientSocket = clientSocket;
        this.subscriber = subscriber;
        this.inStream = inStream;
        this.outStream = outStream;
    }

    /**
     * Run method for thread with runnable
     */
    @Override
    public void run() {
        //Internal Runnable to get the GUI-Update run on the GUI-Thread
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                subscriber.receiveProtocol(protocol, outStream);
            }
        };


        //While true: keep listening
        //TODO change 'while true' to 'while not isInterrupted()'
        while(run) {
            try {
                protocol = (Protocol)inStream.readObject();
                    Platform.setImplicitExit(false);
                    Platform.runLater(updater);
                
            } catch (ClassNotFoundException e) {
                    Platform.setImplicitExit(false);
                    Platform.runLater(() -> subscriber.alertError("Das Empfangen des Objektes ist fehlgeschlagen!" , e.getMessage()));
            } catch (IOException e) {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    //do nothing
                } finally {
                    run = false;
//                    this.stop();
                }
            }
        }
    }
}
