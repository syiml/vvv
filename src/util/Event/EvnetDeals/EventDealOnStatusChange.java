package util.Event.EvnetDeals;

import entity.Result;
import util.Event.EventDeal;
import util.Event.Events.EventStatusChange;
import util.Main;
import util.TimerTasks.OneProblemEveryDay;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventDealOnStatusChange extends EventDeal<EventStatusChange> {
    public EventDealOnStatusChange() {
        super(EventStatusChange.class);
    }

    @Override
    public void dealEvent(EventStatusChange event) {
        Main.status.onStatusChange(event.ps,event.s);
        OneProblemEveryDay.onUserAcProblem(event.ps,event.s);
    }

    @Override
    public Class<EventStatusChange> getEventClass() {
        return EventStatusChange.class;
    }
}
