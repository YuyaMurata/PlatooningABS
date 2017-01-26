/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prop;

import prop.json.ABSJSONReader;

/**
 *
 * @author 悠也
 */
public interface ABSSettings {
    public static String settingFileName = "platooning_abs_param_demo.json";
    public static ABSJSONReader json = ABSJSONReader.getInstance();
}
