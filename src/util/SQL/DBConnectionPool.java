package util.SQL;

import util.Main;
import util.TimerTasks.MyTimer;
import util.Tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 手写连接池
 * 队列存储，SQL对象的close方法即归还连接池。
 *
 * 并且自动连接数据库
 * 由于mysql默认8小时没有数据交互就自动断开连接
 * 所以每过一段时间(6小时)就自动重新连接上数据库
 *
 * 链接可能未被6小时清空
 *
 * Created by Administrator on 2015/11/22 0022.
 */
public class DBConnectionPool extends MyTimer {
    public static int num=0;
    /**
     * 连接队列
     */
    private BlockingQueue<Connection> Connections =new ArrayBlockingQueue<>(10);
    DBConnectionPool(){}

    /**
     * 创建一个新的连接
     * @return 连接
     */
    private Connection getNew(){
        num++;
        try {
            return DriverManager.getConnection(Main.config.sqlconnstring, Main.config.sqlusername, Main.config.sqlpassword);
        } catch (SQLException e) {
            Tool.log("===连接失败，请检查数据库是否已经启动===");
        }
        return null;
    }

    /**
     * 从连接池里取出一个连接
     * @return 连接
     */
    Connection getConn(){
        Connection ret= Connections.poll();
        if(ret==null){
            ret=getNew();//队列为空则直接新建一个连接
        }
        return ret;
    }

    /**
     * 归还连接池
     * @param c 连接
     */
    void putCondition(Connection c){
        if(!Connections.offer(c)){
            try {
                c.close();
                num--;
            } catch (SQLException ignored) {}
        }
    }

    /**
     * 清空连接池
     * MySQL需要8小时重置连接
     */
    public void clear(){
        Connection ret;
        while((ret= Connections.poll())!=null){
            try {
                ret.close();
            } catch (Exception ignored) { }
        }
        num=0;
    }

    @Override
    public void run() {
        Tool.debug("autoConnect run");
        clear();
    }

    @Override
    public void getTimer(){
        new Timer().schedule(this,Main.config.autoConnectionTimeMinute * 60000,Main.config.autoConnectionTimeMinute * 60000);
    }
}
