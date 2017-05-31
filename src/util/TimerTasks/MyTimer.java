package util.TimerTasks;

import dao.LogDao;
import util.Main;
import util.SQL.SQL;

import java.util.*;

/**
 * 定时任务
 * Created by QAQ on 2016/8/25.
 */
public abstract class MyTimer extends TimerTask{
    protected Date date;
    protected long delay;
    protected long period;

    /**
     * 添加所有定时任务。未添加的不执行
     */
    private static void addAllTask() throws Exception {
        add(new TaskProblemSubmitCount());
        add(SQL.conns);
        add(Main.logs);
        add(new TaskWeekRankCount());
        add(new OneProblemEveryDay());
        add(new TaskUpdateAllUserRank());
        add(new RichRankTitleEvent());
    }

    public static void Init() throws Exception {
        addAllTask();
    }

    private static void add(MyTimer t){
        try {
            t.getTimer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设定为每天的h:m:s时间执行
     * 执行后使用date和period 给 Timer
     * @param h 小时，24小时制
     * @param m 分钟
     * @param s 秒钟
     */
    protected void setEveryDay(int h,int m,int s) throws Exception {
        if(h>=24||h<0||m>=60||m<0||s>=60||s<0){
            throw new Exception("任务时间非法");
        }
        period = 24L * 60 * 60 * 1000;
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY,h);
        ca.set(Calendar.MINUTE,m);
        ca.set(Calendar.SECOND,s);
        date = ca.getTime();
        if (date.before(new Date())) {
            ca.add(Calendar.DATE,1);
        }
        date = ca.getTime();
    }

    public abstract void run();
    public abstract void getTimer() throws Exception;
}
