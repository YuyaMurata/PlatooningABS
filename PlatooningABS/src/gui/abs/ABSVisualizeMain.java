/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.abs;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.ABSThreadRun;

/**
 *
 * @author murata
 */
public class ABSVisualizeMain  extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("ABSVisualizeFXML.fxml"));
        Parent root = fxml.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        addEvent(stage);
    }
    
    private void addEvent(Stage stage){
        stage.setOnHidden((WindowEvent t) ->{
            closeAction(t);
        });
    }
    
    private void closeAction(WindowEvent t){
        ABSThreadRun.close();
        ABSVisualizerFXML.stopVisualize();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
