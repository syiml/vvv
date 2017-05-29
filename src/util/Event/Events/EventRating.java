package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventRating extends BaseTitleEvent {
    private int rank;
    public EventRating(User u, int rank) {
        super(u);
        this.rank = rank;
    }
    public Integer getInt(String name){
        if(name.equals("rank")){
            return rank;
        }
        return super.getInt(name);
    }
    public void before(){

    }
}
