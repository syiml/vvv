package util.GlobalVariables;

import java.io.*;

import net.sf.json.JSONObject;


/**
 * Created by Syiml on 2015/7/7 0007.
 */
public class GlobalVariables {
    public static JSONObject read(){
            String JsonContext = new GlobalVariables().ReadFile(GlobalVariables.class.getResource("GlobalVariables.json").getPath());
            //System.out.println(JsonContext);
            JSONObject jo = JSONObject.fromObject(JsonContext);
            return jo;

//        for(int  i = 0; i < size; i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            System.out.println("[" + i + "]name=" + jsonObject.get("name"));
//            System.out.println("[" + i + "]package_name=" + jsonObject.get("package_name"));
//            System.out.println("[" + i + "]check_version=" + jsonObject.get("check_version"));
//        }
    }

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
}

