package dao;

import entity.Log;
import util.Main;
import util.MyTime;
import util.SQL.SQL;
import util.TimerTasks.MyTimer;
import util.Tool;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by QAQ on 2016-2-27.
 */
public class LogDao extends MyTimer{
    /******
     * 定时清理过期log
     */

    private Calendar ca = Calendar.getInstance();

    public Log getLog(int id){
        return new SQL("select * from t_log where id=?",id).queryBean(Log.class);
    }

    public void save(Log log){
        new SQL("insert into t_log(time,text,sessionUser) values(?,?,?)"
                ,log.getTime(),log.getText(),log.getSessionUser()).update(false);
    }

    public List<Log> getLogs(int from,int num){
        return new SQL("select * from t_log order by id desc limit ?,?",from,num).queryBeanList(Log.class);
    }

    public int getLogsNum(){
        return new SQL("select count(*) from t_log ").queryNum();
    }

    /**
     * 删除一个月前的数据
     */
    private void removeExpired(){
        Timestamp time = new Timestamp(Tool.now().getTime() - (1000L*60*60*24*30));
        new SQL("DELETE FROM t_log WHERE time < ?",time).update();
    }

    @Override
    public void run() {
        removeExpired();
        ca.add(Calendar.MONTH,1);
        new Timer().schedule(this,date.getTime());
    }

    @Override
    public void getTimer(){
        //每个月1号2点更新
        ca.set(Calendar.DAY_OF_MONTH,1);
        ca.set(Calendar.HOUR_OF_DAY,2);
        ca.set(Calendar.MINUTE,0);
        ca.set(Calendar.SECOND,0);
        date = ca.getTime();
        if (date.before(new Date())) {
            ca.add(Calendar.MONTH,1);
        }
        date = ca.getTime();
        new Timer().schedule(this,date.getTime());
    }
}
