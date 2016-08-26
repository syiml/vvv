package util.TimerTasks;

import util.Main;
import util.Tool;

import java.util.Timer;

/**
 * Created by Syiml on 2015/7/10 0010.
 * 自动连接数据库类
 * 由于mysql默认8小时没有数据交互就自动断开连接
 * 所以每过一段时间(6小时)就自动重新连接上数据库
 */
public class autoConnecter extends MyTimer{

    /**
     * 每过6小时自动重置连接池
     */
    public void run(){
        Tool.debug("autoConnecter run");
        Main.conns.clear();
    }

    @Override
    public void getTimer() throws Exception {
        new Timer().schedule(this,Main.autoConnectionTimeMinute * 60000,Main.autoConnectionTimeMinute * 60000);
    }
}
