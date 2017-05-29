package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventVerify extends BaseTitleEvent {
    public EventVerify(User u) {
        super(u);
    }
}
