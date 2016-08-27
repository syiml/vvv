package util.Event.Events;

import entity.Status;
import util.Event.BaseEvent;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventStatusChange extends BaseEvent{
    public Status ps,s;
    public EventStatusChange(Status ps, Status s){
        this.ps=ps;
        this.s=s;
    }
}
