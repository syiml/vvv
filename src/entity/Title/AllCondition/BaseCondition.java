package entity.Title.AllCondition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class BaseCondition {
    public boolean check(BaseEvent event){
        return false;
    }

    public static BaseCondition getCondition(JSONObject jo){
        if(!jo.containsKey("type")) return new BaseCondition();
        switch (jo.getString("type")){
            case "cmp":{
                JSONArray array = jo.getJSONArray("value");
                return new ConditionCmp(array.getString(0),array.getString(1),array.getString(2));
            }
            case "time_cmp":{
                JSONArray array = jo.getJSONArray("value");
                return new ConditionTimeCmp(array.getString(0),array.getString(1),array.getString(2),array.getInt(3));
            }
            case "or":{
                return new ConditionOr(jo.getJSONArray("value"));
            }
            case "and":{
                return new ConditionAnd(jo.getJSONArray("value"));
            }
        }
        return new BaseCondition();
    }
}
