package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventRating extends BaseTitleEvent {
    private int rank;
    private int total_num;
    public EventRating(User u, int rank , int total_num) {
        super(u);
        this.rank = rank;
        this.total_num = total_num;
    }
    public Integer getInt(String name){
        if(name.equals("rank")){
            return rank;
        }
        if(name.equals("total_num")) return total_num;
        return super.getInt(name);
    }
    public void before(){

    }
}
