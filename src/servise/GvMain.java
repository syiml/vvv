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
    public static int getOneProblemEveryDayCid(){
        String s = gvSQL.get("oneProblemEveryDayCid");
        int ret;
        try {
            ret = Integer.parseInt(s);
        }catch (Exception e){
            ret = -1;
        }
        return ret;
    }

    public static void setOneProblemEveryDayCid(int cid){
        gvSQL.set("oneProblemEveryDayCid",cid+"");
    }

    public static String getOneProblemEveryDayName(){
        return gvSQL.get("oneProblemEveryDayName");
    }

    public static void setOneProblemEveryDayName(String name){
        gvSQL.set("oneProblemEveryDayName",name);
    }
}
