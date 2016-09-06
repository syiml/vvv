package util.GlobalVariables;

import java.io.*;

import net.sf.json.JSONObject;


/**
 * Created by Syiml on 2015/7/7 0007.
 */
public class GlobalVariables {
    public String ReadFile(String Path){
//        System.out.println(Path);
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
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
        return laststr;
    }
    public static JSONObject read(){
        String JsonContext = new GlobalVariables().ReadFile(GlobalVariables.class.getResource("GlobalVariables.json").getPath());
        //System.out.println(JsonContext);
        return JSONObject.fromObject(JsonContext);
    }
}

