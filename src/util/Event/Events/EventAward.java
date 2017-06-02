package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * 荣誉榜事件
 * Created by QAQ on 2017/6/1.
 */
public class EventAward extends BaseTitleEvent {
    private int contestLevel;
    private int awardLevel;
    public EventAward(User u,int contestLevel,int awardLevel) {
        super(u);
        this.awardLevel = awardLevel;
        this.contestLevel = contestLevel;
    }

    @Override
    public Integer getInt(String name) {
        switch (name){
            case "contest_level":return contestLevel;
            case "award_level":return awardLevel;
        }
        return super.getInt(name);
    }
}
