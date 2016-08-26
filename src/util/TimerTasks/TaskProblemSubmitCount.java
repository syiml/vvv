package util.TimerTasks;

import util.Tool;

import java.util.Timer;

/**
 * Created by QAQ on 2016/8/26.
 */
public class TaskProblemSubmitCount extends MyTimer {

    @Override
    public void run() {
        Tool.debug("TaskProblemSubmitCount run");
    }

    @Override
    public void getTimer() throws Exception {
        setEveryDay(3,0,0);
        new Timer().scheduleAtFixedRate(this, date, period);
    }
}
