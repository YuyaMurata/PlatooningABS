/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import agent.BusAgent;
import agent.BusAgents;
import fileout.OutputInstance;
import java.util.List;
import obj.BusStop;
import obj.BusStops;
import prop.ABSSettings;

/**
 *
 * @author kaeru
 */
public class StepExecutor implements ABSSettings{
    private List<BusAgent> execList;
    public StepExecutor(List<BusAgent> busLists) {
        this.execList = busLists;
    }
    
    public static Long step = 0L;
    public void execute(long t){
        step = t;
        
        //form line
        BusStops.occureQueue();
        
        //Agent Execute
        execList.stream().forEach(bus -> ((BusAgent)bus).move());
        
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
        long ql = 0L;
        for(BusStop bs : BusStops.getBusStops())
            ql = ql + bs.getQueue().size();
        
        for(BusAgent ba : BusAgents.getList())
            ql = ql + ba.numPassenger();
            
        if(ql == 0L) return true;
        return false;
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
