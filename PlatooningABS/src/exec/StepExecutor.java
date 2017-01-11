/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import agent.BusAgent;
import java.util.List;

/**
 *
 * @author kaeru
 */
public class StepExecutor {
    private List<BusAgent> execList;
    public StepExecutor(List<BusAgent> busLists) {
        this.execList = busLists;
    }
    
    public void execute(long t){
        System.out.println("Step : "+t);
        
        //Agent Execute
        execList.stream().forEach(bus -> ((BusAgent)bus).move());
        
        //Step TimeSpan
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}
