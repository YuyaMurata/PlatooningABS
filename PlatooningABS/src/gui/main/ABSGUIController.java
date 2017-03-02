/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import gui.abs.ABSVisualizeMain;
import gui.edit.bus.BusEditGUIMain;
import gui.edit.busstop.BusStopEditGUIMain;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.ABSThreadRun;
import prop.ABSSettings;

/**
 * FXML Controller class
 *
 * @author murata
 */
public class ABSGUIController implements Initializable, ABSSettings{
    private ABSGUIInitializeFile iniFIle = new ABSGUIInitializeFile();
    
    @FXML // fx:id="busstop_edit"
    private Button busstop_edit; // Value injected by FXMLLoader

    @FXML // fx:id="comm_fault_check"
    private CheckBox comm_fault_check; // Value injected by FXMLLoader

    @FXML // fx:id="visual_gui_slider"
    private Slider visual_gui_slider; // Value injected by FXMLLoader

    @FXML // fx:id="mode_limit_queue"
    private TextArea mode_limit_queue; // Value injected by FXMLLoader

    @FXML // fx:id="log_consol_check"
    private CheckBox log_consol_check; // Value injected by FXMLLoader

    @FXML // fx:id="abs_run"
    private Button abs_run; // Value injected by FXMLLoader

    @FXML // fx:id="log_folder"
    private TextArea log_folder; // Value injected by FXMLLoader

    @FXML // fx:id="log_trace_file"
    private TextArea log_trace_file; // Value injected by FXMLLoader

    @FXML // fx:id="log_people_file"
    private TextArea log_people_file; // Value injected by FXMLLoader

    @FXML // fx:id="bus_num"
    private TextArea bus_num; // Value injected by FXMLLoader

    @FXML // fx:id="queue_class"
    private TextArea queue_class; // Value injected by FXMLLoader

    @FXML // fx:id="visual_gui_text"
    private Text visual_gui_text; // Value injected by FXMLLoader

    @FXML // fx:id="mode"
    private ToggleGroup mode; // Value injected by FXMLLoader

    @FXML // fx:id="param_file"
    private TextArea param_file; // Value injected by FXMLLoader

    @FXML // fx:id="mode_limit_step"
    private TextArea mode_limit_step; // Value injected by FXMLLoader

    @FXML // fx:id="exp_num"
    private TextArea exp_num; // Value injected by FXMLLoader

    @FXML // fx:id="busagent_edit"
    private Button busagent_edit; // Value injected by FXMLLoader

    @FXML // fx:id="bus_pax_num"
    private TextArea bus_pax_num; // Value injected by FXMLLoader

    @FXML // fx:id="busstop_num"
    private TextArea busstop_num; // Value injected by FXMLLoader

    @FXML // fx:id="log_trace_check"
    private CheckBox log_trace_check; // Value injected by FXMLLoader

    @FXML // fx:id="mode_queue_radio"
    private RadioButton mode_queue_radio; // Value injected by FXMLLoader

    @FXML // fx:id="bus_platoon_check"
    private CheckBox bus_platoon_check; // Value injected by FXMLLoader

    @FXML // fx:id="log_people_check"
    private CheckBox log_people_check; // Value injected by FXMLLoader

    @FXML // fx:id="queue_step_num"
    private TextArea queue_step_num; // Value injected by FXMLLoader

    @FXML // fx:id="param_save"
    private Button param_save; // Value injected by FXMLLoader

    @FXML // fx:id="visual_gui_check"
    private CheckBox visual_gui_check; // Value injected by FXMLLoader

    @FXML // fx:id="cell_row"
    private TextArea cell_row; // Value injected by FXMLLoader

    @FXML // fx:id="cell_col"
    private TextArea cell_col; // Value injected by FXMLLoader

    @FXML // fx:id="comm_fault_time"
    private TextArea comm_fault_time; // Value injected by FXMLLoader

