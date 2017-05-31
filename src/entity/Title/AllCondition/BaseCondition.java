package entity.Title.AllCondition;

import entity.Title.title_value;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseTitleEvent;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/28.
 */
public class BaseCondition {
    private boolean res;
    protected int title_id;
    public BaseCondition(){res=false;}
    public void setTitleID(int id){
        this.title_id = id;
    }
    private BaseCondition(boolean res){
        this.res = res;
    }
    public boolean check(BaseTitleEvent event){
        return res;
    }

    public static BaseCondition getCondition(JSONObject jo){
        if(!jo.containsKey("type")) return new BaseCondition(false);
        switch (jo.getString("type")){
            case "cmp":{
                JSONArray array = jo.getJSONArray("value");
                return new ConditionCmp(array.getString(0),array.getString(1),array.getString(2));
            }
            case "time_check":{
                JSONArray array = jo.getJSONArray("value");
                return new ConditionTimeCheck(array.getString(0),array.getString(1),array.getString(2),array.getInt(3));
            }
            case "time_cmp":{
                JSONArray array = jo.getJSONArray("value");
                return new ConditionTimeCmp(array.get(0),array.getString(1),array.get(2));
            }
            case "or":{
                return new ConditionOr(jo.getJSONArray("value"));
            }
            case "and":{
                return new ConditionAnd(jo.getJSONArray("value"));
            }
            case "true":{
                return new BaseCondition(true);
            }
        }
        return new BaseCondition(false);
    }

    public int getInt(String name,BaseTitleEvent event){
        if(name.equals("jd")){
            title_value value = event.user.titleSet.getTitleValue(title_id);
            return value.jd;
        }
        return event.getInt(name);
    }
    public String getString(String name, BaseTitleEvent event){
        return event.getString(name);
    }
    public Timestamp getTimestamp(String name, BaseTitleEvent event){
        if(name.equals("clear_time")){
            title_value value = event.user.titleSet.getTitleValue(title_id);
            return value.clear_time;
        }
        return event.getTimestamp(name);
    }
}
