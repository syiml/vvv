package entity.Title.AllTimeCompute;

import util.Event.BaseEvent;
import util.MyTime;

/**
 * Created by QAQ on 2017/5/28.
 */
public class TimeComputeNowAdd extends BaseTimeCompute{

    long addition;

    TimeComputeNowAdd(long value,String unit){
        switch (unit){
            case "day": addition = value * MyTime.DAY;break;
            case "month": addition = value * MyTime.MONTH;break;
            case "hour": addition = value * MyTime.HOUR;break;
            default: addition = value;
        }
    }
    @Override
    public long getTime(BaseEvent event){
        return MyTime.addTimestamp(event.getTimestamp(null),addition).getTime();
    }
}