    @FXML // fx:id="bus_lost_prob"
    private TextArea bus_lost_prob; // Value injected by FXMLLoader

    @FXML // fx:id="comm_fault_period"
    private TextArea comm_fault_period; // Value injected by FXMLLoader

    @FXML // fx:id="param_open"
    private Button param_open; // Value injected by FXMLLoader

    @FXML // fx:id="mode_step_radio"
    private RadioButton mode_step_radio; // Value injected by FXMLLoader
    
    void guiSpeedSlide() {
        int speed = (int) visual_gui_slider.getValue();
        visual_gui_text.setText((speed*10)+"ms");
    }
    
    @FXML
    void saveABSParameter(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ABS パラメータファイルの保存");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.setInitialFileName(param_file.getText());
        fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
        Window window = param_save.getScene().getWindow();
        
        String fileName = "";
        try{
            fileName = fc.showSaveDialog(window).getName();
            
            //Save (JSON and SetParam)
            param_file.setText(fileName);
            setGUIToParam();
            
            //Update INI File
            iniFIle.setABSParamFile(fileName);
        }catch(Exception e){
        }
    }

    @FXML
    void openABSParameter(ActionEvent event) {
        final FileChooser fc = new FileChooser();
        fc.setTitle("ABS パラメータファイルの選択");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.setInitialFileName(param_file.getText());
        fc.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
        Window window = param_open.getScene().getWindow();
        
        String fileName = "";
        try{
            fileName = fc.showOpenDialog(window).getName();
            
            //Open (JSON Read)
            param_file.setText(fileName);
            setParamToGUI();
            
            //Update INI File
            iniFIle.setABSParamFile(fileName);
        }catch(Exception e){
        }
    }

