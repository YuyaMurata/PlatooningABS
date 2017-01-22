package fileout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import prop.ABSSettings;

/**
 * Output Log File
 */
public class OutputData implements ABSSettings{

    private String filename;

    public OutputData(String filename) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.filename = filename;
        init();
    }

    private PrintWriter pw;

    private void init() {
        try {
            File file = new File(filename);
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
        } catch (FileNotFoundException e) {
        }
    }

    public void write(String str) {
        if(json.param.consoleSW) System.out.println(str); 
        else pw.println(str);
    }

    public void close() {
        pw.close();
    }
}
