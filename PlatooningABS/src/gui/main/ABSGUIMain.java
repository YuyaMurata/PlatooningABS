/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import log.LoggingFileStream;
import prop.ABSSettings;

/**
 * パラメタ管理GUI
 * @author murata
 */
public class ABSGUIMain extends Application implements ABSSettings{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ABSGUI.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //標準出力の変更
        if(logConsoleFileSW)
            try {
                System.setOut(new LoggingFileStream("abs_syslog.log"));
                System.setErr(new LoggingFileStream("abs_errlog.log"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        
        launch(args);
    }
}
