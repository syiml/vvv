package util;

/**
 * Created by Syiml on 2015/7/10 0010.
 * 自动连接数据库类
 * 由于mysql默认8小时没有数据交互就自动断开连接
 * 所以每过一段时间(6小时)就自动重新连接上数据库
 */
public class autoConnecter implements Runnable{
    /**
     * 连接数据库
     */
    public void conn(){
        Main.conns.clear();
    }

    /**
     * 每过6小时自动连接数据库
     */
    public void run(){
        Tool.sleep(Main.autoConnectionTimeMinute * 60000);
        while(true){
            conn();
            Tool.sleep(Main.autoConnectionTimeMinute * 60000);
//            Main.conn.close();
        }
    }
}
