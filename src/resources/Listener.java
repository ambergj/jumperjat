package resources;

import javafx.application.Platform;

import java.io.*;

/**
 * When the Thread is started, the object constantly listens on the given InputStream for
 * incoming Protocol objects, which are immediately transfered to the subscribing Server or Client
 * by calling its 'receiveProtocol' method.
 *
 * @author luescherphi
 * @version 2.0
 * @since 1.8.0
 */
public class Listener implements Runnable {
    
    private ReceiverProtocol subscriber;
    private Protocol protocol;
    private ObjectInputStream inStream;
    
    public Listener(ReceiverProtocol subscriber, ObjectInputStream inStream) {
        this.subscriber = subscriber;
        this.inStream = inStream;
    }

    /**
     * Run methode for thread with runnable
     */
    @Override
    public void run() {
        //Internal Runnable to get the GUI-Update run on the GUI-Thread
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                subscriber.receiveProtocol(protocol);
            }
        };
        
        
        //While true: keep listening
        //TODO change 'while true' to 'while not isInterrupted()'
        while(true) {
            try {
                protocol = (Protocol)inStream.readObject();
                Platform.runLater(updater);
            } catch (IOException | ClassNotFoundException e) {
                //TODO Handle Exceptions
                e.printStackTrace();
            }
        }
    }
}
