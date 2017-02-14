/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Point;

/**
 *
 * @author murata
 */
public class PointTest {
    public static void main(String[] args) {
        Integer x = 0, y = 0;
        Point p = new Point(x, y);
        
        System.out.println(p);
        
        x = 1; y= 2;
        
        System.out.println(p);
    }
}
