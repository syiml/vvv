package util.GlobalVariables;

import java.io.*;

import net.sf.json.JSONObject;
import util.Config.Config;


/**
 * Created by Syiml on 2015/7/7 0007.
 */
public class GlobalVariables {
    public static JSONObject read(){
        String path = GlobalVariables.class.getResource("../../Config/GlobalVariables.json").getPath();
        String JsonContext = Config.readFile(path);
        return JSONObject.fromObject(JsonContext);
    }
}

