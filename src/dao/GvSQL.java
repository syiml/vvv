package dao;

import util.SQL.SQL;

/**
 * Created by QAQ on 2016/6/26 0026.
 */
public class GvSQL {
    public String get(String key){
        return new SQL("SELECT value FROM t_gv WHERE `key`=?",key).queryString();
    }
    public void set(String key,String value){
        new SQL("REPLACE INTO t_gv VALUES(?,?)",key,value).update();
    }
}
