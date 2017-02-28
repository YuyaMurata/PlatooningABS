/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import agent.BusAgents;
import exec.StepExecutor;
import fileout.OutputInstance;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import obj.BusStops;
import park.AmusementPark;
import prop.ABSSettings;
import static prop.ABSSettings.json;

/**
 *
 * @author murata
 */
public class PlatooningABSExperiment implements ABSSettings{
    public static void main(String[] args) {
        //Parameter
        json.absJSONRead(settingFileName);
        
        //高速化
        json.param.stepWaitTime = 0L;
        json.param.loggingSW = false;

        //実験時のログデータの初期化
        //ログの日付
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH-mm-ss");
        //ログ出力フォルダの作成
        String dirName = json.param.folderName+"\\"+sdf.format(date)+"_"+json.param.numOfExec;
        new File(dirName).mkdirs();
        //サマリーの作成
        OutputInstance.NewFileSummary(dirName+"\\"+"summary.csv");
        //各ログファイルの出力フォルダの作成
        String dirTraceName = dirName+"\\trace";
        new File(dirTraceName).mkdirs();
        String dirLogName = dirName+"\\log";
        new File(dirLogName).mkdirs();
        
        //パラメータファイルをコピー
        try {
            Files.copy (new File(ABSSettings.settingFileName).toPath(), 
                        new File(dirName+"\\"+ABSSettings.settingFileName).toPath());
        } catch (IOException ex) {
        }
        
        //Time
        Long start = System.currentTimeMillis();
        Long totalStep = 0L;
        System.out.println("Start Platooning ABS.");
        for(int i=0; i < json.param.numOfExec; i++){
            //Start
            Long execStart = System.currentTimeMillis();
            System.out.println("> Experiment:"+i);
            
            //ログファイルの作成
            OutputInstance.NewFilePeopleLog(dirLogName+"\\exec_"+i+"_"+json.param.peopleFileName);
            OutputInstance.NewFileTraceLog(dirTraceName+"\\exec_"+i+"_"+json.param.traceFileName);
            
            //遊園地クラスの初期化
            AmusementPark park = AmusementPark.getInstance();
            park.init();
            
            //Create Agent & Object
            BusStops.generate();  //BusStopの作成
            BusAgents.generate(); //BusAgentの作成
            
            //シミュレーションの実行
            long time = 1L;
            StepExecutor step = new StepExecutor();
            while(true){
                //1Step 実行
                step.execute(time++);
                
                //実験終了処理
                if(step.finishCheck()){
                    //ログファイルの出力
                    OutputInstance.dataPeopleLog.write("Finish Steps, "+time);
                    System.out.println("Finish Steps, "+time);
                    totalStep += time;
                    OutputInstance.close();
                    break;
                }
            }
            
            //実験の結果を出力
            Long execStop = System.currentTimeMillis() - execStart;
            System.out.println("> Finish Experiment:"+i+" time="+execStop+"[ms]");
            OutputInstance.dataSummary.write("Experiment:"+i+", "+time+", "+execStop+", ms");
        }
        
        //Finish
        Long stop = System.currentTimeMillis() -  start;
        System.out.println("Stop Platooning ABS. Time : "+stop+" [ms]");
        
        //平均ステップ数を出力
        Long avg = totalStep / json.param.numOfExec;
        OutputInstance.dataSummary.write("Platooning ABS Total Experiment:"+json.param.numOfExec+", "+avg+", "+stop+", ms");
        OutputInstance.dataSummary.close();
    }
}
