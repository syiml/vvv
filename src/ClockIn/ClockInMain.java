package ClockIn;

import dao.SomeOptRecordSQL;
import entity.Enmu.AcbOrderType;
import entity.SomeOptRecord.ESomeOptRecordType;
import entity.SomeOptRecord.SomeOptRecord;
import entity.User;
import servise.MessageMain;
import util.Event.EventMain;
import util.Event.Events.EventClockIn;
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
            Main.users.addACB(u.getUsername(), clock_in_award_acb , AcbOrderType.CLOCK_IN,"");
            MessageMain.addMessageAwardACB(u.getUsername(),clock_in_award_acb ,"每日签到奖励");
        }
        int ret = someOptRecordSQL.addRecord(ESomeOptRecordType.ClockIn,u.getUsername(), Tool.now(),0,Main.getIP());
        SomeOptRecord record = new SomeOptRecord();
        record.setUsername(u.getUsername());
        record.setType(ESomeOptRecordType.ClockIn);
        record.setTime(Tool.now());
        record.setData(Main.getIP());
        EventMain.triggerEvent(new EventClockIn(u,record));
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
    public static boolean autoClockIn(){
        User u = Main.loginUser();
        if(u==null) return false;
        if(u.autoClockInTime == null || !MyTime.getFistTimeOfHour(u.autoClockInTime).equals(MyTime.getFistTimeOfHour(Tool.now()))){
            u.autoClockInTime = Tool.now();
            return clockIn() == MainResult.SUCCESS;
        }
        return false;
    }
}
