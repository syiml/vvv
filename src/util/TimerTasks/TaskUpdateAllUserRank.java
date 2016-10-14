package util.TimerTasks;

import servise.MallMain;
import util.Main;
import util.MyTime;

import java.util.Timer;

/**
 * Created by QAQ on 2016/10/14.
 */
public class TaskUpdateAllUserRank extends MyTimer {
    @Override
    public void run() {
        Main.users.updateAllUserRank();
    }

    @Override
    public void getTimer() throws Exception {
        //每小时更新一次
        new Timer().scheduleAtFixedRate(this, 5 * MyTime.MINUTE, MyTime.HOUR);
    }
}
