package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 手写连接池
 * 队列存储，SQL对象的close方法即归还连接池。
 * Created by Administrator on 2015/11/22 0022.
 */
public class DBConnectionPool {
    public static int num=0;
    /**
     * 连接队列
     */
    BlockingQueue<Connection> conns=new ArrayBlockingQueue<Connection>(10);
    public DBConnectionPool(){}

    /**
     * 创建一个新的连接
     * @return 连接
     */
    private Connection getNew(){
        Tool.debug(conns.size()+" 新建连接");
        num++;
        try {
            return DriverManager.getConnection(Main.GV.get("sqlconnstring").toString(), Main.GV.get("sqlusername").toString(), Main.GV.get("sqlpassword").toString());
        } catch (SQLException e) {
            Tool.log("===连接失败，请检查数据库是否已经启动===");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从连接池里取出一个连接
     * @return 连接
     */
    public Connection getConn(){
        Connection ret=conns.poll();
        if(ret==null){
            ret=getNew();//队列为空则直接新建一个连接
        }else{
            Tool.debug("取出连接 "+conns.size());
        }
        return ret;
    }

    /**
     * 归还连接池
     * @param c 连接
     */
    public void putCondition(Connection c){
        if(!conns.offer(c)){
            try {
                c.close();
                Tool.debug(conns.size()+"删除连接");
                num--;
            } catch (SQLException ignored) {}
        }else{
            Tool.debug("存入连接"+conns.size()+"/"+num);
        }
    }

    /**
     * 清空连接池
     * MySQL需要8小时重置连接
     */
    public void clear(){
        Connection ret;
        while((ret=conns.poll())!=null){
            try {
                ret.close();
            } catch (Exception ignored) { }
        }
        num=0;
    }
}
