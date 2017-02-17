/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import agent.BusAgents;
import exec.StepExecutor;
import fileout.OutputInstance;
import gui.ABSVisualizer;
import obj.BusStops;
import park.AmusementPark;
import prop.ABSSettings;

/**
 *
 * @author murata
 */
public class PlatooningABSMain implements ABSSettings{
    public static void main(String[] args) {
        //Parameter
        json.absJSONRead();
        
        //ログファイルの作成
        OutputInstance.NewFilePeopleLog(json.param.peopleFileName);
        OutputInstance.NewFileTraceLog(json.param.traceFileName);
        
        //遊園地クラスの初期化
        AmusementPark park = AmusementPark.getInstance();
        park.init();
        
        //Create Agent & Object
        BusStops.generate();  //BusStopの作成
        BusAgents.generate(); //BusAgentの作成
        
        //GUI Start
        ABSVisualizer abs = ABSVisualizer.getInstance();
        abs.startVisualize();
        
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
                OutputInstance.close();
                System.exit(0);
            }
        }
    }
}