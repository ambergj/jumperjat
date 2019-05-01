package resources;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
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
    
    /**
     * Additional utility method to load a fxml file
     * 
     * @param <T> Controller-Class of the fxml file
     * @param calledFrom Object that calls this method; needed to load the url
     * @param location Path to the file as String
     * @param clazz Controller-Class of the fxml file
     * @return LoaderContainer containing the root element and the controller object
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> LoaderContainer<T> loadUI(Object calledFrom, String location, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException{
        FXMLLoader loader = new FXMLLoader(calledFrom.getClass().getResource(location));
        Parent root = loader.load();
        T ctrl = (T)clazz.newInstance();
        ctrl = loader.getController();
        LoaderContainer<T> lc = new LoaderContainer<>(root, ctrl);
        return lc;
        
    }
    
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
