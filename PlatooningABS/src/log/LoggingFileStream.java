/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import prop.ABSSettings;

/**
 *
 * @author murata
 */
public class LoggingFileStream extends PrintStream implements ABSSettings{
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
    
    public LoggingFileStream(String fileName) throws FileNotFoundException {
        super(fileName);
    }
    
    // 現在日時をフォーマットして文字列で返す
    private String getDateTime(){
        return FORMATTER.format( new Date() );
    }
    
    @Override public void print(boolean b) { super.print( getDateTime()+b); }
    @Override public void print(char c) { super.print( getDateTime()+c); }
    @Override public void print(char[] s) { super.print( getDateTime()+String.valueOf(s)); }
    @Override public void print(double d) { super.print(getDateTime()+d); }
    @Override public void print(float f) { super.print(getDateTime()+f); }
    @Override public void print(int i) { super.print(getDateTime()+i); }
    @Override public void print(long l) { super.print(getDateTime()+l); }
    @Override public void print(String s) { super.print(getDateTime()+s); }
    @Override public void print(Object obj) { super.print(getDateTime() + String.valueOf(obj)); }
}
