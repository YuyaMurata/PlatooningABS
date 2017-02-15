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
 * ルートの最短移動経路を計算するクラス(ダイクストラ法)
 * @author murata
 */
public class RootDijkstra implements ABSSettings{
    //バス停のルート情報を隣接行列に変換
    public int[][] createAdjencyMatrix(){
        int n = json.param.busStops.size();
        int[][] matrix = new int[n][n];
        
        //Init 隣接行列の初期化
        for(int i=0; i < matrix.length; i++)
            for(int j=0; j < matrix[i].length; j++)
                matrix[i][j] = 0;
        
        //隣接行列の作成
        for(Object rootNo : json.param.root.keySet()){
            List<String> bsNameList = json.param.root.get(rootNo);
            for(int i=0; i < bsNameList.size(); i++){
                BusStop dept = BusStops.getBusStop(bsNameList.get(i));
                BusStop dest = BusStops.getBusStop(bsNameList.get((i+1)%bsNameList.size()));
                
                //バス停名をインデックスに変換
                int m = Integer.valueOf(dept.name.split("_")[1]);
                int k = Integer.valueOf(dest.name.split("_")[1]);
                
                //バス停間の距離計算　Euclid Distance
                if(matrix[m][k] == 0) matrix[m][k] = Math.max(Math.abs(dept.x - dest.x), Math.abs(dept.y - dest.y));
                if(matrix[k][m] == 0) matrix[k][m] = Math.max(Math.abs(dest.x - dept.x), Math.abs(dest.y - dept.y));                
            }
        }
        
        //隣接行列の確認
        for(int i=0; i < matrix.length; i++){
            for(int j=0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j]+" ");
            System.out.println();
        }
        
        return matrix;
    }
    
    //ダイクストラ法
    public BusStopVertex[] dijkstra(int[][] adj, int src){
        //初期化
        PriorityQueue<BusStopVertex> pq = new PriorityQueue();
        int[] dist = new int[adj.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        pq.add(new BusStopVertex(src, src, 0)); //start
        
        BusStopVertex[] path = new BusStopVertex[adj.length];
        
        //ダイクストラ法計算
        while(!pq.isEmpty()){
            BusStopVertex done = (BusStopVertex) pq.poll();
            if(dist[done.to] < done.cost) continue;
            
            //経路が確定したバス停を保存
            path[done.to] = done;
            
            for(int i=0; i < adj[done.to].length; i++){
                BusStopVertex next = new BusStopVertex(done.to, i, adj[done.to][i]);
                if(next.cost == 0) continue;
                
                if(dist[next.to] > dist[done.to] + next.cost){
                    dist[next.to] = dist[done.to] + next.cost;
                    next.cost = dist[next.to];
                    pq.offer(next);
                }
            }
        }
        
        return path;
    }
    
    //最短経路を取得
    public List<BusStopVertex> getShortestPath(BusStopVertex[] path, int goal){
        List<BusStopVertex> shortPath = new ArrayList<>();
        
        //終点から始点までの経路を取り出す
        BusStopVertex v = path[goal];
        while(true){
            shortPath.add(v);
            if(!v.equals(path[v.from]))
                v = path[v.from];
            else break;
        }
        
        //順番を反転し始点から終点までの経路順とする
        Collections.reverse(shortPath);
        return shortPath;
    }
    
    /* テスト
    public static void main(String[] args) {
        json.absJSONRead();
        BusStops.generate();
        
        RootDijkstra dijk = new RootDijkstra();
        int[][] mat = dijk.createAdjencyMatrix();
        
        BusStopVertex[] path = dijk.dijkstra(mat, 0);
        
        System.out.println(dijk.getShortestPath(path, 3));
    }*/
}
