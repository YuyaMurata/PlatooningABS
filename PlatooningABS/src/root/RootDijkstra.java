/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import obj.BusStop;
import obj.BusStops;
import prop.ABSSettings;

/**
 *
 * @author murata
 */
public class RootDijkstra implements ABSSettings{
    public int[][] createAdjencyMatrix(){
        int n = json.param.busStops.size();
        int[][] matrix = new int[n][n];
        
        //Init
        for(int i=0; i < matrix.length; i++)
            for(int j=0; j < matrix[i].length; j++)
                matrix[i][j] = 0;
        
        //Create
        for(Object rootNo : json.param.root.keySet()){
            List<String> bsNameList = json.param.root.get(rootNo);
            for(int i=0; i < bsNameList.size(); i++){
                BusStop dept = BusStops.getBusStop(bsNameList.get(i));
                BusStop dest = BusStops.getBusStop(bsNameList.get((i+1)%bsNameList.size()));
                
                int m = Integer.valueOf(dept.name.split("_")[1]);
                int k = Integer.valueOf(dest.name.split("_")[1]);
                
                //Euclid Distance
                if(matrix[m][k] == 0) matrix[m][k] = Math.max(Math.abs(dept.x - dest.x), Math.abs(dept.y - dest.y));
                if(matrix[k][m] == 0) matrix[k][m] = Math.max(Math.abs(dest.x - dept.x), Math.abs(dest.y - dept.y));                
            }
        }
        
        //Check
        for(int i=0; i < matrix.length; i++){
            for(int j=0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j]+" ");
            System.out.println();
        }
        
        return matrix;
    }
    
    public BusStopVertex[] dijkstra(int[][] adj, int src){
        PriorityQueue pq = new PriorityQueue<BusStopVertex>();
        
        int[] dist = new int[adj.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        pq.add(new BusStopVertex(src, src, 0)); //start
        
        BusStopVertex[] path = new BusStopVertex[adj.length];
        
        while(!pq.isEmpty()){
            BusStopVertex done = (BusStopVertex) pq.poll();
            if(dist[done.to] < done.cost) continue;
            //System.out.println("Done : "+done);
            
            //Root
            path[done.to] = done;
            
            for(int i=0; i < adj[done.to].length; i++){
                BusStopVertex next = new BusStopVertex(done.to, i, adj[done.to][i]);
                if(next.cost == 0) continue;
                //System.out.println("To : "+next);
                
                if(dist[next.to] > dist[done.to] + next.cost){
                    dist[next.to] = dist[done.to] + next.cost;
                    next.cost = dist[next.to];
                    pq.offer(next);
                    
                    //path.add(next);
                }
            }
        }
        
        //[[0, 0]=0, [4, 0]=4, [1, 0]=8, [3, 4]=8, [2, 4]=8]
        //System.out.println(path);
        
        return path;
    }
    
    public List<BusStopVertex> getShortestPath(BusStopVertex[] path, int goal){
        List<BusStopVertex> shortPath = new ArrayList<>();
        
        BusStopVertex v = path[goal];
        while(true){
            shortPath.add(v);
            if(!v.equals(path[v.from]))
                v = path[v.from];
            else break;
        }
        
        Collections.reverse(shortPath);
        return shortPath;
    }
    
    public static void main(String[] args) {
        json.absJSONRead();
        BusStops.generate();
        
        RootDijkstra dijk = new RootDijkstra();
        int[][] mat = dijk.createAdjencyMatrix();
        
        BusStopVertex[] path = dijk.dijkstra(mat, 0);
        
        System.out.println(dijk.getShortestPath(path, 3));
    }
}
