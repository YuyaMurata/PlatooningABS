/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileout;

/**
 *
 * @author 悠也
 */
public class OutputInstance {
    public static OutputData data;
    public static void NewFile(String filename){
        data = new OutputData(filename);
    }
}