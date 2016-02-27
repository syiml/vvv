package dao;

import entity.Log;
import util.SQL;

import java.util.List;

/**
 * Created by QAQ on 2016-2-27.
 */
public class LogDao {
    public Log getLog(int id){
        return new SQL("select * from t_log where id=?",id).queryBean(Log.class);
    }
    public void save(Log log){
        new SQL("insert into t_log(time,text,sessionUser) values(?,?,?)"
                ,log.getTime(),log.getText(),log.getSessionUser()).update();
    }
    public List<Log> getLogs(int from,int num){
        return new SQL("select * from t_log order by id desc limit ?,?",from,num).queryBeanList(Log.class);
    }
    public int getLogsNum(){
        return new SQL("select count(*) from t_log ").queryNum();
    }
}
