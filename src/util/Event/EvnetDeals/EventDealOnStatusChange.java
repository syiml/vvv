package util.Event.EvnetDeals;

import util.Event.EventDeal;
import util.Event.Events.EventStatusChange;
import util.Main;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventDealOnStatusChange extends EventDeal<EventStatusChange> {
    @Override
    public void dealEvent(EventStatusChange event) {
        Main.status.onStatusChange(event.ps,event.s);
    }

    @Override
    public Class<EventStatusChange> getEventClass() {
        return EventStatusChange.class;
    }
}
