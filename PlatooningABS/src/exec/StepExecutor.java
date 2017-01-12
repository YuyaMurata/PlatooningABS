/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import agent.BusAgent;
import agent.BusAgents;
import java.util.List;
import obj.BusStop;
import obj.BusStops;

/**
 *
 * @author kaeru
 */
public class StepExecutor {
    private List<BusAgent> execList;
    public StepExecutor(List<BusAgent> busLists) {
        this.execList = busLists;
    }
    
    public static Long step = 0L;
    public void execute(long t){
        System.out.println("Step : "+t);
        step = t;
        
        //Agent Execute
        execList.stream().forEach(bus -> ((BusAgent)bus).move());
        
        //BusStop State
        System.out.println(BusStops.getList());
        
        //Step TimeSpan
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }*/
    }
    
    public Boolean finishCheck(){
        long ql = 0L;
        for(BusStop bs : BusStops.getList())
            ql = ql + bs.getQueue().size();
        
        for(BusAgent ba : BusAgents.getList())
            ql = ql + ba.numGetOn();
            
        if(ql == 0L) return true;
        return false;
    }
}
