package util.Event;

import util.Tool;

/**
 * Created by QAQ on 2016/8/27.
 */
public abstract class EventDeal<T extends BaseEvent> extends BaseEventDeal {
    public final void run(BaseEvent event){
        if(isRemoved) return ;
        if(this.getEventClass() != event.getClass()) return ;
        T trueEvent = (T)event;
//        Tool.debug("EventDeal:"+this+"->"+trueEvent);
        dealEvent(trueEvent);
    }
    public abstract void dealEvent(T event);
    public abstract Class<T> getEventClass();
}
