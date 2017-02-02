/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import exec.StepExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import obj.BusStops;

/**
 *
 * @author murata
 */
public class RootManager{
    private static RootManager manager = new RootManager();
    
    Map<Object, List<String>> root = new HashMap<>();
    Map<Object, List<List>> candidatePath = new HashMap();
    
    public static RootManager getInstance(){
        return manager;
    }
    
    public void createRoot(Map<Object, List<String>> root){
        //root
        this.root = root;
        
        RootDijkstra path = new RootDijkstra();
        int[][] adjRoot = path.createAdjencyMatrix();
        
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
            
            candidatePath.put(BusStops.getBusStop(i).name, rootShort);
        }
        
        //Check
        for(Object key : candidatePath.keySet()){
            //System.out.println(key+":"+candidatePath.get(key));
            for(List l : candidatePath.get(key)){
                Object ra = "";
                for(int i=0; i < l.size()-1; i++){
                    Object r = getRoot((String)l.get(i), (String)l.get(i+1));
                    if(ra.equals(r)) l.remove((String)l.get(i));
                    ra = r;
                }
            }
            System.out.println(key+":"+candidatePath.get(key));
        }
    }
    
    public Object compareRoot(){
        Map<Object, Integer> compMap = new HashMap();
        for(Object rootNo : root.keySet()){
            int comp = 0;
            for(String busStopName : root.get(rootNo))
                comp = comp + BusStops.getBusStop(busStopName).getQueueLength(rootNo);
            compMap.put(rootNo, comp);
        }
        
        System.out.println(compMap);
        
        Entry<Object, Integer> v = compMap.entrySet().stream()
                                                    .max((e1,e2) -> e1.getValue() - e2.getValue()).get();
        
        return v.getKey();
    }
    
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
    
    //BusName -> Root
    public Object getRoot(String busName){
        int i = Math.abs(busName.hashCode()) % root.size();
        return root.keySet().toArray()[i];
    }
    
    //BusStopName -> Root
    public Object getRoot(String dept, String dest){
        Entry<Object,List<String>> v = root.entrySet().stream()
                                            .filter(e -> e.getValue().contains(dept) && e.getValue().contains(dest))
                                            .findFirst().get();
        
        return v.getKey();
    }
    
    public List getRootPath(Object rootNo){
        return root.get(rootNo);
    }
    
    public List<String> getCandidatePath(String bsName){
        int n = candidatePath.get(bsName).size();
        return (List<String>) candidatePath.get(bsName).get(rand.nextInt(n));
    }
    
    private static Random rand = new Random();
    public static void setSeed(long seed){
        if(seed != -1) rand.setSeed(seed);
    }
}
