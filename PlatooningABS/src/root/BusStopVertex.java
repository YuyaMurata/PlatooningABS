/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

/**
 *
 * @author murata
 */
public class BusStopVertex implements Comparable{
    public int to;
    public int from;
    public int cost;
    public BusStopVertex(int source, int target, int cost) {
        this.from = source;
        this.to = target;
        this.cost = cost;
    }
    
    public String toString(){
        return from+"->"+to+"["+cost+"]";
    }

    @Override
    public int compareTo(Object o) {
        BusStopVertex v = (BusStopVertex) o;
        return this.cost > v.cost ? 1 : (this.cost > v.cost ? -1 : 0);
    }
}
