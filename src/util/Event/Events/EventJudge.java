package util.Event.Events;

import entity.Status;
import entity.User;
import util.Event.BaseTitleEvent;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventJudge extends BaseTitleEvent {
    public Status s;
    public EventJudge(User u, Status s) {
        super(u);
        this.s = s;
    }
    public void before(){

    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name == null) return super.getTimestamp(null);
        switch (name){
            case "submit_time":return s.getSbmitTime();
        }
        return super.getTimestamp(name);
    }

    @Override
    public Integer getInt(String name) {
        switch (name){
            case "judge_result":return s.getResult().getValue();
        }
        return super.getInt(name);
    }
}
