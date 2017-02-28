package fileout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import prop.ABSSettings;

/**
 * ログデータファイル出力クラス
 * Output Log File
 */
public class OutputData implements ABSSettings{
    private String filename; //ログファイル名
    private PrintWriter pw; //ファイル出力用
    private Boolean logSW; //ファイル出力のスイッチ (true=ファイル出力)
    
    public OutputData(String filename, Boolean logSW) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.filename = filename;
        this.logSW = logSW;
        init();
    }

    //出力ファイルの初期化
    private void init() {
        try {
            File file = new File(filename);
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
        } catch (FileNotFoundException e) {
        }
    }

    //ファイルへの書き込み
    public void write(String str) {
        if(json.param.consoleSW) System.out.println(str); 
        if(logSW) pw.println(str);
    }

    //ファイルのクローズ (実際のファイルに書き込まれる)
    public void close() {
        pw.close();
    }
}
