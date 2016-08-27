package util.Event.EvnetDeals;

import util.Event.EventDeal;
import util.Event.Events.EventStatusAdd;
import util.Main;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventDealOnStatusAdd extends EventDeal<EventStatusAdd> {

    @Override
    public void dealEvent(EventStatusAdd event) {
        Main.status.onStatusAdd(event.s);
    }

    @Override
    public Class<EventStatusAdd> getEventClass() {
        return EventStatusAdd.class;
    }
}
