/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import agent.BusAgents;
import center.CenterInfo;
import center.ControlCenter;
import fileout.OutputInstance;
import obj.BusStops;
import prop.ABSSettings;

/**
 * 単一ステップの実行用クラス
 * @author kaeru
 */
public class StepExecutor implements ABSSettings{
    public static Long step = 0L; //Step数
    
    public StepExecutor() {
    }
    
    //1Step 実行処理
    public void execute(long t){
        step = t;
        
        //バス停の人の発生処理
        BusStops.occureQueue();
        
        //Agent Execute (man)
        BusAgents.execute("man", null);
        
        //Center Communication
        CenterInfo info = ControlCenter.comm();
        System.out.println(info.toString());
        
        //Agent Execute (robot)
        BusAgents.execute("robot", info);
        
        //Test
        //if(step == 10L) commFailure = true;
        
        //ログ出力
        printLog("Step : "+step);
        traceLog(step);
        
        //Step TimeSpan
        try {
            Thread.sleep(json.param.stepWaitTime);
        } catch (InterruptedException ex) {
        }
    }
    
    //シミュレーションの終了確認
    public Boolean finishCheck(){
        return BusAgents.finish() && BusStops.finish();
    }
    
    //ログ出力
    public static void printLog(String str){
        if(json.param.loggingSW){
            System.out.println(str);
            
            //ABS State
            BusStops.printLog();
            BusAgents.printLog();
        }
    }
    
    //トレースログ出力
    public static void traceLog(Long step){
        if(json.param.traceSW){
            StringBuilder sb = new StringBuilder();
            sb.append(step);
            sb.append(",");
            sb.append(BusAgents.traceLog());
            sb.append(",");
            sb.append(BusStops.traceLog());
            
            OutputInstance.dataTraceLog.write(sb.toString());
        }
    }
}
