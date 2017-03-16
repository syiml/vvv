package dao;

import entity.SomeOptRecord.ESomeOptRecordType;
import entity.SomeOptRecord.SomeOptRecord;
import util.SQL.SQL;
import util.Tool;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by QAQ on 2017/3/16.
 */
public class SomeOptRecordSQL {
    private static SomeOptRecordSQL sql = new SomeOptRecordSQL();
    /**
     * 单例
     * @return SomeOptRecordSQL对象
     */
    public static SomeOptRecordSQL getInstance(){
        return sql;
    }
    private SomeOptRecordSQL(){

    }
    public List<SomeOptRecord> getRecord(ESomeOptRecordType type,String user, Timestamp from,Timestamp to){
        return new SQL("SELECT * FROM t_some_opt_record WHERE `type`=? AND `username`=? AND `time`>=? AND `time`<? ORDER BY `time`",
                type.getValue(),user,from,to
        ).queryBeanList(SomeOptRecord.class);
    }
    public List<SomeOptRecord> getRecord(ESomeOptRecordType type, Timestamp from,Timestamp to){
        return new SQL("SELECT * FROM t_some_opt_record WHERE `type`=? AND `time`>=? AND `time`<? ORDER BY `time`",
                type.getValue(),from,to
        ).queryBeanList(SomeOptRecord.class);
    }
    public int addRecord(ESomeOptRecordType type,String username,Timestamp time,int id,String data){
        return new SQL("INSERT INTO t_some_opt_record VALUES(?,?,?,?,?)",username, time,type.getValue(),id,data).update();
    }

}
