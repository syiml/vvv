package util.Event.Events;

import dao.SomeOptRecordSQL;
import entity.SomeOptRecord.ESomeOptRecordType;
import entity.SomeOptRecord.SomeOptRecord;
import entity.User;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.MyTime;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/29.
 */
public class EventClockIn extends BaseTitleEvent {
    private int total_num;
    private int today_num;
    SomeOptRecord record;

    public EventClockIn(User user, SomeOptRecord record){
        super(user);
        this.record = record;
    }

    @Override
    public void before() {
        //获取两个数据
        total_num = SomeOptRecordSQL.getInstance().getRecordNum(ESomeOptRecordType.ClockIn,user.getUsername(),null,null,null);
        today_num = SomeOptRecordSQL.getInstance().getRecordNum(ESomeOptRecordType.ClockIn,user.getUsername(), MyTime.getFistTimeOfDay(record.getTime()),null,null);
    }

    @Override
    public Integer getInt(String name) {
        switch (name){
            case "total_num":return total_num;
            case "today_num":return today_num;
        }
        return super.getInt(name);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name==null) return super.getTimestamp(null);
        switch (name){
            case "clock_in_time":return record.getTime();
        }
        return super.getTimestamp(name);
    }
}
