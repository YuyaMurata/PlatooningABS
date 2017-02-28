/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.edit.bus;

import agent.BusAgent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import prop.ABSSettings;

/**
 * FXML Controller class
 *
 * @author murata
 */
public class BusEditGUIController implements Initializable, ABSSettings {
    private ObservableList<BusAgent> tableRecord = FXCollections.observableArrayList();
    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setABSParamToTable();
    } 
    
    //結果を反映する
    @FXML
    void busEditApply(ActionEvent event) {
        setTableToABSParam();
    }
    
    //テーブルにバス情報をセット
    void setABSParamToTable(){
        bus_table.setItems(tableRecord);
        
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
        
        for(int i=0; i < json.param.numBusAgents; i++){
            BusAgent bus = null;
            try{
                bus = json.param.busAgents.get(i);
            }catch(IndexOutOfBoundsException e){
                bus = new BusAgent("Bus_"+i, "man", "bus.DefaultBusAgent", "none");
            }
            
            bus_table.getItems().add(bus);
        }
    }
    
    //バス情報を更新
    void setTableToABSParam(){
        json.param.busAgents = bus_table.getItems();
        System.out.println(json.param.busAgents);
    }
}
