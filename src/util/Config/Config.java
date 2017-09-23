package util.Config;

import net.sf.json.JSONObject;
import util.GlobalVariables.GlobalVariables;
import util.Tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by QAQ on 2017/9/16.
 */
public class Config {
    public TopConfig topConfig = new TopConfig();

    public String OJName;
    public void init(){
        init(topConfig);
        /*---------------*/


        OJName = topConfig.OJName;
    }

    public void init(BaseConfig config){
        String JsonContext = readFile(Config.class.getResource("../../Config/"+config.getFileName()).getPath());
        config.readConfig(JSONObject.fromObject(JsonContext));
    }

    public static String readFile(String Path){
//        System.out.println(Path);
        BufferedReader reader = null;
        StringBuilder last_str = new StringBuilder();
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                last_str.append(tempString);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return last_str.toString();
    }

}
