package servise;

import dao.GvSQL;
import net.sf.json.JSONObject;

/**
 * Created by QAQ on 2016/6/26 0026.
 */
public class GvMain {
    private static GvSQL gvSQL=new GvSQL();
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
    public static void setOneProblemEveryDayPid(int pid){
        gvSQL.set("oneProblemEveryDayPid",pid+"");
    }
    public static int getOneProblemEveryDayPid(){
        String ret = gvSQL.get("oneProblemEveryDayPid");
        return ret==null?0:Integer.parseInt(ret);
    }
    public static String getOneProblemEveryDayName(){
        return gvSQL.get("oneProblemEveryDayName");
    }
    public static void setOneProblemEveryDayName(String name){
        gvSQL.set("oneProblemEveryDayName",name);
    }
    public static int getAppVersionCode(){
        int ret = 0;
        try{
            ret = Integer.parseInt(gvSQL.get("appVersionCode"));
        }catch (Exception ignored){}
        return ret;
    }
    public static void setAppVersionCode(int version){
        gvSQL.set("appVersionCode",version+"");
    }
    public static String getAppVersionName(){
        return gvSQL.get("appVersionName");
    }
    public static void setAppVersionName(String version){
        gvSQL.set("appVersionName",version+"");
    }
    public static String getAppUpdate(){
        return gvSQL.get("appUpdate");
    }
    public static void setAppUpdate(String text){
        gvSQL.set("appUpdate",text);
    }
}
