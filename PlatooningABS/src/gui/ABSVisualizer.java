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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import obj.BusStop;
import park.AmusementPark;
import prop.ABSSettings;
import static prop.ABSSettings.json;

/**
 *
 * @author kaeru
 */
public class ABSVisualizer implements Runnable, ABSSettings{
    private static ABSVisualizer absImage = new ABSVisualizer();
    private ABSFrame frame = ABSFrame.getInstance();
    private ABSVisualizer(){
        //Frame
        frame.execute();
    }
    
    public static ABSVisualizer getInstance(){
        return absImage;
    }
    
    private AmusementPark park = AmusementPark.getInstance();
    private JPanel absArea;
    private Image screenImg;
    private Graphics screen;
    private int w, h;
    public void setABSArea(JPanel area){       
        //Init
        absArea = area;
        
        this.w = absArea.getWidth();
        this.h = absArea.getHeight();
        
        setVisualParameter();
        
        //Load AgentImage
        loadAgentImage();
        
        //ABS Area Image
        resizeABSArea();
    }
    
    public static Boolean state = false;
    public void resizeABSArea(){
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
    
    public void startVisualize(){
        setABSArea(frame.getABSPanel());
        
        //
        this.state = true;
        
        drawBusStops();
        drawBusAgents();
        drawCell();
        
        Thread thread = new Thread(this);
        thread.start();
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
    
    public void setVisualParameter(){
        this.col = json.param.column;
        this.row = json.param.row;
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
    
    private List<ImageIcon> busImg, origBusImg;
    private TreeMap<Integer, ImageIcon> busStopImg, origBusStopImg;
    private void loadAgentImage(){
        origBusImg = new ArrayList<>();
        for(String img : json.param.busIMG)
            origBusImg.add(new ImageIcon(img));
        
        origBusStopImg = new TreeMap<>();
        for(Integer th : json.param.busStopIMG.keySet())
            origBusStopImg.put(th, new ImageIcon(json.param.busStopIMG.get(th)));
    }
    
    private void resizeAgentImage(int imgw, int imgh){
        double scaleBus = (double)imgw / origBusImg.get(0).getIconWidth();
        
        int i=0;
        for(ImageIcon img : origBusImg){
            ImageIcon icon = new ImageIcon(img.getImage().getScaledInstance(
                (int)(img.getIconWidth() * scaleBus), 
                (int)(img.getIconHeight()* scaleBus), 
                Image.SCALE_SMOOTH));
            
            if(busImg == null) busImg = new ArrayList<>();
            if(!state) busImg.add(icon);
            else busImg.set(i++, icon);
            
        }
        
        double scaleBusStop = (double)imgh / origBusStopImg.get(0).getIconHeight();
        busStopImg = new TreeMap();
        for(Integer th : origBusStopImg.keySet())
            busStopImg.put(th, new ImageIcon(origBusStopImg.get(th).getImage().getScaledInstance(
                (int)(origBusStopImg.get(th).getIconWidth() * scaleBus), 
                (int)(origBusStopImg.get(th).getIconHeight()* scaleBus),
                Image.SCALE_SMOOTH)));
    }
    
    public void drawBusAgents(){
        int img_w = w / col/ 5;
        int img_h = h / row / 5;
        
        for(List<BusAgent> busAgents : park.getBusAgents()){
            int i = 0;
            for(BusAgent bus : busAgents){
                Point p = mappingArea(bus.x, bus.y);
                screen.drawImage(busImg.get(0).getImage(), p.x+i*img_h, p.y-img_h-+i*img_w, null);
                i++;
            }
        }
    }
    
    public void drawBusStops(){
        int img_w = w / col / 5;
        int img_h = h / row / 5;
        
        for(BusStop busStop : park.getBusStops()){
            Point p = mappingArea(busStop.x, busStop.y);
            screen.drawImage(
                busStopImg.ceilingEntry(busStop.getQueueLength()).getValue().getImage(),
                p.x, p.y-img_h, null);
        }
    }
    
    public void redrawABS(){
        screen.setColor(absArea.getBackground());
        screen.fillRect(0, 0, w, h);
        
        drawBusAgents();
        drawBusStops();
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
                Thread.sleep(json.param.renderTime);
            } catch (InterruptedException ex) {
            }
        }
    }
}
