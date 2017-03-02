/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
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
    
    public void run(){
        
        if(num == 1)
            new PlatooningABSMain().run(file);
        else
            new PlatooningABSExperiment().run(file);
        
        System.out.println("Finish ABSMain Thread!");
    }
}
