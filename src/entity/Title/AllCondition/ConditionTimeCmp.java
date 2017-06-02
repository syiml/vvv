package entity.Title.AllCondition;

import entity.Title.AllTimeCompute.BaseTimeCompute;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/29.
 */
public class ConditionTimeCmp extends BaseCondition {
    private BaseTimeCompute timeCompute1,timeCompute2;
    private String cmpType;
    public ConditionTimeCmp(Object obj1, String cmpType, Object obj2) {
        timeCompute1 = BaseTimeCompute.getTimeCompute(obj1);
        timeCompute2 = BaseTimeCompute.getTimeCompute(obj2);
        this.cmpType = cmpType;
    }

    public boolean less(Timestamp time1,Timestamp time2){
        if(time1==null && time2==null) return false;
        if(time1!=null && time2!=null) return time1.before(time2);
        return time2 == null;
    }
    public boolean equals(Timestamp time1,Timestamp time2) {
        return time1 == null && time2 == null || !(time1 == null || time2 == null) && time1.equals(time2);
    }

    @Override
    public void setTitleID(int id) {
        timeCompute1.setTitle_id(id);
        timeCompute2.setTitle_id(id);
        super.setTitleID(id);
    }

    @Override
    public boolean check(BaseTitleEvent event) {
        long t1 = timeCompute1.getTime(event);
        long t2 = timeCompute2.getTime(event);
        Timestamp time1 = t1<=0?null:new Timestamp(t1);
        Timestamp time2 = t2<=0?null:new Timestamp(t2);
        //if(time1 == null || time2 == null) return false;
        switch (cmpType){
            case "<":return less(time1,time2);
            case ">":return less(time2,time1);
            case "=":
            case "==":return equals(time1,time2);
            case ">=":return less(time2,time1) || equals(time1,time2);
            case "<=":return less(time1,time2) || equals(time1,time2);
            case "<>":
            case "!=":return !equals(time1,time2);
        }
        return false;
    }
}
