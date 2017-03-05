/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.edit.bus;

import agent.BusAgent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import prop.ABSSettings;

/**
 * FXML Controller class
 * バス編集GUI
 * @author murata
 */
public class BusEditGUIController implements Initializable, ABSSettings {
    private ObservableList<BusAgent> tableRecord = FXCollections.observableArrayList();
    private TextArea bus_num;
    
    @FXML // fx:id="name_col"
    private TableColumn<BusAgent, String> name_col; // Value injected by FXMLLoader

    @FXML // fx:id="param_col"
    private TableColumn<BusAgent, String> param_col; // Value injected by FXMLLoader

    @FXML // fx:id="class_col"
    private TableColumn<BusAgent, String> class_col; // Value injected by FXMLLoader

    @FXML // fx:id="bus_table"
    private TableView<BusAgent> bus_table; // Value injected by FXMLLoader

    @FXML // fx:id="type_col"
    private TableColumn<BusAgent, String> type_col; // Value injected by FXMLLoader

    @FXML // fx:id="bus_edit_apply"
    private Button bus_edit_apply; // Value injected by FXMLLoader
    
    @FXML // fx:id="bus_img_text"
    private TextField bus_img_text; // Value injected by FXMLLoader
    
    @FXML // fx:id="bus_edit_change"
    private ToggleButton bus_edit_change; // Value injected by FXMLLoader
    
    @FXML // fx:id="deleteRow"
    private MenuItem deleteRow; // Value injected by FXMLLoader

    @FXML // fx:id="add_row"
    private MenuItem add_row; // Value injected by FXMLLoader
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        prefTable();
        setABSParamToTable();
    }
    
    //編集方法の切り替え
    @FXML
    void busEditChange(ActionEvent event) {
        System.out.println("Button="+bus_edit_change.isSelected());
        if(bus_edit_change.isSelected()){
            setTableToABSParam();
            setABSParamToChangeInfo();
        }else{
            setChangeInfoToABSParam();
            setABSParamToTable();
        }
    }
    
    //結果を反映する
    @FXML
    void busEditApply(ActionEvent event) {
        if(bus_edit_change.isSelected())
            setChangeInfoToABSParam();
        else
            setTableToABSParam();
        
        bus_num.clear();
        bus_num.setText(String.valueOf(json.param.numBusAgents));
    }
    
    //1行追加
    @FXML
    void addRow(ActionEvent event) {
        int i = bus_table.getSelectionModel().getSelectedIndex();
        bus_table.getItems().add(i+1, new BusAgent("Bus?","Type","Class","0"));
    }

    //1行削除
    @FXML
    void deleteRow(ActionEvent event) {
        int i = bus_table.getSelectionModel().getSelectedIndex();
        if(bus_table.getItems().size() > 0)
            bus_table.getItems().remove(i);
    }
    
    public void setBusNum(TextArea bus_num){
        this.bus_num = bus_num;
    }
    
    //テーブルとオブジェクト(BusAgent)の関連付け
    void prefTable(){
        bus_table.setItems(tableRecord);
        
        //各セルに情報を表示
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        name_col.setCellFactory(cell -> new TextFieldTableCell<>(new DefaultStringConverter()));
        name_col.setOnEditCommit((TableColumn.CellEditEvent<BusAgent, String> event) -> {
            BusAgent bus = event.getTableView().getItems().get(event.getTablePosition().getRow());
            bus.setName(event.getNewValue());
        });
        
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        type_col.setCellFactory(cell -> new TextFieldTableCell<>(new DefaultStringConverter()));
        type_col.setOnEditCommit((TableColumn.CellEditEvent<BusAgent, String> event) -> {
            BusAgent bus = event.getTableView().getItems().get(event.getTablePosition().getRow());
            bus.setType(event.getNewValue());
        });
        
        class_col.setCellValueFactory(new PropertyValueFactory<>("clazz"));
        class_col.setCellFactory(cell -> new TextFieldTableCell<>(new DefaultStringConverter()));
        class_col.setOnEditCommit((TableColumn.CellEditEvent<BusAgent, String> event) -> {
            BusAgent bus = event.getTableView().getItems().get(event.getTablePosition().getRow());
            bus.setClazz(event.getNewValue());
        });
        
        param_col.setCellValueFactory(new PropertyValueFactory<>("param"));
        param_col.setCellFactory(cell -> new TextFieldTableCell<>(new DefaultStringConverter()));
        param_col.setOnEditCommit((TableColumn.CellEditEvent<BusAgent, String> event) -> {
            BusAgent bus = event.getTableView().getItems().get(event.getTablePosition().getRow());
            bus.setParam(event.getNewValue());
        });
    }
    
    //テーブルにバス情報をセット
    void setABSParamToTable(){
        bus_table.getItems().clear();
        
        for(int i=0; i < json.param.numBusAgents; i++){
            BusAgent bus = null;
            try{
                bus = json.param.busAgents.get(i);
            }catch(IndexOutOfBoundsException e){
                bus = new BusAgent("Bus_"+i, "man", "bus.DefaultBusAgent", "none");
            }
            
            bus_table.getItems().add(bus);
        }
        
        bus_img_text.setText(json.param.busIMG.get(0));
    }
    
    //バス情報を更新
    void setTableToABSParam(){
        json.param.busAgents = bus_table.getItems();
        json.param.numBusAgents = json.param.busAgents.size();
        
        json.param.busIMG.clear();
        json.param.busIMG.add(bus_img_text.getText());
    }
    
    //ABSパラメータを切替情報に適用
    void setABSParamToChangeInfo(){
        Map<String, List<BusAgent>> group = json.param.busAgents.stream()
                                                    .collect(Collectors.groupingBy(bus -> bus.clazz));
        System.out.println(group);
        bus_table.getItems().clear();
        
        //man
        for(String clazz : group.keySet()){
            String type = group.get(clazz).get(0).type;
            if(!type.equals("man")) continue ;
            String origName = group.get(clazz).get(0).name;
            String name = origName.substring(0, origName.lastIndexOf("_"));
            bus_table.getItems().add(new BusAgent(name, type, clazz, String.valueOf(group.get(clazz).size())));
        }
        
        //others
        for(String clazz : group.keySet()){
            String type = group.get(clazz).get(0).type;
            if(type.equals("man")) continue ;
            String origName = group.get(clazz).get(0).name;
            String name = origName.substring(0, origName.lastIndexOf("_"));
            bus_table.getItems().add(new BusAgent(name, type, clazz, String.valueOf(group.get(clazz).size())));
        }
    }
    
    //切替情報をABSパラメータに適用
    void setChangeInfoToABSParam(){
        json.param.busAgents = new ArrayList<>();
        
        int j = 0;
        
        //type = man
        List<BusAgent> man = bus_table.getItems().stream()
                        .filter(bus -> bus.type.equals("man"))
                        .collect(Collectors.toList());
        for(BusAgent bus : man){
            for(int i=0; i < Integer.parseInt(bus.param); i++){
                json.param.busAgents.add(new BusAgent(bus.name+"_"+j, bus.type, bus.clazz, ""));
                j++;
            }
        }
        
        //type = others
        List<BusAgent> other = bus_table.getItems().stream()
                        .filter(bus -> !bus.type.equals("man"))
                        .collect(Collectors.toList());
        for(BusAgent bus : other){
            for(int i=0; i < Integer.parseInt(bus.param); i++){
                json.param.busAgents.add(new BusAgent(bus.name+"_"+j, bus.type, bus.clazz, ""));
                j++;
            }
        }
        
        json.param.numBusAgents = json.param.busAgents.size();
    }
}
