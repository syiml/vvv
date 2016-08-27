package util.Event.Events;

import entity.Status;
import util.Event.BaseEvent;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventStatusAdd extends BaseEvent {
    public Status s;
    public EventStatusAdd(Status s){
        this.s = s;
    }
}
