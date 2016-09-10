package util.TimerTasks;

import servise.WeekRankCount.WeekRankCountHTML;

import java.util.Timer;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class TaskWeekRankCount extends MyTimer{
    @Override
    public void run() {
        WeekRankCountHTML.compute();
    }

    @Override
    public void getTimer() throws Exception {
        setEveryDay(0,30,0);
        new Timer().scheduleAtFixedRate(this, date, period);
    }
}
