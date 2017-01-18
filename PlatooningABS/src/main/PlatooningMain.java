/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import agent.BusAgents;
import exec.StepExecutor;
import fileout.OutputInstance;
import gui.ABSFrame;
import gui.ABSVisualizer;
import obj.BusStops;

/**
 *
 * @author kaeru
 */
public class PlatooningMain {
    public static void main(String[] args) {
        //File
        OutputInstance.NewFile("platooning_abs_log.txt");
        //Field
        OutputInstance.data.write("PID, Root, BusTime, QueueTime");
        
        int col = 9;
        int row = 9;
        
        //Frame
        ABSFrame frame = ABSFrame.getInstance();
        
        //Line
        ABSVisualizer abs = ABSVisualizer.getInstance();
        abs.setVisualParameter(col, row);
        
        //Agent
        BusStops.generate(5);  //goal
        BusStops.occureQueue();
        BusAgents.generate(4); //agent
        abs.setAgent(BusAgents.getList(), BusStops.getList());
        
        //GUI Start
        frame.execute(args);
        abs.startVisualize();
        
        //Execute
        StepExecutor step = new StepExecutor(BusAgents.getList());
        long time = 0L;
        while(true){
            time++;
            step.execute(time);
            
            if(step.finishCheck()){
                OutputInstance.data.close();
                System.exit(0);
            }
        }
    }
}