/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import java.util.List;
import obj.BusStop;

/**
 * キュー生成用抽象クラス
 * @author murata
 */
public interface QueueType {
    //キュー発生
    public abstract Object occureQueue(List<BusStop> busStops);
    
    public abstract String getName();
    
    //キュー生成時の乱数のシード
    public abstract void setSeed(long seed);
    
    @Override
    public String toString();
}
