package resources;

import java.io.ObjectOutputStream;

/**
 * The ReceiverProtocol-Interface defines objects, that provide a method
 * to compute a Protocol-Object
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public interface ReceiverProtocol {
    
    /**
     * The receiveProtocol-Method serves for computing a received
     * Protocol-Object and act accordingly.
     * 
     * @param p incoming Protocol
     * @param outStream Output-Stream to send the response to
     */
    public void receiveProtocol(Protocol p, ObjectOutputStream outStream);
}
