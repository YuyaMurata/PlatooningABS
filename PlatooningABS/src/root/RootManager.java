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
    
    /*
    private Long nstep = 0L;
    private Map<Object, Integer> ncompMap = new HashMap();
    private Entry<Object, Integer> vq;
    public Object compareRootQueue(){
        long step = StepExecutor.step;
        if(step == nstep){
            if(vq == null) return null;
            else return vq.getKey();
        }
        
        Map<Object, Integer> compMap = new HashMap();
        for(Object rootNo : root.keySet()){
            int comp = 0;
            for(String busStopName : root.get(rootNo))
                comp = comp + BusStops.getBusStop(busStopName).getStepQueue(rootNo).intValue();
            compMap.put(rootNo, comp);
        }
        
        System.out.println("comp="+compMap);
        
        // M/M/1 W= L/λ
        Map<Object, Integer> wmap = new HashMap();
        for(Object rootNo : ncompMap.keySet()){
            int l = compMap.get(rootNo);
            System.out.println("L="+l+" step="+step+", "+nstep);
            int w = (int) (l / (step - nstep));
            
            wmap.put(rootNo, w);
        }
        
        nstep = step;
        
        if(!wmap.isEmpty()){
            vq = wmap.entrySet().stream()
                                    .max((e1,e2) -> e1.getValue() - e2.getValue()).get();
            return vq.getKey();
        }else
            return null;
    }
    */
    
    //Root List
    public List getRootList(){
        return new ArrayList(root.keySet());
    }
    
    //BusName -> Root
    public Object getRoot(String busName){
        int i = Math.abs(busName.hashCode()) % root.size();
        return root.keySet().toArray()[i];
    }
    
    //BusStopName -> Root
    public Object getRoot(String dept, String dest){
        //始点，終点からルートを検索
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
    
    //経路候補取得時の乱数
    public static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
}
