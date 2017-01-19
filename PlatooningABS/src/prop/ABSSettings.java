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
    public static String fileName = "platooning_abs_log.txt";
    public static String fieldName = "PID, BUS, Root, BusTime, QueueTime";
    public static Boolean consoleSW = false;
    public static Boolean loggingSW = true;
    
    //Visual
    public long renderTime = 200; //[ms]
    
    //Executable
    public long stepWaitTime = 400; //[ms]
    
    //Environment
    public int colomn = 9;
    public int ROW = 9;
    
    //BusAgent
    public static int numBusAgents = 4;
    public static int maxPassengers = 10;
    public static enum type {human, robot};
    public static double lostProb = -1;
    public static Boolean commFailure = false;
    
    //BusStop
    public static int numBusStops = 5;
    public static int queuingByStep = 20;
    
    //People -1 = random else set seed
    public static long seed = -1;//Integer.MAX_VALUE;
    public static int amountPeople = 100;
}
