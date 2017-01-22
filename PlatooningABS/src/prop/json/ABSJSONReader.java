/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import prop.ABSParameter;

/**
 *
 * @author kaeru
 */
public class ABSJSONReader {
    private String fileName = "platooning_abs_param.json";
    private static ABSJSONReader paramJSON = new ABSJSONReader();
    public ABSParameter param;
    
    public static ABSJSONReader getInstance(){
        return paramJSON;
    }
    
    public void absJSONWrite() {
        try (JsonWriter writer = 
            new JsonWriter(new BufferedWriter(new FileWriter(fileName)))) {
            
            //Format JSON
            writer.setIndent("  ");
            
            Gson gson = new Gson();
            gson.toJson(new ABSParameter(), ABSParameter.class, writer);          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void absJSONReade() {
        try (JsonReader reader = 
            new JsonReader(new BufferedReader(new FileReader(fileName)))) { 
            
            // JSONからオブジェクトへの変換
            Gson gson = new Gson();
            param = gson.fromJson(reader, ABSParameter.class);
            
            //System.out.println(param);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        getInstance().absJSONReade();
        System.out.println(getInstance().param);
    }
}
