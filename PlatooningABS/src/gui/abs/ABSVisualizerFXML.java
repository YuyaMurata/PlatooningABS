/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.abs;

import agent.AbstractBusAgent;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import obj.BusStop;
import park.AmusementPark;

/**
 * ABSを可視化するためのクラス
 * @author kaeru
 */
public class ABSVisualizerFXML{
    private Canvas canvas;
    
    //遊園地クラスの取得
    private static AmusementPark park = AmusementPark.getInstance();
    private int w, h; //width , Height
    private int col, row; //セルの行と列数
    
    //Agent & Object Image 用
    private List<Image> origBusImg;
    private TreeMap<Integer, Image> origBusStopImg;
    
    public ABSVisualizerFXML(Canvas canvas){    
        this.canvas = canvas;
    }
    
    //パラメータ初期化
    public void setABSVisualParam(int col, int row, List<String> busImg, Map<Integer, String> busStopImg){
        //ウィンドウサイズの取得
        this.w = (int)canvas.getWidth();
        this.h = (int)canvas.getHeight();
        
        this.col = col;
        this.row = row;
        
        //Load AgentImage
        loadAgentImage(busImg, busStopImg);
    }
    
    //ABS座標　→　フレーム座標　に変換
    public Point mappingArea(int cellX, int cellY){
        Point p = new Point();
        
        //e.g. X = Width / セルの全列数 * セルの列番号
        p.x = w / col * cellX;
        p.y = h / row * cellY;
        
        return p;
    }
    
    //セルの罫線を描画
    public void drawCell(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        g.setStroke(Color.BLACK);
        //Column
        for(int i=0; i < col; i++){
            Point p = mappingArea(i, 0);
            g.strokeLine(p.x, 0, p.x, h);
        }
        
        //Row
        for(int i=0; i < row; i++){
            Point p = mappingArea(0, i);
            g.strokeLine(0, p.y, w, p.y);
        }
    }
    
    //Agent Imageの読み込み
    private void loadAgentImage(List<String> busImg, Map<Integer, String> busStopImg){
        //BusAgent Image
        origBusImg = new ArrayList<>();
        for(String img : busImg){
            try{
                InputStream in = new BufferedInputStream(new FileInputStream(img));
                origBusImg.add(new Image(in));
            }catch(Exception e){
                origBusImg.add(null);
            }
        }
        
        //BusStop Image
        origBusStopImg = new TreeMap<>();
        for(Integer th : busStopImg.keySet()){
            try{
                InputStream in = new BufferedInputStream(new FileInputStream(busStopImg.get(th)));
                origBusStopImg.put(th, new Image(in));
            }catch(Exception e){
                origBusStopImg.put(th, null);
            }
        }
    }
    
    //BusAgent Image 描画
    public void drawBusAgents(){
        int sizeX = w / col;
        int sizeY = h / row;
        if(origBusImg.get(0) != null)
            sizeY = (int) (origBusImg.get(0).getHeight() * sizeX / origBusImg.get(0).getWidth());
        
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        //busImg.get(n) nを変更することでバスの画像を変更可能
        for(List<AbstractBusAgent> busAgents : park.getBusAgents()){
            int i = 0;
            for(AbstractBusAgent bus : busAgents){
                Point p = mappingArea(bus.x, bus.y);
                if(origBusImg != null){
                    if(origBusImg.get(0) != null)
                        g.drawImage(origBusImg.get(0), p.x + i*sizeX/5, p.y - i*sizeY/5, sizeX, sizeY);
                    else
                        g.fillOval(p.x + i*sizeX/5, p.y - i*sizeY/5, sizeX, sizeY);
                }else
                    g.fillOval(p.x + i*sizeX/5, p.y - i*sizeY/5, sizeX, sizeY);
                i++;
            }
        }
    }
    
    //BusStop Image 描画
    public void drawBusStops(){
        int sizeX = w / col;
        int sizeY = h / row;
        if(origBusStopImg.get(0) != null)
            sizeX = (int) (origBusStopImg.get(0).getWidth() * sizeY / origBusImg.get(0).getHeight());
        int sizeCol = w / col;
        
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        for(BusStop busStop : park.getBusStops()){
            Point p = mappingArea(busStop.x, busStop.y);
            if(origBusImg != null){
                if(origBusStopImg.get(0) != null)
                    g.drawImage(
                        origBusStopImg.floorEntry(busStop.getAllQueueLength()).getValue(),
                        p.x + sizeCol/2-sizeX/2, p.y, sizeX, sizeY);
                else
                    g.fillRect(p.x, p.y, sizeX, sizeY);
            }else
                g.fillRect(p.x, p.y, sizeX, sizeY);
        }
    }
    
    //ABSの描画
    public void redrawABS(){
        //描画の初期化
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, w, h);
        
        //Cell & Image 描画 
        drawBusStops();
        drawBusAgents();
        
        drawCell();
    }
    
    private static AnimationTimer animationTimer;
    public void startVisualize(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                redrawABS();
            }
        };
        animationTimer.start();
    }
    
    public static void stopVisualize(){
        animationTimer.stop();
    }
}
