/**
 * Sample Skeleton for 'BusStopEditGUI.fxml' Controller Class
 */

package gui.edit.busstop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gui.edit.ABSEditVisualizer;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import obj.BusStop;
import prop.ABSSettings;

public class BusStopEditGUIController implements Initializable, ABSSettings{
    private TextArea busstop_num;
    
    @FXML // fx:id="img_json_text"
    private TextArea img_json_text; // Value injected by FXMLLoader

    @FXML // fx:id="root_json_text"
    private TextArea root_json_text; // Value injected by FXMLLoader

    @FXML // fx:id="busstop_json_text"
    private TextArea busstop_json_text; // Value injected by FXMLLoader

    @FXML // fx:id="visual_edit_canvas"
    private Canvas visual_edit_canvas; // Value injected by FXMLLoader

    @FXML // fx:id="busstrop_edit_apply"
    private Button busstrop_edit_apply; // Value injected by FXMLLoader
    
    @FXML
    void editApply(ActionEvent event) {
        setTextToParam();
        try{
            busstop_num.setText(String.valueOf(json.param.numBusStops));
        }catch(Exception e){
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setParamToBusStop();
        
        setParamToImage();
        
        setParamToRoot();
        
        visualizeParam();
    }
    
    public void setNumBusStop(TextArea busstop_num){
        this.busstop_num = busstop_num;
    }
    
    public void setParamToBusStop(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        List<BusStop> bs = new ArrayList<>();
        for(int i=0; i < json.param.numBusStops; i++){
            if(json.param.busStops.size() > i)
                bs.add(json.param.busStops.get(i));
            else
                bs.add(new BusStop("BusStop_"+i, 0, 0));
        }
        
        String busStopJson = gson.toJson(bs);
        System.out.println(bs);
        busstop_json_text.setText(busStopJson);
    }
    
    public void setParamToImage(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String busStopImgJson = gson.toJson(json.param.busStopIMG, Map.class);
        System.out.println(json.param.busStopIMG);
        img_json_text.setText(busStopImgJson);
    }
    
    public void setParamToRoot(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String rootJson = gson.toJson(json.param.root, Map.class);
        System.out.println(json.param.root);
        root_json_text.setText(rootJson);
    }
    
    public void visualizeParam(){
        ABSEditVisualizer visualizer = new ABSEditVisualizer(visual_edit_canvas, 9, 9, json.param.busStopIMG.get(0));
        
        //Draw Cell
        visualizer.drawCell();
        
        //Draw BusStop
        for(BusStop bs : json.param.busStops)
            visualizer.drawImage(bs.name, bs.x, bs.y);
        
        //Draw Root
        int no = 0;
        for(Object r : json.param.root.keySet()){
            List<String> bsRoot = json.param.root.get(r);
            for(int i=0; i < bsRoot.size(); i++){
                String stratRoot = bsRoot.get(i);
                BusStop start = json.param.busStops.stream()
                                    .filter(bs -> bs.name.equals(stratRoot))
                                    .findFirst().get();
                
                String stopRoot = bsRoot.get((i+1)%bsRoot.size());
                BusStop stop = json.param.busStops.stream()
                                    .filter(bs -> bs.name.equals(stopRoot))
                                    .findFirst().get();
                
                visualizer.drawRoot(no, start, stop);
            }
            no++;
        }
    }
    
    public void setTextToParam(){
        Gson gson = new Gson();
        
        Type listType = new TypeToken<Map<Object, List<String>>>() { }. getType();
        json.param.root = gson.fromJson(root_json_text.getText(), listType);
        
        listType = new TypeToken<Map<Integer, String>>() { }. getType();
        json.param.busStopIMG =  gson.fromJson(img_json_text.getText(), listType);
        
        listType = new TypeToken<List<BusStop>>() { }. getType();
        json.param.busStops = gson.fromJson(busstop_json_text.getText(), listType);
        
        json.param.numBusStops = json.param.busStops.size();
        
        visualizeParam();
    }
}
