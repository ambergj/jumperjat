package resources;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyOutStream extends ObjectOutputStream implements Serializable {

    protected MyOutStream() throws IOException, SecurityException {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public MyOutStream(OutputStream out) throws IOException {
        super(out);
    }

}
