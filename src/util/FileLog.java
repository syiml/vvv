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
    private static void openUpdateSqlLogFile(){
        String filePath = Main.config.localJudgeWorkPath+"log\\updateSQL\\";
        String fileName = Tool.nowDate()+".txt";
        try {
            updateSqlLogFile = new FileWriter(filePath+fileName, true);
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
                updateSqlLogFile = new FileWriter(filePath+fileName, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (Exception ignored){}
    }
    public static synchronized void updateSqlLog(String content) {
        try {
            openUpdateSqlLogFile();
            Tool.debug("save");
            updateSqlLogFile.write(content+"\r\n");
            updateSqlLogFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
