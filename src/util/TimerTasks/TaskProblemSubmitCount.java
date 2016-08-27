package util.TimerTasks;

import util.SQL;
import util.Tool;

import java.util.Timer;

/**
 * Created by QAQ on 2016/8/26.
 */
public class TaskProblemSubmitCount extends MyTimer {

    @Override
    public void run() {
        Tool.debug("TaskProblemSubmitCount run");
        new SQL("UPDATE problem SET " +
                "totalSubmit=(SELECT COUNT(*) FROM statu WHERE statu.pid=problem.pid)," +
                "totalSubmitUser=(SELECT COUNT(distinct statu.ruser) FROM statu WHERE statu.pid=problem.pid)," +
                "totalAc=(SELECT COUNT(*) FROM statu WHERE statu.pid=problem.pid AND statu.result=1)," +
                "totalAcUser=(SELECT COUNT(distinct statu.ruser) FROM statu WHERE statu.pid=problem.pid AND statu.result=1)").update();
    }

    @Override
    public void getTimer() throws Exception {
        setEveryDay(3,0,0);
        new Timer().scheduleAtFixedRate(this, date, period);
    }
}
