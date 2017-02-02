/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import agent.BusAgents;
import fileout.OutputInstance;
import obj.BusStops;
import prop.ABSSettings;

/**
 *
 * @author kaeru
 */
public class StepExecutor implements ABSSettings{
    public StepExecutor() {
    }
    
    public static Long step = 0L;
    public void execute(long t){
        step = t;
        
        //form line
        BusStops.occureQueue();
        
        //Agent Execute
        BusAgents.execute();
        
        //Logging
        printLog("Step : "+step);
        traceLog(step);
        
        //Test
        //if(step == 10L) commFailure = true;
        
        //Step TimeSpan
        try {
            Thread.sleep(json.param.stepWaitTime);
        } catch (InterruptedException ex) {
        }
    }
    
    public Boolean finishCheck(){
        return BusAgents.finish() && BusStops.finish();
    }
    
    public static void printLog(String str){
        if(json.param.loggingSW){
            System.out.println(str);
            
            //ABS State
            BusStops.printLog();
            BusAgents.printLog();
        }
    }
    
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
