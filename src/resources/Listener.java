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
    private MyOutStream outStream;
    
    public Listener(ReceiverProtocol subscriber, ObjectInputStream inStream, MyOutStream outStream) {
        this.subscriber = subscriber;
        this.inStream = inStream;
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
                //TODO fix
                subscriber.receiveProtocol(protocol, outStream);

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
