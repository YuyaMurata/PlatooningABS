/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import obj.BusStop;

/**
 * ABSパラメータ
 * JSONで読み込まれるため，このクラスを書き換えても反映されない
 * @author murata
 */
public class ABSParameter {
    //File
    public String folderName = "experiments"; //繰り返し実験用のフォルダ 
    public String peopleFileName = "platooning_abs_log.txt"; //発生した人の行動ログ
    public String peopleFieldName = "PID, BUS, Root, BusTime, QueueTime"; //人の行動ログの列名
    public String traceFileName = "platooning_abs_tracelog.txt"; //トレースログ
    public String traceFieldName = //トレースログの列名
            "STEP, BUS(name, type, x, y, n.pass, root, leader), BUSSTOP(name, x, y, queue)";
    public Boolean consoleSW = false; //コンソール表示の切り替え
    public Boolean loggingSW = true; //ログファイルへの出力切り替え
    public Boolean traceSW = true; //トレースログの出力切り替え
    
    //Visual
    public long renderTime = 200; //GUIの表示間隔 [ms]
    //バス停の画像 待ち人数で色を変更可能 put(待ち人数，バス停の色)
    public Map<Integer, String> busStopIMG = new HashMap(){
        {put(0, "./img/busstop/s_137785.png");}
        {put(10, "./img/busstop/s_137785b.png");}
        {put(20, "./img/busstop/s_137785lb.png");}
        {put(30, "./img/busstop/s_137785g.png");}
        {put(40, "./img/busstop/s_137785y.png");}
        {put(50, "./img/busstop/s_137785o.png");}
        {put(60, "./img/busstop/s_137785r.png");}
        };
    //バス画像　色を変更可能(ABSVisualizerで編集する必要あり)
    public List<String> busIMG = Arrays.asList(
            "./img/bus/s_121278.png",
            "./img/bus/s_121278r.png",
            "./img/bus/s_121278b.png",
            "./img/bus/s_121278g.png",
            "./img/bus/s_121278y.png"
            );
    
    //Executable
    public int numOfExec = 10; //繰り返し実験の回数
    public long stepWaitTime = 400; //[ms] 1ステップの時間
    
    //Environment　セル[col×row]
    public int column = 9;
    public int row = 9;
    
    //BusAgent
    public int numBusAgents = 4; //バス台数
    public int numHuman = 2; //有人のバス台数
    public int maxPassengers = 10; //バスの最大乗車数 
    public double lostProb = -1; //無人バスの迷子率
    public Boolean changeLineSW = true; //隊列変更のあり=true，なし
    
    //Bus Root
    public Map<Object, List<String>> root = new HashMap(){
        {put("root0", Arrays.asList("BusStop_0", "BusStop_1", "BusStop_4"));}
        {put("root1", Arrays.asList("BusStop_2", "BusStop_3", "BusStop_4"));}
    };
    
    //BusStop
    public int numBusStops = 5; //バス停数
    public int queuingByStep = 20; //1ステップでのバス停の最大待ち発生数
    public List<BusStop> busStops = Arrays.asList(
            new BusStop("BusStop_0",0, 0),
            new BusStop("BusStop_1",0, 8),
            new BusStop("BusStop_2",8, 8),
            new BusStop("BusStop_3",8, 0),
            new BusStop("BusStop_4",4, 4)
        );
    
    //People -1 = random else set seed
    public long seed = -1;// ABS内で利用される全乱数のSeed
    public int amountPeople = 100; //環境で発生する最大人数
    
    //Accident
    public Boolean failureSW = true; //障害のあり=true，なし
    public long accidentPeriod = 10L; //障害の発生間隔
    public long accidentTime = 2L; //障害の持続時間
    
    //パラメータの出力
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " = " + field.get(this) + "\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
        
        return sb.toString();
    }
}
