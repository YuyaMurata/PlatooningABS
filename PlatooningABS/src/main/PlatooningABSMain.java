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

/**
 *
 * @author murata
 */
public class PlatooningABSMain implements ABSSettings{
    public static void main(String[] args) {
        //Parameter
        json.absJSONRead();
        
        //File
        OutputInstance.NewFilePeopleLog(json.param.fileName);
        OutputInstance.NewFileTraceLog(json.param.traceFileName);
        
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
        StepExecutor step = new StepExecutor();
        long time = 0L;
        while(true){
            time++;
            step.execute(time);
            
            if(step.finishCheck()){
                OutputInstance.dataPeopleLog.write("Finish Steps, "+time);
                System.out.println("Finish Steps, "+time);
                OutputInstance.close();
                System.exit(0);
            }
        }
    }
}