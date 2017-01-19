/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

/**
 *
 * @author 悠也
 */
public abstract class ABSSettings {
    //File
    public String fileName = "platooning_abs_log.txt";
    public String fieldName = "PID, Root, BusTime, QueueTime";
    public static Boolean consoleSW = true;
    public static Boolean loggingSW = true;
    
    //Visual
    public long renderTime = 200;
    
    //Executable
    public long stepWaitTime = 400; //[ms]
    
    //Environment
    public int colomn = 9;
    public int ROW = 9;
    
    //BusAgent
    public static int numBusAgents = 4;
    public int maxPassengers = 10;
    public enum type {human, robot};
    
    //BusStop
    public static int numBusStops = 5;
    public static int queuingByStep = 20;
    
    //People
    public static long seed = Integer.MAX_VALUE;
}
