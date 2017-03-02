/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.edit;

import java.awt.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import obj.BusStop;

/**
 *
 * @author murata
 */
public class ABSEditVisualizer {
    private Canvas canvas;
    private int  w, h, col, row;
    private Image img;
    private Color[] color = new Color[]{Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.PURPLE, Color.AQUA};
    
    public ABSEditVisualizer(Canvas canvas, int col, int row, String img) {
        this.canvas = canvas;
        this.w = (int) canvas.getWidth();
        this.h = (int) canvas.getHeight();
        this.col = col;
        this.row = row;
        
        try{
            this.img = new Image(getClass().getResourceAsStream(img)); 
        }catch(Exception e){
            this.img = null;
        }
        
        this.canvas.getGraphicsContext2D().clearRect(0, 0, w, h);
    }
    
    //ABS座標　→　フレーム座標　に変換
    public Point mappingArea(int pc, int pr){
        Point p = new Point();
        
        //e.g. X = Width / セルの全列数 * セルの列番号
        p.x = w / col * pc;
        p.y = h / row * pr;
        
        return p;
    }
    
    //セルの罫線を描画
    public void drawCell(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        g.setStroke(Color.BLACK);
        //Column
        for(int i=0; i < col; i++){
            Point p = mappingArea(i, 0);
            g.strokeLine(p.x, 0, p.x, canvas.getHeight());
        }
        
        //Row
        for(int i=0; i < row; i++){
            Point p = mappingArea(0, i);
            g.strokeLine(0, p.y, canvas.getWidth(), p.y);
        }
    }
    
    //画像の描画
    public void drawImage(String obj, int x, int y){
        int sizeX = w / col;
        int sizeY = h / row;
        
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        Point p = mappingArea(x, y);
        if(img != null)
            g.drawImage(img, p.x, p.y, sizeX, sizeY);
        else
            g.fillRect(p.x, p.y, sizeX, sizeY);
    }
    
    //ルートの描画
    public void drawRoot(int rootNo, BusStop start , BusStop stop){
        int sizeX = w / col /2;
        int sizeY = h / row /2;
        
        Point p1 = mappingArea(start.x, start.y);
        Point p2 = mappingArea(stop.x, stop.y);
        
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        g.setStroke(color[rootNo]);
        g.strokeLine(p1.x+sizeX, p1.y+sizeY, p2.x+sizeX, p2.y+sizeY);
    }
}
