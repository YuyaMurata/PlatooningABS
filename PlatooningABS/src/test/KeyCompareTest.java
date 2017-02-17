/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author murata
 */
public class KeyCompareTest {
    public static void main(String[] args) {
        Object keyA = 0+"-"+0;
        for(int i=0; i < 10; i++){
            for(int j=0; j < 10; j++){
                Object keyB = i+"-"+j;
                System.out.println("keyA:"+keyA+" - keyB:"+keyB+" comp="+(keyA.toString().compareTo(keyB.toString())));
            }
        }
    }
}
