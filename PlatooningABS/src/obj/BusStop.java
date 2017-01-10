/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import park.AmusementPark;

/**
 *
 * @author kaeru
 */
public class BusStop {
    public String name, key;
    public int x, y;
    public BusStop(int index) {
        this.name = "BusStop_"+index;
        setBusStop(0, 0);
    }
    
    public void setBusStop(int x, int y){
        this.x = x;
        this.y = y;
        
        key = x+"-"+y;
        AmusementPark.getInstance().setBusStop(this);
    }
    
    public String toString(){
        return name+":[x="+x+" ,y="+y+"]";
    }
}
