/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class MapRemoveTest {
    public static void main(String[] args) {
        Map map = new HashMap();
        
        map.put("A", "TEST-A");
        map.put("B", "TEST-B");
        map.put("C", "TEST-C");
        
        map.values().toArray()[0] = "TEST-D";
        
        map.remove("B");
        
        System.out.println(map);
    }
}
