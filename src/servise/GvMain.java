package servise;

import dao.GvSQL;
import net.sf.json.JSONObject;

/**
 * Created by QAQ on 2016/6/26 0026.
 */
public class GvMain {
    public static GvSQL gvSQL=new GvSQL();
    public static JSONObject getChallengeJson(){
        String challenge=gvSQL.get("challenge");
        return JSONObject.fromObject(challenge);
    }
    public static void setChallengeJson(String value){
        gvSQL.set("challenge",value);
    }
}
