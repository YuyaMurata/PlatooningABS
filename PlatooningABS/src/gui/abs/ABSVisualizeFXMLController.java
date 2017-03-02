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
import javafx.scene.layout.AnchorPane;
import prop.ABSSettings;

public class ABSVisualizeFXMLController implements Initializable, ABSSettings{
    private ABSVisualizerFXML abs;
    
    @FXML // fx:id="abs_visual"
    private Canvas abs_visual; // Value injected by FXMLLoader
    
    @FXML // fx:id="abs_visual_ancpane"
    private AnchorPane abs_visual_ancpane; // Value injected by FXMLLoader
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abs = new ABSVisualizerFXML(abs_visual);
        
        // TODO
        abs.setABSVisualParam(
                json.param.column, 
                json.param.row, 
                json.param.busIMG, 
                json.param.busStopIMG
        );
        
        addEvent();
        
        abs.startVisualize();
    }
    
    private void addEvent(){
        // イベントハンドラをシーンに登録
        abs_visual_ancpane.widthProperty().addListener(evt -> redraw());
        abs_visual_ancpane.heightProperty().addListener(evt -> redraw());
    }
    
    public void redraw(){
        abs_visual.setWidth(abs_visual_ancpane.getWidth());
        abs_visual.setHeight(abs_visual_ancpane.getHeight());
        
        abs.resize();
    }
}
