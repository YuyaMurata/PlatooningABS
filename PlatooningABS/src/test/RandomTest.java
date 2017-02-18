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
        RandomDataGenerator rand = new RandomDataGenerator();
        
        for(int i=0; i < 1000; i++)
            System.out.println(rand.nextGaussian(0, 1)%1);
    }
}
