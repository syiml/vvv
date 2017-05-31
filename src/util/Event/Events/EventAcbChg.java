package util.Event.Events;

import entity.AcbOrder;
import entity.User;
import util.Event.BaseTitleEvent;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/29.
 */
public class EventAcbChg extends BaseTitleEvent {
    private AcbOrder acbOrder;
    public EventAcbChg(User u, AcbOrder order) {
        super(u);
        this.acbOrder = order;
    }

    @Override
    public Integer getInt(String name) {
        if(name.equals("chg")) return acbOrder.change;
        if(name.equals("reason")) return acbOrder.reason.getId();
        return super.getInt(name);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name.equals("get_time")){
            return acbOrder.time;
        }
        return super.getTimestamp(name);
    }
}
