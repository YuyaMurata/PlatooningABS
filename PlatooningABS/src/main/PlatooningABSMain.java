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
import static prop.ABSSettings.json;

/**
 *
 * @author kaeru
 */
public class PlatooningABSMain implements ABSSettings{
    public static void main(String[] args) {
        //Parameter
        json.absJSONRead();
        
        //File
        OutputInstance.NewFile(json.param.fileName);
        
        //Universe
        AmusementPark park = AmusementPark.getInstance();
        park.init();
        
        //Agent
        BusStops.generate();  //goal
        BusAgents.generate(); //agent
        
        //GUI Start
        ABSVisualizer abs = ABSVisualizer.getInstance();
        abs.startVisualize();
        
        //Execute
        StepExecutor step = new StepExecutor(BusAgents.getList());
        long time = 0L;
        while(true){
            time++;
            step.execute(time);
            
            if(step.finishCheck()){
                OutputInstance.data.write("Finish Steps, "+time);
                System.out.println("Finish Steps, "+time);
                OutputInstance.data.close();
                System.exit(0);
            }
        }
    }
}