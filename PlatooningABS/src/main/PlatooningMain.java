/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.ABSFrame;
import gui.ABSVisualizer;

/**
 *
 * @author kaeru
 */
public class PlatooningMain {
    public static void main(String[] args) {
        
        //GUI Start
        ABSFrame frame = ABSFrame.getInstance();
        frame.execute(args);
        
        ABSVisualizer abs = ABSVisualizer.getInstance();
        abs.setDrawParameter(10, 10);
        abs.drawCells();
    }
}
