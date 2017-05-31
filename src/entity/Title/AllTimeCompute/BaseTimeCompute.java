package entity.Title.AllTimeCompute;

import entity.Title.title_value;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.MyTime;
import util.Tool;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/28.
 */
public class BaseTimeCompute {
    private int title_id;
    private JSONArray ja;
    private String name;
    public BaseTimeCompute(JSONArray ja){
        this.ja = ja;
    }

    public void setTitle_id(int id){
        this.title_id = id;
    }
    public long getTime(BaseTitleEvent event){
        if(ja == null){
            if(name==null) return 0;
            else{
                Timestamp ret = getTimestamp(name,event);
                if(ret == null) return 0;
                return ret.getTime();
            }
        }
        switch (ja.getString(0)){
            case "last_time_of_day":{
                return MyTime.getLastTimeOfDay(getTimestamp(ja.getString(1),event)).getTime();
            }
            case "last_time_of_tomorrow":{
                return MyTime.getLastTimeOfDay(MyTime.addTimestamp(getTimestamp(ja.getString(1),event),MyTime.DAY)).getTime();
            }
        }
        return -1;
    }
    public Timestamp getTimestamp(String name, BaseTitleEvent event){
        if(name==null) return Tool.now();
        switch (name){
            case "clear_time": return event.user.titleSet.getTitleValue(title_id).clear_time;
            case "forever":return null;
        }
        return event.getTimestamp(name);
    }

    public static BaseTimeCompute getTimeCompute(Object obj){
        if(obj instanceof String){
            BaseTimeCompute cmp = new BaseTimeCompute(null);
            cmp.name = (String)obj;
            return cmp;
        }
        JSONArray ja = JSONArray.fromObject(obj);
        if(ja.size()==0) return new BaseTimeCompute(null);
        switch (ja.getString(0)){
            case "now_add":{
                return new TimeComputeNowAdd(ja,ja.getLong(1),ja.getString(2));
            }
            default:return new BaseTimeCompute(ja);
        }
    }
}
