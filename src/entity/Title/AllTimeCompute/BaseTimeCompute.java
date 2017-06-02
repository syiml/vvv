package entity.Title.AllTimeCompute;

import entity.Title.title_value;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.MyTime;
import util.Tool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2017/5/28.
 */
public class BaseTimeCompute {
    private int title_id;
    //private JSONArray ja;
    private String name;

    private String type;
    private List<Object> args;

    public BaseTimeCompute(JSONArray ja){
        args = new ArrayList<>();
        //this.ja = ja;
        name = null;
        if(ja!=null) {
            type = ja.getString(0);
            switch (type) {
                case "last_time_of_day":
                case "last_time_of_tomorrow": {
                    args.add(getTimeCompute(ja.get(1)));
                    break;
                }
                case "end_time_of_some_title": {
                    args.add(ja.getInt(1));
                    break;
                }
                case "time_compute": {
                    args.add(getTimeCompute(ja.get(1)));
                    args.add(ja.getLong(2));
                    args.add(ja.getString(3));
                    break;
                }
            }
        }
    }

    public void setTitle_id(int id){
        this.title_id = id;
    }

    /**
     * 获取时间计算器的运算结果
     * @param event 事件
     * @return 时间。如果为0表示永久
     */
    public long getTime(BaseTitleEvent event){
        if(name != null){
            Timestamp ret = getTimestamp(name,event);
            if(ret == null) return 0;
            return ret.getTime();
        }
        switch (type){
            case "last_time_of_day":{
                Long time = ((BaseTimeCompute)args.get(0)).getTime(event);
                if(time <=0) return 0;
                return MyTime.getLastTimeOfDay(new Timestamp(time)).getTime();
            }
            case "last_time_of_tomorrow":{
                Long time = ((BaseTimeCompute)args.get(0)).getTime(event);
                if(time <=0) return 0;
                return MyTime.getLastTimeOfDay(MyTime.addTimestamp(new Timestamp(time),MyTime.DAY)).getTime();
            }
            case "end_time_of_some_title":{
                Timestamp time = event.user.titleSet.getTitleValue((int)args.get(0)).clear_time;
                if(time==null) return 0;
                return time.getTime();
            }
            case "time_compute":{
                Long time = ((BaseTimeCompute)args.get(0)).getTime(event);
                if(time<=0) return 0;
                return time + getTimeByUnit((long)args.get(1),(String)args.get(2));
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

    protected long getTimeByUnit(long value,String unit){
        switch (unit){
            case "year":return value * MyTime.YEAR;
            case "month": return value * MyTime.MONTH;
            case "day": return value * MyTime.DAY;
            case "hour": return value * MyTime.HOUR;
            case "minute":return value * MyTime.MINUTE;
            case "second":return value * MyTime.SECOND;
            default: return value;
        }
    }
}
