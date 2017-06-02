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
        addition = getTimeByUnit(value,unit);
    }
    @Override
    public long getTime(BaseTitleEvent event){
        return MyTime.addTimestamp(getTimestamp(null,event),addition).getTime();
    }
}
