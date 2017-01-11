/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaeru
 */
public class BusAgents {
    private static List busAgents;
    public static void generate(int n){
        busAgents = new ArrayList();
        
        for(int i=0; i < n; i++){
            busAgents.add(new BusAgent(i, "human"));
        }
        
        //Check BusStop
        busAgents.stream().forEach(System.out::println);
    }
    
    public static BusAgent getBusAgent(int i){
        return (BusAgent)busAgents.get(i);
    }
    
    public static Integer getNumBusStop(){
        return busAgents.size();
    }
    
    public static List<BusAgent> getList(){
        return busAgents;
    }
}
