/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author kaeru
 */
public class ABSVisualizer {
    private static ABSVisualizer absImage = new ABSVisualizer();
    public static ABSVisualizer getInstance(){
        return absImage;
    }
    
    private JPanel absArea;
    private int w, h;
    public void setABSArea(JPanel area){
        //delay for paint
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        
        absArea = area;
        
        this.w = absArea.getWidth();
        this.h = absArea.getHeight();
        
        this.state = true;
    }
    
    public Point mappingArea(int pc, int pr){
        Point p = new Point();

        p.x = w / col * pc;
        p.y = h / row * pr;
        
        return p;
    }
    
    public void setDrawParameter(int col, int row){
        this.col = col;
        this.row = row;
    }
    
    private int col, row;
    public void drawCells(){
        
        Graphics g = absArea.getGraphics();
        
        //Column
        for(int i=0; i < col; i++){
            Point p = mappingArea(i, 0);
            g.drawLine(p.x, 0, p.x, h);
        }
        
        //Row
        for(int i=0; i < row; i++){
            Point p = mappingArea(0, i);
            g.drawLine(0, p.y, w, p.y);
        }
        
        g.dispose();
    }
    
    private Boolean state = false;
    public void redrawABS(){
        if(!state) return;
        
        this.w = absArea.getWidth();
        this.h = absArea.getHeight();
        
        drawCells();
    }
}
