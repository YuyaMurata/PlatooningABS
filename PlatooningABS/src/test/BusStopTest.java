/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import obj.BusStop;

/**
 *
 * @author 悠也
 */
public class BusStopTest {
    public static void main(String[] args) {
        BusStop bs1,bs2,bs3, temp;
        bs1 = new BusStop("bs0",0,0);
        bs2 = new BusStop("bs1",0,0);
        bs3 = new BusStop("bs2",0,0);
        
        System.out.println(bs1);
        System.out.println(bs2);
        System.out.println(bs3);
        
        temp = bs1;
        bs1 = bs2;
        bs2 = temp;
        
        temp = null;
        
        System.out.println(bs1);
        System.out.println(bs2);
        System.out.println(bs3);
        
    }
}
