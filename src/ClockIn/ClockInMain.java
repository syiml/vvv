package ClockIn;

import dao.SomeOptRecordSQL;
import entity.Enmu.AcbOrderType;
import entity.SomeOptRecord.ESomeOptRecordType;
import entity.User;
import servise.MessageMain;
import util.Main;
import util.MainResult;
import util.MyTime;
import util.Tool;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/3/16.
 */
public class ClockInMain {
    private static SomeOptRecordSQL someOptRecordSQL = SomeOptRecordSQL.getInstance();

    public static int clock_in_award_acb = 5;
    public static MainResult clockIn(){
        User u = Main.loginUser();
        if(u == null) return MainResult.NO_LOGIN;
        if(!canClockIn(u.getUsername())) return MainResult.FAIL;
        if(isFistClockInOfDay(u.getUsername())){
            Main.users.addACB(u.getUsername(), 5 , AcbOrderType.CLOCK_IN,"");
            MessageMain.addMessageAwardACB(u.getUsername(),5,"每日签到奖励");
        }
        int ret = someOptRecordSQL.addRecord(ESomeOptRecordType.ClockIn,u.getUsername(), Tool.now(),0,Main.getIP());
        if(ret == 1) {
            return MainResult.SUCCESS;
        }else{
            return MainResult.FAIL;
        }
    }
    public static boolean isFistClockInOfDay(String user){
        Timestamp now = Tool.now();
        return someOptRecordSQL.getRecord(ESomeOptRecordType.ClockIn,user, MyTime.getFistTimeOfDay(now),MyTime.getLastTimeOfDay(now)).size() == 0;
    }
    public static boolean canClockIn(String user){
        Timestamp now = Tool.now();
        return someOptRecordSQL.getRecord(ESomeOptRecordType.ClockIn,user, MyTime.getFistTimeOfHour(now),MyTime.getLastTimeOfHour(now)).size() == 0;
    }
}
