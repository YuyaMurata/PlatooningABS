/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import agent.BusAgent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import obj.BusStop;

/**
 *
 * @author kaeru
 */
public class ABSVisualizer extends Thread{
    private static ABSVisualizer absImage = new ABSVisualizer();
    public static ABSVisualizer getInstance(){
        return absImage;
    }
    
    private JPanel absArea;
    private Image screenImg;
    private Graphics screen;
    private int w, h;
    public void setABSArea(JPanel area){       
        //Init
        absArea = area;
        
        this.w = absArea.getWidth();
        this.h = absArea.getHeight();
        
        //Load AgentImage
        loadAgentImage();
        
        //ABS Area Image
        resizeABSArea();
        
        this.state = true;
    }
    
    private List<BusAgent> busList;
    private List<BusStop> busstopList;
    public void setAgent(List<BusAgent> busList, List<BusStop> busstopList){
        this.busList = busList;
        this.busstopList = busstopList;
    }
    
    public void startVisualize(){
        drawBusStops(busstopList);
        drawBusAgents(busList);
        drawCell();
        
        this.start();
    }
    
    public void stopVisualize(){
        state = false;
    }
    
    
    public Point mappingArea(int pc, int pr){
        Point p = new Point();

        p.x = w / col * pc;
        p.y = h / row * pr;
        
        return p;
    }
    
    public void setVisualParameter(int col, int row){
        this.col = col;
        this.row = row;
    }
    
    private int col, row;
    public void drawCell(){
        screen.setColor(Color.BLACK);
        
        //Column
        for(int i=0; i < col; i++){
            Point p = mappingArea(i, 0);
            screen.drawLine(p.x, 0, p.x, h);
        }
        
        //Row
        for(int i=0; i < row; i++){
            Point p = mappingArea(0, i);
            screen.drawLine(0, p.y, w, p.y);
        }
    }
    
    private ImageIcon busImg, busStopImg, origBusImg, origBusStopImg;
    private void loadAgentImage(){
        origBusImg = new ImageIcon("./img/bus/s_121278.png");
        origBusStopImg = new ImageIcon("./img/busstop/s_137785.png");
    }
    
    private void resizeAgentImage(int imgw, int imgh){
        double scale = (double)imgw / origBusImg.getIconWidth();
        
        busImg = new ImageIcon(origBusImg.getImage().getScaledInstance(
                (int)(origBusImg.getIconWidth() * scale), 
                (int)(origBusImg.getIconHeight()* scale), 
                Image.SCALE_SMOOTH));
        
        busStopImg = new ImageIcon(origBusStopImg.getImage().getScaledInstance(
                (int)(origBusStopImg.getIconWidth() * scale), 
                (int)(origBusStopImg.getIconHeight()* scale),
                Image.SCALE_SMOOTH));
    }
    
    public void drawBusAgents(List<BusAgent> busList){
        for(BusAgent bus : busList){
            Point p = mappingArea(bus.x, bus.y);
            screen.drawImage(busImg.getImage(), p.x, p.y-5, null);
        }
    }
    
    public void drawBusStops(List<BusStop> busStopList){
        for(BusStop busStop : busStopList){
            Point p = mappingArea(busStop.x, busStop.y);
            screen.drawImage(busStopImg.getImage(), p.x, p.y-5, null);
        }
    }
    
    public void resizeABSArea(){
        if(!state) return ;
        
        //Get Size
        w = absArea.getWidth();
        h = absArea.getHeight();
        
        //off screen
        screenImg = absArea.createImage(w, h);
        screen = screenImg.getGraphics();
        
        //Image size
        int img_w = w / col;
        int img_h = h / row;
        
        resizeAgentImage(img_w, img_h);
    }
    
    private Boolean state = false;
    public void redrawABS(){
        if(!state) return;
        
        screen.setColor(absArea.getBackground());
        screen.fillRect(0, 0, w, h);
        
        drawBusAgents(busList);
        drawBusStops(busstopList);
        drawCell();
        
        Graphics g = absArea.getGraphics();
        g.drawImage(screenImg, 0, 0, absArea);
        g.dispose();
    }
    
    public void run(){
        while(state){
            redrawABS();
            
            try {
                //Sleep
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
    }
}