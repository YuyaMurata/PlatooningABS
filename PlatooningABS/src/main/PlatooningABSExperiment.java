/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import agent.BusAgents;
import exec.StepExecutor;
import fileout.OutputInstance;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import obj.BusStops;
import park.AmusementPark;
import prop.ABSSettings;
import static prop.ABSSettings.json;

/**
 *
 * @author murata
 */
public class PlatooningABSExperiment implements ABSSettings{
    public static void main(String[] args) {
        //Parameter
        json.absJSONRead();
        
        //Highspeed
        json.param.stepWaitTime = 0L;
        json.param.loggingSW = false;

        //log mkdir
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH-mm-ss");
        String dirName = json.param.folderName+"\\"+sdf.format(date);
        File dir = new File(dirName);
        dir.mkdirs();
        //Copy Setting File
        try {
            Files.copy (new File(ABSSettings.settingFileName).toPath(), 
                        new File(dirName+"\\"+ABSSettings.settingFileName).toPath());
        } catch (IOException ex) {
        }
        
        //Time
        Long start = System.currentTimeMillis();
        System.out.println("Start Platooning ABS.");
        for(int i=0; i < json.param.numOfExec; i++){
            //Start
            Long execStart = System.currentTimeMillis();
            System.out.println("> Experiment:"+i);
            
            //File
            OutputInstance.NewFile(dirName+"\\exec_"+i+"_"+json.param.fileName);
            
            //Universe
            AmusementPark park = AmusementPark.getInstance();
            park.init();
            
            //Agent
            BusStops.generate();  //goal
            BusAgents.generate(); //agent
            
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
                    break;
                }
            }
            
            //Stop
            Long execStop = System.currentTimeMillis() - execStart;
            System.out.println("> Finish Experiment:"+i+" time="+execStop+"[ms]");
        }
        
        //Finish
        Long stop = System.currentTimeMillis() -  start;
        System.out.println("Stop Platooning ABS. Time : "+stop+" [ms]");
    }
}
