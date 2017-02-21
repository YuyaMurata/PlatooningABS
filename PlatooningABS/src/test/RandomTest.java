/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.commons.math3.random.RandomDataGenerator;

/**
 *
 * @author murata
 */
public class RandomTest {
    public static void main(String[] args) {
        int num = 6;
        
        for(int i=0; i < num; i++)
            System.out.println(i+" = "+100/num*(i+1));
    }
    
    RandomDataGenerator rand = new RandomDataGenerator();
    public Integer getGaussRandom(){
        int value = (int)rand.nextGaussian(50, 20);
        if(value > 100 || value < 0)
            value = rand.nextInt(0, 100);
        
        return value;
    }
}
