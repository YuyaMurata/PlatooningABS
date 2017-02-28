/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import java.util.List;
import java.util.TreeMap;
import obj.BusStop;
import obj.BusStops;
import obj.People;
import org.apache.commons.math3.random.RandomDataGenerator;
import queue.QueueType;

/**
 * 人気アトラクションのバス停に待ち行列を発生させるクラス
 * @author murata
 */
public class PopularAtractionQueue implements QueueType{
    private String name; //クラス名
    private RandomDataGenerator rand = new RandomDataGenerator(); //乱数
    private static Integer mu, sigma; //乱数の平均値と分散
    private TreeMap map = new TreeMap(); //バス停のインデックスと乱数値を関連付ける変数
    
    public PopularAtractionQueue(String name) {
        this.name = name;
        init();
        setSeed(-1);
    }
    
    //初期化
    private void init(){
        int n = BusStops.getNumBusStop();
        for(int i=0; i < n; i++)
            map.put(100/n*(i), i);
        
        System.out.println(name+"-"+map);
    }
    
    //待ち行列発生
    @Override
    public Object occureQueue(List<BusStop> busStops) {
        int i = (int)map.floorEntry(getGaussRandom()).getValue();
        
        BusStop bs = busStops.get(i);
        bs.queuePeople(new People(bs));
        
        return bs.name;
    }

    @Override
    public String getName() {
        return name;
    }

    //乱数のシード
    @Override
    public void setSeed(long seed) {
        mu = 50;
        sigma = 20;
        
        if(seed != -1)
            rand.reSeed(seed);
    }
    
    //正規分布に従い乱数を生成
    public Integer getGaussRandom(){
        int value = (int)rand.nextGaussian(mu, sigma);
        if(value > 100 || value < 0)
            value = rand.nextInt(0, 100);
        
        return value;
    }
}
