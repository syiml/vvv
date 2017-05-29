package util.Event;

import entity.User;

/**
 * Created by QAQ on 2017/5/27.
 */
public class BaseTitleEvent extends BaseEvent {
    public User user;
    protected BaseTitleEvent(User u){
        this.user = u;
    }

    public Integer getInt(String name){
        switch (name){
            case "acnum": return user.getAcnum();
            case "acb":   return user.getAcb();
            case "rating":return user.getShowRating();
            case "ratting_num":return user.getRatingnum();
            case "status":return user.getInTeamStatus();
        }
        return super.getInt(name);
    }

}
