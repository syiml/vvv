package util.Event;

/**
 * Created by QAQ on 2016/8/27.
 */
public abstract class BaseEventDeal{
    public boolean isRemoved = false;
    public abstract void run(BaseEvent event);
    public abstract Class<?> getEventClass();
}
