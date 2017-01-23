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
        
        //Trace Log ON
        json.param.traceSW = true;

        //log mkdir
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH-mm-ss");
        String dirName = json.param.folderName+"\\"+sdf.format(date)+"_"+json.param.numOfExec;
        new File(dirName).mkdirs();
        OutputInstance.NewFileSummary(dirName+"\\"+"summary.txt");
        
        String dirTraceName = dirName+"\\trace";
        new File(dirTraceName).mkdirs();
        String dirLogName = dirName+"\\log";
        new File(dirLogName).mkdirs();
        
        //Copy Setting File
        try {
            Files.copy (new File(ABSSettings.settingFileName).toPath(), 
                        new File(dirName+"\\"+ABSSettings.settingFileName).toPath());
        } catch (IOException ex) {
        }
        
        
        //Time
        Long start = System.currentTimeMillis();
        Long totalStep = 0L;
        System.out.println("Start Platooning ABS.");
        for(int i=0; i < json.param.numOfExec; i++){
            //Start
            Long execStart = System.currentTimeMillis();
            System.out.println("> Experiment:"+i);
            
            //File
            OutputInstance.NewFilePeopleLog(dirTraceName+"\\exec_"+i+"_"+json.param.fileName);
            OutputInstance.NewFileTraceLog(dirLogName+"\\exec_"+i+"_"+json.param.traceFileName);
            
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
                    OutputInstance.dataPeopleLog.write("Finish Steps, "+time);
                    System.out.println("Finish Steps, "+time);
                    totalStep += time;
                    OutputInstance.close();
                    break;
                }
            }
            
            //Stop
            Long execStop = System.currentTimeMillis() - execStart;
            System.out.println("> Finish Experiment:"+i+" time="+execStop+"[ms]");
            OutputInstance.dataSummary.write("Experiment:"+i+", "+time+", "+execStop+", ms");
        }
        
        //Finish
        Long stop = System.currentTimeMillis() -  start;
        System.out.println("Stop Platooning ABS. Time : "+stop+" [ms]");
        
        Long avg = totalStep / json.param.numOfExec;
        OutputInstance.dataSummary.write("Platooning ABS Total Experiment:"+json.param.numOfExec+", "+avg+", "+stop+", ms");
        OutputInstance.dataSummary.close();
    }
}
