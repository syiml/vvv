package util;

import util.Event.EventMain;
import util.TimerTasks.MyTimer;

import javax.servlet.http.HttpServlet;

/**
 * 服务器刚启动时要做的事
 * Created by QAQ on 2016/8/25.
 */
public class Init extends HttpServlet {
    public void init(){
        try {
            //Test.test();
            Main.Init();
            MyTimer.Init();
            EventMain.Init();
            Tool.log("服务器启动完成");
        }catch (Exception e){
            Tool.log("服务器启动时出现异常");
            e.printStackTrace();
        }
    }
}
