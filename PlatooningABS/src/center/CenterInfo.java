/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package center;

import java.util.List;
import java.util.Map;

/**
 *
 * @author murata
 */
public class CenterInfo {
    public static enum paramID{
        ROOT_QUEUE, ROOT_STEP_QUEUE, ROOT_BUS, CALC_TIME
    }
    private Map param;
    
    public CenterInfo(){
    }
    
    public void setParam(Map param){
        this.param = param;
    }
    
    public Object[] getRootBus(Object root, String name){
        return (Object[]) ((Map<Object, List>)param.get(paramID.ROOT_BUS)).get(root).get(0);
    }
    
    public Map<Object, List<Integer>> getRootQueue(){
        return (Map<Object, List<Integer>>) param.get(paramID.ROOT_QUEUE);
    }
    
    public String toString(){
        return param.toString();
    }
}
