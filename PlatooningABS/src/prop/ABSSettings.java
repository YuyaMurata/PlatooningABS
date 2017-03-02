/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

import java.net.MalformedURLException;
import prop.json.ABSJSONReader;

/**
 * ABSで用いられる実験パラメータを管理するクラス
 * @author murata
 */
public interface ABSSettings {
    //実験パラメータの設定ファイル
    public static String settingFileName = "platooning_abs_param.json";
    
    //JSONファイルのリーダー
    public static ABSJSONReader json = ABSJSONReader.getInstance();
    
    //出力の切り替え File = true console = false
    public static Boolean logConsoleFileSW = false;
    
    //ClassLoader
    public static String classFolder = "./class/";
    public static ClassLoader absClassLoader = createClassLoader(classFolder);
    
    //クラスローダー　classフォルダ内のファイルを読み込む
    public static ClassLoader createClassLoader(String dirname) {
        java.net.URL[] url = new java.net.URL[1];
        java.io.File file;
        if (dirname.endsWith("/")) {
            file = new java.io.File(dirname);
        }else {
            // ディレクトリは最後にスラッシュが必要
            file = new java.io.File(dirname + "/");
        }
        try {
            url[0]= file.toURI().toURL();
            ClassLoader parent = ClassLoader.getSystemClassLoader();
            java.net.URLClassLoader loader = new java.net.URLClassLoader(url, parent);

            return loader;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
