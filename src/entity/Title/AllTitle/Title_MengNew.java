package entity.Title.AllTitle;

import entity.Title.BaseTitle;
import servise.UserService;
import util.Event.Events.EventRegister;
import util.MyTime;
import util.Tool;

/**
 * 称号：萌新
 * 注册时获得
 * 持续30天
 * Created by QAQ on 2017/5/27.
 */
public class Title_MengNew extends BaseTitle<EventRegister> {

    public Title_MengNew(){
        super(EventRegister.class);
    }

    @Override
    public Integer getID() {
        return 1;
    }

    @Override
    public String getName() {
        return "萌新";
    }

    @Override
    public String getDes() {
        return "注册30天内获得";
    }

    @Override
    public void dealEvent(EventRegister event) {
        UserService.addTitle(event.user,getID(), MyTime.addTimestamp(Tool.now(),30*MyTime.DAY));
    }
}
