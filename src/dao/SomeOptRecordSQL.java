package dao;

import entity.SomeOptRecord.ESomeOptRecordType;
import entity.SomeOptRecord.SomeOptRecord;
import util.SQL.SQL;
import util.Tool;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    /**
     * 为private不允许直接new，必须通过getInstance()返回单例
     */
    private SomeOptRecordSQL(){
    }

    /**
     * 获取操作信息列表，对应参数筛选。如果为null则该项筛选无效
     * @param type 记录类型
     * @param user 记录用户名
     * @param from 开始时间
     * @param to   截止时间
     * @param id   对应id
     * @return 操作列表
     */
    public List<SomeOptRecord> getRecord(ESomeOptRecordType type,String user,Timestamp from,Timestamp to,Integer id){
        String unionString = "WHERE";
        List<Object> args = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM t_some_opt_record ");
        if(type != null){
            sqlBuilder.append(unionString).append(" `type`=? ");
            args.add(type.getValue());
            unionString = "AND";
        }
        if(user != null){
            sqlBuilder.append(unionString).append(" `username`=? ");
            args.add(user);
            unionString = "AND";
        }
        if(from != null){
            sqlBuilder.append(unionString).append(" `time`>=? ");
            args.add(from);
            unionString = "AND";
        }
        if(to != null){
            sqlBuilder.append(unionString).append(" `time`<? ");
            args.add(to);
            unionString = "AND";
        }
        if(id != null){
            sqlBuilder.append(unionString).append(" `id`=? ");
            args.add(id);
        }
        return new SQL(sqlBuilder.toString(),args).queryBeanList(SomeOptRecord.class);
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
    public List<SomeOptRecord> getRecord(ESomeOptRecordType type,int id){
        return new SQL("SELECT * FROM t_some_opt_record WHERE `type`=? AND id=?",type.getValue(),id).queryBeanList(SomeOptRecord.class);
    }
}
