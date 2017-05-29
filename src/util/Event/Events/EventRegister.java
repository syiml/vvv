package util.Event.Events;

import entity.User;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/5/27.
 */
public class EventRegister extends BaseTitleEvent {

    public EventRegister(User u) {
        super(u);
    }
}
