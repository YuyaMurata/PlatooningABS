/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import obj.BusStops;

/**
 * バス停間のルート管理用のクラス
 * @author murata
 */
public class RootManager{
    private static RootManager manager = new RootManager();
    private static Random rand = new Random();
    
    private Map<Object, List<String>> root = new HashMap<>(); //ルート管理
    private Map<Object, List<List>> candidatePath = new HashMap(); //ルート候補の管理
    
    //Singleton
    public static RootManager getInstance(){
        return manager;
    }
    
    //移動経路の作成
    public void createRoot(Map<Object, List<String>> root){
        //root
        this.root = root;
        
        //ダイクストラ法の準備
        RootDijkstra path = new RootDijkstra();
        int[][] adjRoot = path.createAdjencyMatrix();
        
        //すべてのバス停を始点，終点とした最短経路を計算
        for(int i=0; i < adjRoot.length; i++){
            List rootShort = new ArrayList();
            BusStopVertex[] busStopPath = path.dijkstra(adjRoot, i);
            for(int j=0; j < adjRoot.length; j++){
                if(i == j) continue;
                
                //Vertex -> BusStopName
                List rootBusStopNames = new ArrayList();
                for(BusStopVertex v : path.getShortestPath(busStopPath, j))
                    rootBusStopNames.add(BusStops.getBusStop(v.to).name);
                
                rootShort.add(rootBusStopNames);      
            }
            
            //経路候補の保存
            candidatePath.put(BusStops.getBusStop(i).name, rootShort);
        }
        
        //最短経路の道順を保存
        for(Object key : candidatePath.keySet()){
            //System.out.println(key+":"+candidatePath.get(key));
            for(List l : candidatePath.get(key)){
                Object rootName = "";
                for(int i=0; i < l.size()-1; i++){
                    Object r = getRoot((String)l.get(i), (String)l.get(i+1));
                    if(rootName.equals(r)) l.remove((String)l.get(i));
                    rootName = r;
                }
            }
            System.out.println(key+":"+candidatePath.get(key));
        }
    }
    
    //Rootリストを取得
    public List getRootList(){
        return new ArrayList(root.keySet());
    }
    
    //BusNameからRootを取得
    public Object getRoot(String busName){
        int i = Math.abs(busName.hashCode()) % root.size();
        return root.keySet().toArray()[i];
    }
    
    //BusStopNameからRootを取得
    public Object getRoot(String dept, String dest){
        //始点，終点が属するルートを検索
        Object rootName = root.entrySet().stream()
                                            .filter(e -> e.getValue().contains(dept) && e.getValue().contains(dest))
                                            .findFirst().get().getKey();
        
        return rootName;
    }
    
    //巡回バスの経路を取得
    public List getRootPath(Object rootNo){
        return root.get(rootNo);
    }
    
    //人の目的地までの経路候補を取得
    public List<String> getCandidatePath(String bsName){
        int n = candidatePath.get(bsName).size();
        return (List<String>) candidatePath.get(bsName).get(rand.nextInt(n));
    }
    
    //人の経路候補取得時の乱数
    public static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
}
