/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import agent.AbstractBusAgent;
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
 * ABSを可視化するためのクラス
 * ABSFrame から参照される
 * @author kaeru
 */
public class ABSVisualizer implements Runnable, ABSSettings{
    private static ABSVisualizer absImage = new ABSVisualizer();
    private ABSFrame frame = ABSFrame.getInstance();
    
    //遊園地クラスの取得
    private static AmusementPark park = AmusementPark.getInstance();
    
    public static Boolean state = false; //実行状態 true = 実行可能
    
    private JPanel absArea; //GUI Panel
    private Image screenImg; //ダブルバッファ用
    private Graphics screen; //ダブルバッファ用
    private int w, h; //width , Height
    private int col, row; //セルの行と列数
    
    //Agent & Object Image 用
    private List<ImageIcon> busImg, origBusImg;
    private TreeMap<Integer, ImageIcon> busStopImg, origBusStopImg;
    
    //Singleton
    private ABSVisualizer(){
        //Frame
        frame.execute();
    }
    
    public static ABSVisualizer getInstance(){
        return absImage;
    }
    
    //GUI Frame , Agent & Object Image, パラメータ初期化
    public void setABSArea(JPanel area){       
        //Init
        absArea = area;
        
        //ウィンドウサイズの取得
        this.w = absArea.getWidth();
        this.h = absArea.getHeight();
        
        //可視化パラメータの設定
        setVisualParameter();
        
        //Load AgentImage
        loadAgentImage();
        
        //ABS Area Image
        resizeABSArea();
    }
    
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
        
        //AgentImageを現在のウィンドウサイズに合わせる
        resizeAgentImage(img_w, img_h);
    }
    
    //可視化のスタート
    public void startVisualize(){
        //初期化
        setABSArea(frame.getABSPanel());
        
        //状態を実行可能に変更
        this.state = true;
        
        //ABSの状態を描画
        redrawABS();
        
        //可視化の実行
        Thread thread = new Thread(this);
        thread.start();
    }
    
    //可視化の終了
    public void stopVisualize(){
        state = false;
    }
    
    //ABS座標　→　フレーム座標　に変換
    public Point mappingArea(int pc, int pr){
        Point p = new Point();
        
        //e.g. X = Width / セルの全列数 * セルの列番号
        p.x = w / col * pc;
        p.y = h / row * pr;
        
        return p;
    }
    
    //パラメータ設定
    public void setVisualParameter(){
        this.col = json.param.column;
        this.row = json.param.row;
    }
    
    //セルの罫線を描画
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
    
    //Agent Imageの読み込み
    private void loadAgentImage(){
        //BusAgent Image
        origBusImg = new ArrayList<>();
        for(String img : json.param.busIMG)
            origBusImg.add(new ImageIcon(img));
        
        //BusStop Image
        origBusStopImg = new TreeMap<>();
        busStopImg = new TreeMap();
        for(Integer th : json.param.busStopIMG.keySet())
            origBusStopImg.put(th, new ImageIcon(json.param.busStopIMG.get(th)));
    }
    
    //Agent Image サイズ変更
    private void resizeAgentImage(int imgw, int imgh){
        //BusAgent Image Resize
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
        
        //BusStop Image Resize バスのスケールに合わすためウィンドウサイズによってはずれる
        for(Integer th : origBusStopImg.keySet())
            busStopImg.put(th, new ImageIcon(origBusStopImg.get(th).getImage().getScaledInstance(
                (int)(origBusStopImg.get(th).getIconWidth() * scaleBus), 
                (int)(origBusStopImg.get(th).getIconHeight()* scaleBus),
                Image.SCALE_SMOOTH)));
    }
    
    //BusAgent Image 描画
    public void drawBusAgents(){
        int img_w = w / col/ 5;
        int img_h = h / row / 5;
        
        //busImg.get(n) nを変更することでバスの画像を変更可能
        for(List<AbstractBusAgent> busAgents : park.getBusAgents()){
            int i = 0;
            for(AbstractBusAgent bus : busAgents){
                Point p = mappingArea(bus.x, bus.y);
                screen.drawImage(busImg.get(0).getImage(), p.x+i*img_h, p.y-img_h-+i*img_w, null);
                i++;
            }
        }
    }
    
    //BusStop Image 描画
    public void drawBusStops(){
        int img_w = w / col / 5;
        int img_h = h / row / 5;
        
        for(BusStop busStop : park.getBusStops()){
            Point p = mappingArea(busStop.x, busStop.y);
            screen.drawImage(
                busStopImg.floorEntry(busStop.getAllQueueLength()).getValue().getImage(),
                p.x, p.y-img_h, null);
        }
    }
    
    //ABSの描画
    public void redrawABS(){
        //描画の初期化
        screen.setColor(absArea.getBackground());
        screen.fillRect(0, 0, w, h);
        
        //Cell & Image 描画 
        drawBusAgents();
        drawBusStops();
        drawCell();
        
        //ダブルバッファ
        Graphics g = absArea.getGraphics();
        g.drawImage(screenImg, 0, 0, absArea);
        g.dispose();
    }
    
    //描画の非同期実行
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
