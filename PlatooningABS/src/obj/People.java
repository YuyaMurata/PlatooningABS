/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import exec.StepExecutor;
import fileout.OutputInstance;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import root.RootManager;

/**
 * 環境に発生する人クラス
 * @author murata
 */
public class People {
    private enum paramID{ //パラメータID
        PID, BUS, ROOT, BTIME, QTIME
    }
    public static long pid; //PeopleID
    private long waitTime, getTime; //待ち時間，乗車時間
    public LinkedList path = new LinkedList<>(); //移動経路の管理
    private Map log = new LinkedHashMap(); //人の行動ログ管理
    private static Random rand = new Random(); //乱数
    private Object dep; //出発地点
    
    public People(BusStop deptBusStop){
        //People ID
        pid++;
        
        //候補経路の取得
        path.addAll(RootManager.getInstance().getCandidatePath(deptBusStop.name));
        
        //ログの初期化
        log.put(paramID.PID, "people-"+pid);
        log.put(paramID.BUS, "bus");
        
        //出発地点の登録
        dep = path.poll();
        log.put(paramID.ROOT, dep);
    }
    
    //待ち時間の測定
    public void queueTime(){
        this.waitTime = StepExecutor.step;
    }
    
    //乗車時間の測定
    public void getOnTime(String busName){
        waitTime = StepExecutor.step - waitTime;
        getTime = StepExecutor.step;
        log.put(paramID.BUS, log.get(paramID.BUS)+"-"+busName);
    }
    
    //降車の確認処理
    public Boolean getOffCheck(BusStop location){
        //目的地の取得
        Object destination = path.peek();
        
        //現在地 = 目的地か判定
        if(destination.equals(location.name)){
            //乗車時間の計算
            getTime = StepExecutor.step - getTime;
            
            //ログの書き込み
            if(log.get(paramID.BTIME) != null){
                getTime = getTime + (Long)log.get(paramID.BTIME);
                waitTime = waitTime + (Long)log.get(paramID.QTIME);
            }
            log.put(paramID.BTIME, getTime);
            log.put(paramID.QTIME, waitTime);
            log.put(paramID.ROOT, log.get(paramID.ROOT)+"->"+destination);
            
            //現在地が最終目的地か確認
            dep = path.poll();
            if(!path.isEmpty()) location.queuePeople(this);
            else printLog();
            
            return true;
        }
        return false;
    }
    
    //目的地の取得
    public String getDestination(){
        return (String) path.peek();
    }
    
    //出発地の取得
    public String getDeprture(){
        return (String) dep;
    }
    
    //乱数の取得
    public static void setRandom(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
    
    //ログの出力
    public void printLog(){
        OutputInstance.dataPeopleLog.write(log.values().toString());
    }
}
