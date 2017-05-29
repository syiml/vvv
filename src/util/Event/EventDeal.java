package util.Event;

import util.Tool;

/**
 * Created by QAQ on 2016/8/27.
 */
public abstract class EventDeal<T extends BaseEvent> extends BaseEventDeal {

    private Class<T> cls;
    protected EventDeal(Class<T> cls){
        this.cls = cls;
    }
    public final void run(BaseEvent event){
        if(isRemoved) return ;
        if(cls != event.getClass()) return ;
        T trueEvent = (T)event;
//        Tool.debug("EventDeal:"+this+"->"+trueEvent);
        dealEvent(trueEvent);
    }
    public abstract void dealEvent(T event);
    public Class<T> getEventClass(){
        return cls;
    }
}
