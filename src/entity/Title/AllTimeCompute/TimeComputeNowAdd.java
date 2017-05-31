package entity.Title.AllTimeCompute;

import net.sf.json.JSONArray;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.MyTime;

/**
 * Created by QAQ on 2017/5/28.
 */
public class TimeComputeNowAdd extends BaseTimeCompute{

    private long addition;

    TimeComputeNowAdd(JSONArray ja,long value, String unit){
        super(ja);
        switch (unit){
            case "day": addition = value * MyTime.DAY;break;
            case "month": addition = value * MyTime.MONTH;break;
            case "hour": addition = value * MyTime.HOUR;break;
            default: addition = value;
        }
    }
    @Override
    public long getTime(BaseTitleEvent event){
        return MyTime.addTimestamp(getTimestamp(null,event),addition).getTime();
    }
}
