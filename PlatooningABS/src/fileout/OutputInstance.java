/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileout;

import static prop.ABSSettings.json;

/**
 *
 * @author 悠也
 */
public class OutputInstance {
    public static OutputData dataPeopleLog;
    public static void NewFilePeopleLog(String filename){
        dataPeopleLog = new OutputData(filename);
        
        //Field
        dataPeopleLog.write(json.param.fieldName);
    }
    
    public static OutputData dataTraceLog;
    public static void NewFileTraceLog(String filename){
        dataTraceLog = new OutputData(filename);
        
        //Field
        dataTraceLog.write(json.param.traceFieldName);
    }
    
    public static OutputData dataSummary;
    public static void NewFileSummary(String filename){
        dataSummary = new OutputData(filename);
        
        //Field
        dataSummary.write("n.exp, total step, time[ms]");
    }
    
    public static void close(){
        dataPeopleLog.close();
        dataTraceLog.close();
    }
}
