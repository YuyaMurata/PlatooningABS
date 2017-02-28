/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

/**
 *
 * @author murata
 */
public class BusAgent {
    public String name, type, clazz, param;

    public BusAgent(String name, String type, String clazz, String param) {
        this.name = name;
        this.type = type;
        this.clazz = clazz;
        this.param = param;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getClazz(){
        return clazz.replace(".class", "");
    }
    
    public void setClazz(String clazz){
        this.clazz = clazz;
    }
    
    public String getParam(){
        return param;
    }
    
    public void setParam(String param){
        this.param = param;
    }

    public String toString(){
        return name+"<"+type+">:"+getClazz()+"("+getParam()+")";
    }
}
