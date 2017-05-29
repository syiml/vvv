package entity.Title.AllCondition;

import net.sf.json.JSONArray;
import util.Event.BaseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAQ on 2017/5/29.
 */
public class ConditionAnd extends BaseCondition {
    private List<BaseCondition> conditions;

    public ConditionAnd(JSONArray ja) {
        conditions = new ArrayList<>();
        for(int i=0;i<ja.size();i++){
            conditions.add(BaseCondition.getCondition(ja.getJSONObject(i)));
        }
    }

    @Override
    public boolean check(BaseEvent event) {
        for (BaseCondition condition : conditions) {
            if (!condition.check(event)) return false;
        }
        return true;
    }
}
