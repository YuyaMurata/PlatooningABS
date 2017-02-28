/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 悠也
 */
public class ListTest {
    public static void main(String[] args) {
        List testList = Arrays.asList("A","B","C");
        List subList = new ArrayList();
        subList.addAll(testList);
        
        subList.remove("B");
        
        testList = subList;
        
        System.out.println(testList+"\n"+subList);
    }
}
