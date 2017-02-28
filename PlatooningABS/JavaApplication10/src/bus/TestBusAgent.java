/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus;

import agent.AbstractBusAgent;
import center.CenterInfo;
import java.awt.Point;

/**
 *
 * @author murata
 */
public class TestBusAgent extends AbstractBusAgent{
    public TestBusAgent(String name, String type) {
        super(name, type);
    }
    
    @Override
    public void init(String string) {
        System.out.println("Test:"+super.toString());
    }

    @Override
    public Point planning(CenterInfo ci) {
        System.out.println("Test:"+super.toString());
        return new Point(9, 9);
    }

    @Override
    public Object root() {
        return "";
    }
}
