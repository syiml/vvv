package util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by QAQ on 2016/10/15.
 */
public class FileLog{
    private static FileWriter updateSqlLogFile = null;
    private static FileWriter LogFile = null;
    private static FileWriter openFile(String filePath,String fileName){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath+fileName, true);
        }catch (FileNotFoundException e){
            File f = new File(filePath);
            if(!f.exists()){
                f.mkdirs();
            }
            File file = new File(filePath,fileName);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ee) {
                    // TODO Auto-generated catch block
                    ee.printStackTrace();
                }
            }
            try {
                fw = new FileWriter(filePath+fileName, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (Exception ignored){}
        return fw;
    }
    public static synchronized void updateSqlLog(String content) {
        try {
            updateSqlLogFile = openFile(Main.config.topConfig.localJudgeWorkPath+"log/updateSQL/",Tool.nowDate()+".txt");
            updateSqlLogFile.write(content+"\n");
            updateSqlLogFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized void RunLog(String content) {
        try {
            LogFile = openFile(Main.config.topConfig.localJudgeWorkPath+"log/runLog/",Tool.nowDate()+".txt");
            LogFile.write(content+"\n");
            LogFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
