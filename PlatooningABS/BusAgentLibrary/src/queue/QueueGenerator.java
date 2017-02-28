/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import java.util.List;
import obj.BusStop;

/**
 * キュー生成用クラス(ストラテジーパターン)
 * @author murata
 */
public class QueueGenerator {
    private QueueType type; //キューの生成パターン
    
    public QueueGenerator(QueueType type){
        this.type = type;
    }
    
    //バス停リストを渡すことでキューを発生
    public void generate(List<BusStop> busStops){
        type.occureQueue(busStops);
    }
    
    public String getName(){
        return type.getName();
    }
    
    public void setSeed(long seed){
        type.setSeed(seed);
    }
}
