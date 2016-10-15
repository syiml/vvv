package util.TimerTasks;

import servise.MallMain;
import util.Main;
import util.MyTime;

import java.util.Timer;

/**
 * Created by QAQ on 2016/10/14.
 */
public class TaskUpdateAllUserRank extends MyTimer {
    public static boolean updateNextTime = true;
    @Override
    public void run() {
        if(updateNextTime) {
            Main.users.updateAllUserRank();
            updateNextTime = false;
        }
    }

    @Override
    public void getTimer() throws Exception {
        //每小时更新一次
        new Timer().scheduleAtFixedRate(this, 5 * MyTime.MINUTE, MyTime.HOUR);
    }
}
