package resources;

import javafx.scene.Parent;

/**
 * This class only serves to combine a view-controller and a root element
 * from a FXML-Loader object.
 * Using this class you can ship both at once.
 * 
 * @author luescherphi
 * @version 1.0
 * @since 1.8.0
 */
public class LoaderContainer<T>{
    
    private Parent root;
    private T ctrl;
    
    public LoaderContainer(Parent root, T ctrl){
        this.root = root;
        this.ctrl = ctrl;
    }
    
    public Parent getRoot() {
        return root;
    }
    
    public void setRoot(Parent root) {
        this.root = root;
    }
    
    public T getCtrl() {
        return ctrl;
    }
    
    public void setCtrl(T ctrl) {
        this.ctrl = ctrl;
    }

}
