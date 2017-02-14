/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

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
}
