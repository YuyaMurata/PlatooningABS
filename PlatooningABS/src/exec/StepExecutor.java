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
 * 1ステップの実行用クラス
 * @author murata
 */
public class StepExecutor implements ABSSettings{
    public static Long step = 0L; //Step数
    
    public StepExecutor() {
    }
    
    //1Step 実行処理
    public void execute(long t){
        //Performance
        long start = System.currentTimeMillis();
        
        step = t;
        printLog("Step : "+step);
        
        long pstop = System.currentTimeMillis();
        
        //バス停の人の発生処理
        BusStops.occureQueue();
        
        long bsstop = System.currentTimeMillis();
        
        //Agent Execute (man)
        BusAgents.execute("man", null);
        
        long bstop = System.currentTimeMillis();
        
        //Center Communication
        CenterInfo info = ControlCenter.comm();
        
        long cstop = System.currentTimeMillis();
        
        //Agent Execute (robot)
        BusAgents.execute("robot", info);
        
        long brstop = System.currentTimeMillis();
        
        //ログ出力
        traceLog(step);
        
        long tlstop = System.currentTimeMillis();
        
        System.out.print(", exec(print, busstop, bus, center, bus(robo), log)"+
                (pstop-start)+","+(bsstop-pstop)+","+(bstop-bsstop)+","+(cstop-bstop)+
                ","+(brstop-cstop)+","+(tlstop-brstop)+",");
        
        //Step TimeSpan
        try {
            Thread.sleep(json.param.stepWaitTime);
        } catch (InterruptedException ex) {
        }
    }
    
    //シミュレーションの終了確認
    public Boolean finishCheck(){
        if(json.param.mode == 1)
            return json.param.amountStep == step;
        return BusAgents.finish() && BusStops.finish();
    }
    
    //ログ出力
    public static void printLog(String str){
        System.out.print(str);
            
        //ABS State
        BusStops.printLog();
        BusAgents.printLog();
    }
    
    //トレースログ出力
    public static void traceLog(Long step){
        StringBuilder sb = new StringBuilder();
        sb.append(step);
        sb.append(",");
        sb.append(BusAgents.traceLog());
        sb.append(",");
        sb.append(BusStops.traceLog());
            
        OutputInstance.dataTraceLog.write(sb.toString());
    }
}
