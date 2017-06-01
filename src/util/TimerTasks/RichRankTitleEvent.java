package util.TimerTasks;

import entity.User;
import util.Event.EventMain;
import util.Event.Events.EventRichRank;
import util.Main;

import java.util.List;
import java.util.Timer;

/**
 * Created by QAQ on 2017/5/30.
 */
public class RichRankTitleEvent extends MyTimer {
    @Override
    public void run() {
        List<User> list= Main.users.getRichTop10();
        for (int i=0;i<list.size();i++) {
            EventMain.triggerEvent(new EventRichRank(Main.users.getUser(list.get(i).getUsername()),i+1));
        }
    }

    @Override
    public void getTimer() throws Exception {
        setEveryDay(0,0,0);
        new Timer().scheduleAtFixedRate(this, date, period);
    }
}
