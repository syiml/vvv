package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/30.
 */
public class EventRichRank extends BaseTitleEvent {
    private int rank;
    public EventRichRank(User u, int rank) {
        super(u);
        this.rank = rank;
    }

    @Override
    public Integer getInt(String name) {
        if(name.equals("rank")) return rank;
        return super.getInt(name);
    }
}
