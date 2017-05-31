package util.Event.Events;

import entity.Result;
import entity.Status;
import entity.User;
import util.Event.BaseTitleEvent;
import util.Main;
import util.SQL.SQL;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventJudge extends BaseTitleEvent {
    public Status s;
    int is_repeat;
    public EventJudge(User u, Status s) {
        super(u);
        this.s = s;
    }
    public void before(){
        if(s.getResult() == Result.AC){
            int acNum = new SQL("SELECT COUNT(*) FROM statu WHERE pid=? AND ruser=? AND result=? AND id<?",s.getPid(),s.getUser(),Result.AC.getValue(),s.getRid()).queryNum();
            if(acNum == 0) is_repeat = 0;
            else is_repeat = 1;
        }else {
            is_repeat = 2;
        }
    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name == null) return super.getTimestamp(null);
        switch (name){
            case "submit_time":return s.getSbmitTime();
        }
        return super.getTimestamp(name);
    }

    @Override
    public Integer getInt(String name) {
        switch (name){
            case "judge_result":return s.getResult().getValue();
            case "is_repeat":return is_repeat;
        }
        return super.getInt(name);
    }
}