    @FXML
    void editBusAgent(ActionEvent event) {
        json.param.numBusAgents = Integer.valueOf(bus_num.getText());

        BusEditGUIMain busEdit = new BusEditGUIMain();
        
        Stage editStage = new Stage();
        editStage.initOwner(busagent_edit.getScene().getWindow());
        try {
            busEdit.start(editStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void editBusStop(ActionEvent event) {
        json.param.numBusStops = Integer.valueOf(busstop_num.getText());
        json.param.column = Integer.valueOf(cell_col.getText());
        json.param.row = Integer.valueOf(cell_row.getText());
        
        BusStopEditGUIMain busStopedit = new BusStopEditGUIMain();
        busStopedit.busstop_num = busstop_num;
        
        Stage editStage = new Stage();
        editStage.initOwner(busagent_edit.getScene().getWindow());
        try {
            busStopedit.start(editStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void runABS(ActionEvent event) {
        //Write JSON
        if(!param_file.getText().contains("json"))
            param_file.setText(settingFileName);
        setGUIToParam();
        
        //ABS GUI
        if(visual_gui_check.isSelected()){
            ABSVisualizeMain abs = new ABSVisualizeMain();
            Stage absStage = new Stage();
            absStage.initOwner(abs_run.getScene().getWindow());
            try {
                abs.start(absStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        //ABS Run
        Thread thread = new ABSThreadRun(param_file.getText(), json.param.numOfExec);
        thread.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set INI File
        System.out.println("Initialize:"+iniFIle.getABSParamFile());
        param_file.setText(iniFIle.getABSParamFile());
        
        //Set JSON File
        setParamToGUI();
            
        //add EventListener
        addEvent();
    }
    
    //独自イベントの追加
    public void addEvent(){
        // Listen for Slider value changes
        visual_gui_slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
                guiSpeedSlide();
            }
        });
    }
    
    private void setParamToGUI(){
        //JSON Read
        try{
            json.absJSONRead(param_file.getText());
        }catch(Exception e){
            //JSONファイルの取得を失敗
            //デフォルトのパラメータを各項目に設定
        }
            
        //ABS Visualize
        visual_gui_check.setSelected(json.param.guiSW);
        visual_gui_slider.setValue((double)json.param.stepWaitTime / 10);
        visual_gui_text.setText(json.param.stepWaitTime+"ms");
        
        //Experimental Conditions
        exp_num.setText(String.valueOf(json.param.numOfExec));
        cell_col.setText(String.valueOf(json.param.column));
        cell_row.setText(String.valueOf(json.param.row));
        switch(json.param.mode){
            case 0: mode_queue_radio.setSelected(true);
                    break;
            case 1: mode_step_radio.setSelected(true);
                    break;
        }
        mode_limit_queue.setText(String.valueOf(json.param.amountPeople));
        mode_limit_step.setText(String.valueOf(json.param.amountStep));
                    
        //Bus Agent
        bus_num.setText(String.valueOf(json.param.numBusAgents));
        bus_pax_num.setText(String.valueOf(json.param.maxPassengers));
        bus_lost_prob.setText(String.valueOf(json.param.lostProb));
        bus_platoon_check.setSelected(json.param.changeLineSW);
        
        //Bus Stop
        busstop_num.setText(String.valueOf(json.param.numBusStops));
        
        //Queue
        queue_step_num.setText(String.valueOf(json.param.queuingByStep));
        queue_class.setText(json.param.queueClassName);
        
        //Center
        comm_fault_check.setSelected(json.param.failureSW);
        comm_fault_period.setText(String.valueOf(json.param.accidentPeriod));
        comm_fault_time.setText(String.valueOf(json.param.accidentTime));
        
        //Logging
        log_folder.setText(json.param.folderName);
        log_people_check.setSelected(json.param.loggingSW);
        log_people_file.setText(json.param.peopleFileName);
        log_trace_check.setSelected(json.param.traceSW);
        log_trace_file.setText(json.param.traceFileName);
        log_consol_check.setSelected(json.param.consoleSW);
    }
    
    private void setGUIToParam(){
        //ABS Visualize
        json.param.guiSW = visual_gui_check.isSelected();
        json.param.stepWaitTime = Long.valueOf(visual_gui_text.getText().replace("ms", ""));
        
        //Experimental Conditions
        json.param.numOfExec = Integer.valueOf(exp_num.getText());
        json.param.column = Integer.valueOf(cell_col.getText());
        json.param.row = Integer.valueOf(cell_row.getText());
        if(mode_queue_radio.isSelected())
            json.param.mode = 0;
        else
            json.param.mode = 1;
        json.param.amountPeople = Integer.valueOf(mode_limit_queue.getText());
        json.param.amountStep = Integer.valueOf(mode_limit_step.getText());
                    
        //Bus Agent
        json.param.numBusAgents = Integer.valueOf(bus_num.getText());
        json.param.maxPassengers = Integer.valueOf(bus_pax_num.getText());
        json.param.lostProb = Double.valueOf(bus_lost_prob.getText());
        json.param.changeLineSW = bus_platoon_check.isSelected();
        
        //Bus Stop
        json.param.numBusStops = Integer.valueOf(busstop_num.getText());
        
        //Queue
        json.param.queuingByStep = Integer.valueOf(queue_step_num.getText());
        json.param.queueClassName = queue_class.getText();
        
        //Center
        json.param.failureSW = comm_fault_check.isSelected();
        json.param.accidentPeriod = Long.valueOf(comm_fault_period.getText());
        json.param.accidentTime = Long.valueOf(comm_fault_time.getText());
        
        //Logging
        json.param.folderName = log_folder.getText();
        json.param.loggingSW = log_people_check.isSelected();
        json.param.peopleFileName = log_people_file.getText();
        json.param.traceSW = log_trace_check.isSelected();
        json.param.traceFileName = log_trace_file.getText();
        json.param.consoleSW = log_consol_check.isSelected();
        
        //JSON Write
        System.out.println(param_file.getText());
        json.absJSONWrite(param_file.getText());
    }
}
