package util.Event.Events;

import entity.User;
import util.Event.BaseTitleEvent;

/**
 * Created by QAQ on 2017/6/2.
 */
public class EventShopBuy extends BaseTitleEvent {
    private int goods_id;
    public EventShopBuy(User u, int goods_id) {
        super(u);
        this.goods_id = goods_id;
    }

    @Override
    public Integer getInt(String name) {
        if(name.equals("goods_id")) return goods_id;
        return super.getInt(name);
    }
}
