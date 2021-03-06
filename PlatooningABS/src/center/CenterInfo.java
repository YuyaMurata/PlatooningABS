/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package center;

import java.util.List;
import java.util.Map;

/**
 * センターの送信情報
 * @author murata
 */
public class CenterInfo {
    private Map infoMap; //センター情報
    public static enum infoID{ //センター情報の管理ID
        ROOT_QUEUE, ROOT_STEP_QUEUE, ROOT_BUS, CALC_TIME
    }
    
    //センター情報をセット　ControlCenterで実行
    public void setInfo(Map info){
        this.infoMap = info;
    }
    
    //巡回バスの位置情報を取得
    public List getRootBus(Object root, String name){
        try{
            List busInfo = (List) ((Map<Object, List>)infoMap.get(infoID.ROOT_BUS)).get(root).get(0);
            Object check = busInfo.get(0);
            return busInfo;
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }
    
    //ルートの待ち人数を取得
    public Map<Object, List<Integer>> getRootQueue(){
        return (Map<Object, List<Integer>>) infoMap.get(infoID.ROOT_QUEUE);
    }
    
    //ルートの1ステップの待人数増加量を取得
    public Map<Object, List<Integer>> getRootStepQueue(){
        return (Map<Object, List<Integer>>) infoMap.get(infoID.ROOT_STEP_QUEUE);
    }
    
    //障害が発生 = true
    public Boolean isEmpty(){
        return infoMap == null;
    }
    
    public String toString(){
        if(infoMap == null) return "通信障害発生 !";
        return infoMap.toString();
    }
}
