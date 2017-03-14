/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import agent.BusAgents;
import exec.StepExecutor;
import fileout.OutputInstance;
import obj.BusStops;
import park.AmusementPark;
import prop.ABSSettings;

/**
 * 単一実験用のクラス
 * @author murata
 */
public class PlatooningABSMain implements ABSSettings{
    public static Boolean runnable;
    
    public void run(String paramFile){
        runnable = true;
        
        if(paramFile != null)
            PlatooningABSMain.execute(paramFile);
        else
            PlatooningABSMain.execute(settingFileName);
    }
    
    public static void main(String[] args) {
        execute(settingFileName);
    }
    
    private static void execute(String paramFile){
        //Parameter
        json.absJSONRead(paramFile);
        
        //ログファイルの作成
        OutputInstance.NewFilePeopleLog(json.param.peopleFileName);
        OutputInstance.NewFileTraceLog(json.param.traceFileName);
        
        //遊園地クラスの初期化
        AmusementPark park = AmusementPark.getInstance();
        park.init();
        
        //Create Agent & Object
        BusStops.generate();  //BusStopの作成
        BusAgents.generate(); //BusAgentの作成
            
        //シミュレーションの実行
        long time = 1L;
        StepExecutor step = new StepExecutor();
        while(runnable){
            //1Step 実行
            long start = System.currentTimeMillis();
            step.execute(time);
            long stop1 = System.currentTimeMillis();
            
            //実験終了処理
            if(step.finishCheck()){
                //ログファイルの出力
                OutputInstance.dataPeopleLog.write("Finish Steps, "+time);
                OutputInstance.close();
                break;
            }
            long stop2 = System.currentTimeMillis();
            
            System.out.println("Performance Main (exec, finish)=,"+(stop1-start)+","+(stop2-stop1));
            
            //時間経過
            time++;
        }
        
        System.out.println("Finish ABS ");
        park.state = false;
    }
    
    public void stop(){
        runnable = false;
    }
}