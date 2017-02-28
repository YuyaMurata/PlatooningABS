/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author murata
 */
public class ABSGUIInitializeFile {
    private static final String iniFile = "platooning_abs.ini";
    private static final Properties iniProp = new Properties();
    
    public void readINIFile(){
        try {
            iniProp.load(new FileInputStream(iniFile));
        } catch (IOException ex) {
        }
    }
    
    public void writeINIFile(){
        try {
            iniProp.store(new FileOutputStream(iniFile), null);
        } catch (IOException ex) {
        }
    }
    
    public String getABSParamFile(){
        readINIFile();
        return iniProp.getProperty("file");
    }
    
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
