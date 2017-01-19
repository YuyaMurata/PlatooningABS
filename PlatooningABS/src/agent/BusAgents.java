/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.ArrayList;
import java.util.List;
import prop.ABSSettings;

/**
 *
 * @author kaeru
 */
public class BusAgents extends ABSSettings{
    private static List<BusAgent> busAgents;
    public static void generate(){
        int n = numBusAgents;
        
        busAgents = new ArrayList();
        
        for(int i=0; i < 2; i++){
            busAgents.add(new BusAgent(i, "human"));
            busAgents.get(i).busPosition(i*8, i*8);
        }
        
        if(n-2 > 0)
            for(int i=2; i < n; i++){
                busAgents.add(new BusAgent(i, "robot"));
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
    
    private static int leaderNum = 0;
    public static BusAgent getLeader(BusAgent robot){
        //Test
        return busAgents.get((leaderNum++) % 2);
    }
    
    public static BusAgent getLeader(int rootNo){
        //Test
        if(rootNo == 0) return busAgents.get((0));
        else return busAgents.get((1));
    }
    
    public static void printLog(){
        if(loggingSW)
            busAgents.stream().forEach(System.out::println);
    }
}
