package entity.Title.AllCondition;

import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/6/1.
 */
public class ConditionHaveTitleCheck extends BaseCondition {
    private int title_id;

    public ConditionHaveTitleCheck(int title_id) {
        super();
        this.title_id = title_id;
    }

    @Override
    public boolean check(BaseTitleEvent event) {
        return event.user.titleSet.haveTitle(title_id);
    }
}
