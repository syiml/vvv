package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/6/1.
 */
public class EventRatingRank extends BaseTitleEvent {
    private int rank;
    public EventRatingRank(User u, int rank) {
        super(u);
        this.rank = rank;
    }

    @Override
    public Integer getInt(String name) {
        if(name.equals("rank")) return rank;
        return super.getInt(name);
    }
}
