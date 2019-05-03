package serverView;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import server.*;

/**
 * Controller for the 'startServer.fxml' UI
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class StartServerController implements Initializable{
    
    private Server server;
    
    @FXML
    private TextField txtPort;
    
    @FXML
    private Button btnLaunch;
    
    @FXML
    private Label lblIP;
    
    /**
     * This method serves for loading values dynamically onto the UI
     * while launching it.
     * 
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try(final DatagramSocket socket = new DatagramSocket()){
            //try to connect to 8.8.8.8 to locate the running Adapter
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ipAddress = socket.getLocalAddress().getHostAddress();
            socket.close();
            lblIP.setText(ipAddress);
        } catch (Exception e) {
            e.printStackTrace();
            lblIP.setText("127.0.0.1");
        }
    }
    
    /**
     * This method receives the server, which was loading this controller
     * and stores it locally.
     * The server object can later be used to hand controll back to the server.
     * 
     * @param server Server, which loaded this controller
     */
    public void setServer(Server server) {
        this.server = server;
    }
    
    /**
     * This method is launched when the Launch-Button on the UI is clicked.
     * It calls the next startup method on the server.
     */
    @FXML
    private void launch() {
        int port = Integer.parseInt(txtPort.getText());
        server.launchServer(port);
    }
}
