package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class DBConnectionPool {
    public static int num=0;
    BlockingQueue<Connection> conns=new ArrayBlockingQueue<Connection>(10);
    public DBConnectionPool(){}
    private Connection getNew(){
        Main.debug(conns.size()+" 新建连接");
        num++;
        try {
            return DriverManager.getConnection(Main.GV.get("sqlconnstring").toString(), Main.GV.get("sqlusername").toString(), Main.GV.get("sqlpassword").toString());
        } catch (SQLException e) {
            Main.log("=连接失败=");
        }
        return null;
    }
    public Connection getConn(){
        Connection ret=conns.poll();
        if(ret==null){
            ret=getNew();
        }else{
            Main.debug("取出连接 "+conns.size());
        }
        return ret;
    }
    public void putCondition(Connection c){
        if(!conns.offer(c)){
            try {
                c.close();
                Main.debug(conns.size()+"删除连接");
                num--;
            } catch (SQLException ignored) {}
        }else{
            Main.debug("存入连接"+conns.size()+"/"+num);
        }
    }
    public void clear(){
        Connection ret;
        while((ret=conns.poll())!=null){
            try {
                ret.close();
            } catch (SQLException ignored) {}
        }
        num=0;
    }
}
