package util.Event;

import entity.Title.title_value;
import entity.User;
import util.Tool;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/27.
 */
public class BaseTitleEvent extends BaseEvent {
    public User user;
    private Timestamp event_time;
    protected BaseTitleEvent(User u){
        this.user = u;
        event_time = Tool.now();
    }

    public Integer getInt(String name){
        switch (name){
            case "acnum": return user.getAcnum();
            case "acb":   return user.getAcb();
            case "rating":return user.getShowRating();
            case "rating_num":return user.getRatingnum();
            case "status":return user.getInTeamStatus();
        }
        return super.getInt(name);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name.equals("event_time")) return event_time;
        return super.getTimestamp(name);
    }
}
