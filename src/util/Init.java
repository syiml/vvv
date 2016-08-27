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
        Main.Init();
        MyTimer.Init();
        EventMain.Init();
    }
}
