/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Sample Skeleton for 'ABSVisualizeFXML.fxml' Controller Class
 */

package gui.abs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import prop.ABSSettings;

public class ABSVisualizeFXMLController implements Initializable, ABSSettings{

    @FXML // fx:id="abs_visual"
    private Canvas abs_visual; // Value injected by FXMLLoader
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ABSVisualizerFXML abs = new ABSVisualizerFXML(abs_visual);
        abs.setABSVisualParam(
                json.param.column, 
                json.param.row, 
                json.param.busIMG, 
                json.param.busStopIMG
        );
        
        abs.startVisualize();
    }     
}
