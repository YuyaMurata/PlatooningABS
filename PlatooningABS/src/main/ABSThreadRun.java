/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.abs.ABSVisualizeMain;

/**
 * ABSのスレッド実行
 * @author murata
 */
public class ABSThreadRun extends Thread{
    private PlatooningABSMain exec;
    private String file;
    private int num;
    
    public ABSThreadRun(String file, int num) {
        this.file = file;
        this.num = num;
    }
    
    private static PlatooningABSMain abs1;
    private static PlatooningABSExperiment abs2;
    
    public void run(){
        
        if(num == 1){
            abs1 = new PlatooningABSMain();
            abs1.run(file);
        }else{
            abs2 = new PlatooningABSExperiment();
            abs2.run(file);
        }
            
        System.out.println("Finish ABSMain Thread!");
        
        ABSVisualizeMain.closeWindow();
    }
    
    //強制終了
    public static void close(){
        if(abs1 != null)abs1.stop();
        if(abs2 != null)abs2.stop();
    }
}
