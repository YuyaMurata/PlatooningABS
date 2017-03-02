/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * INIファイルのリードライト
 * @author murata
 */
public class ABSGUIInitializeFile {
    private static final String iniFile = "platooning_abs.ini";
    private static final Properties iniProp = new Properties();
    
    public void readINIFile(){
        //INIファイルの作成
        File newfile = new File(iniFile);
        try {
            newfile.createNewFile();
        } catch (IOException ex) {
        }
        
        //INIファイルの読み込み
        try {
            iniProp.load(new FileInputStream(iniFile));
        } catch (IOException ex) { 
        }
    }
    
    //INIファイルの書き込み
    public void writeINIFile(){
        try {
            iniProp.store(new FileOutputStream(iniFile), null);
        } catch (IOException ex) {
        }
    }
    
    //ABSパラメータファイルの設定
    public String getABSParamFile(){
        readINIFile();
        return iniProp.getProperty("file");
    }
    
    
    //ABSパラメータファイルの変更
    public void setABSParamFile(String fileName){
        iniProp.setProperty("file", fileName);
        writeINIFile();
    }
    
    public void printINIFile(){
        iniProp.entrySet().stream().forEach(System.out::println);
    }
    
    public static void main(String[] args) {
        ABSGUIInitializeFile ini = new ABSGUIInitializeFile();
        ini.setABSParamFile("platooning_abs_param.json");
    }
}
