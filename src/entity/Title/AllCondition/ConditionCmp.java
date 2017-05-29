package entity.Title.AllCondition;

import util.Event.BaseEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class ConditionCmp extends BaseCondition{
    String name1,name2;
    String cmpType;

    public ConditionCmp(String name1,String cmpType,String name2){
        this.name1 = name1;
        this.name2 = name2;
        this.cmpType = cmpType;
    }
    @Override
    public boolean check(BaseEvent event) {
        Integer int1 = event.getInt(name1);
        Integer int2 = event.getInt(name2);
        if(int1 == null || int2 == null) return false;
        switch (cmpType){
            case "<":return int1 < int2;
            case ">":return int1 > int2;
            case "=":
            case "==":return int1.equals(int2);
            case ">=":return int1 >= int2;
            case "<=":return int1 <= int2;
            case "<>":
            case "!=":return !int1.equals(int2);
        }
        return false;
    }
}
