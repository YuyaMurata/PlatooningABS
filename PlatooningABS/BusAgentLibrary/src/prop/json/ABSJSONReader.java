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
import prop.ABSSettings;

/**
 * JSONファイル(パラメータファイル)の読み込み
 * gson-2.8.0.jarにパスを通しておく必要あり
 * @author murata
 */
public class ABSJSONReader {
    private static ABSJSONReader paramJSON = new ABSJSONReader(); //JSON
    public ABSParameter param = new ABSParameter(); //パラメータ管理クラス
    
    //Singleton
    public static ABSJSONReader getInstance(){
        return paramJSON;
    }
    
    //JSONファイルへの書き込み
    public void absJSONWrite(String fileName) {
        
        //パラメータクラスをJSONWriterに渡す
        try (JsonWriter writer = 
            new JsonWriter(new BufferedWriter(new FileWriter(fileName)))) {
            
            //Format JSON
            writer.setIndent("  ");
            
            Gson gson = new Gson();
            gson.toJson(param, ABSParameter.class, writer);          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //JSONファイルの読み込み
    public void absJSONRead(String fileName) {
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
    
    //実行するとABSParameterクラスからJSONファイルを作成可能
    public static void main(String[] args) {
        getInstance().absJSONWrite(ABSSettings.settingFileName);
        getInstance().absJSONRead(ABSSettings.settingFileName);
        System.out.println(getInstance().param);
    }
}
