/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package center;

import java.util.Map;

/**
 *
 * @author murata
 */
public class CenterInfo {
    public static enum paramID{
        ROOT_QUEUE, ROOT_STEP_QUEUE, BUS_POS, CALC_TIME
    }
    private Map param;
    public CenterInfo(){
    }
    
    public void setParam(Map param){
        this.param = param;
    }
    
    public String toString(){
        return param.toString();
    }
}
