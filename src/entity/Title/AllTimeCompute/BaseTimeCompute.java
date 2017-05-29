package entity.Title.AllTimeCompute;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class BaseTimeCompute {
    public long getTime(BaseEvent event){
        return -1;
    }

    public static BaseTimeCompute getTimeCompute(JSONObject jo){
        if(!jo.containsKey("type")) return new BaseTimeCompute();
        switch (jo.getString("type")){
            case "now_add":{
                JSONArray ja = jo.getJSONArray("value");
                return new TimeComputeNowAdd(ja.getLong(0),ja.getString(1));
            }
        }
        return new BaseTimeCompute();
    }
}
