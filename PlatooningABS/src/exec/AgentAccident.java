/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exec;

import prop.ABSSettings;

/**
 * 周期的にバス-センター間の通信を切断　切断間隔=period 切断時間=actTime
 * @author murata
 */
public class AgentAccident implements ABSSettings{
    private Long period, actTime, time;
    private Boolean failure;
    
    public AgentAccident(Long period, Long actTime) {
        this.period = period;
        this.actTime = actTime;
        this.failure = false;
    }
    
    public Boolean accident(Long step){
        if(!json.param.commFailure) return false;
        
        if(step % period == 0){
            time = 0L;
            failure = true;
        }
        
        if(time++ < actTime) failure = true;
        else failure = false;
        
        return !failure;
    }
}
