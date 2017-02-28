/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

/**
 * ダイクストラ用のVertexクラス
 * @author murata
 */
public class BusStopVertex implements Comparable{
    public int to; //頂点の接続先　from -> Vertex -> to
    public int from; //頂点の接続元 
    public int cost; //接続コスト
    public BusStopVertex(int source, int target, int cost) {
        this.from = source;
        this.to = target;
        this.cost = cost;
    }
    
    public String toString(){
        return from+"->"+to+"["+cost+"]";
    }

    //接続コスト比較
    @Override
    public int compareTo(Object o) {
        BusStopVertex v = (BusStopVertex) o;
        return this.cost > v.cost ? 1 : (this.cost > v.cost ? -1 : 0);
    }
}
