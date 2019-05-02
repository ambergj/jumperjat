package resources;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * This class extends ObjectOutputStream. In addition, it implements Serializable,
 * which makes objects of that class able to be sendt using Object-Streams.
 * It only calls superconstructors and doesn't provide any additional logic.
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class MyOutStream extends ObjectOutputStream implements Serializable {

    protected MyOutStream() throws IOException, SecurityException {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public MyOutStream(OutputStream out) throws IOException {
        super(out);
    }

}
