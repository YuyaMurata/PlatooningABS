/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package center;

import prop.ABSSettings;

/**
 * 周期的にバス-センター間の通信を切断　切断間隔=period 切断時間=actTime
 * @author murata
 */
public class CenterAccident implements ABSSettings{
    private Long period, actTime, time;
    private Boolean failure;
    
    public CenterAccident() {
        this.period = json.param.accidentPeriod;
        this.actTime = json.param.accidentTime;
        this.failure = false;
        this.time = 0L;
    }
    
    //障害発生 failure = true 障害発生
    public Boolean accident(Long step){
        if(!json.param.failureSW) return false;
        
        //障害発生
        if(step % period == 0){
            time = 0L;
            failure = true;
        }
        
        //actTime間 障害を維持
        if(time++ > actTime && failure) failure = false;
        
        //System.out.println("Accident = "+failure);
        
        return failure;
    }
}
