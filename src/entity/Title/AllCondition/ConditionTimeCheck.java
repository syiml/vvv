package entity.Title.AllCondition;

import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.MyTime;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by QAQ on 2017/5/29.
 */
public class ConditionTimeCheck extends BaseCondition{
    String name;
    String element;
    String cmpType;
    int value;

    public ConditionTimeCheck(String name, String element, String cmpType, int value){
        this.name = name;
        this.element = element;
        this.cmpType = cmpType;
        this.value = value;
    }
    public boolean check(BaseTitleEvent event){
        Timestamp t = getTimestamp(name,event);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        int z = 0;
        switch (element){
            case "hour": z = calendar.get(Calendar.HOUR_OF_DAY);break;
            case "week": z = calendar.get(Calendar.DAY_OF_WEEK) - 1;break;
        }
        switch (cmpType){
            case "<":return z < value;
            case ">":return z > value;
            case "=":
            case "==":return z == value;
            case ">=":return z >= value;
            case "<=":return z <= value;
            case "<>":
            case "!=":return z!=value;
        }
        return false;
    }
}
