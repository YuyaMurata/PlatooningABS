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
    
    public AgentAccident(Long period, Long actTime) {
        this.period = period;
        this.actTime = actTime;
    }
    
    public void accident(Long step){
        if(step % period == 0){
            json.param.commFailure = true;
            time = 0L;
        }else{
            if(time++ > actTime)
                json.param.commFailure = false;
        }
    }
}
