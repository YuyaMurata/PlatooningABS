/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileout;

import static prop.ABSSettings.json;

/**
 * ログファイル管理用クラス
 * @author murata
 */
public class OutputInstance {
    //人ログデータの作成
    public static OutputData dataPeopleLog; //人ログデータの管理
    public static void NewFilePeopleLog(String filename){
        //Create File
        dataPeopleLog = new OutputData(filename, json.param.loggingSW);
        
        //Field
        dataPeopleLog.write(json.param.peopleFieldName);
    }
    
    //トレースログの作成
    public static OutputData dataTraceLog; //トレースログの管理
    public static void NewFileTraceLog(String filename){
        //CreateFile
        dataTraceLog = new OutputData(filename, json.param.traceSW);
        
        //Field
        dataTraceLog.write(json.param.traceFieldName);
    }
    
    //サマリーデータの作成
    public static OutputData dataSummary; //サマリーの管理
    public static void NewFileSummary(String filename){
        dataSummary = new OutputData(filename, true);
        
        //Field
        dataSummary.write("n.exp, avg.step, time[ms]");
    }
    
    //コンソール出力
    public static void consoleOut(String str){
        if(json.param.consoleSW)
            System.out.println(str);
    }
    
    //すべてのファイルをクローズ
    public static void close(){
        dataPeopleLog.close();
        dataTraceLog.close();
    }
}
