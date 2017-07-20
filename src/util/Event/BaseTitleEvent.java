package util.Event;

import entity.Title.title_value;
import entity.User;
import util.Tool;
import util.Event.Events.EventJudge;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/27.
 */
public class BaseTitleEvent extends BaseEvent {
    public User user;
    private Timestamp event_time;
    protected BaseTitleEvent(User u){
        this.user = u;
        event_time = Tool.now();
    }

    public Integer getInt(String name){
        switch (name){
            case "acnum": return user.getAcnum();
            case "acb":   return user.getAcb();
            case "rating":return user.getShowRating();
            case "rating_num":return user.getRatingnum();
            case "status":return user.getInTeamStatus();
        }
		if (name.startsWith("[Block")){ //[BlockScore,1]
		    int num = name.length();
		    String buffer = name.substring(12,num-1); //获取模块id
            num = Integer.parseInt(buffer);
            EventJudge.getBlockList(user.getUsername()); //更新模块得分
			return EventJudge.getBlockScore(num);
		}

        if (name.startsWith("[Group")){ //[GroupScore,1]
            int num = name.length();
            String buffer = name.substring(12,num-1);  //获取模块分组id
            num = Integer.parseInt(buffer);
            EventJudge.getBlockList(user.getUsername()); //更新模块得分
            EventJudge.getGroupList(num);                //更新模块分组列表
            return EventJudge.getGroupScore(num);
        }

        return super.getInt(name);
    }

    @Override
    public Timestamp getTimestamp(String name) {
        if(name.equals("event_time")) return event_time;
        return super.getTimestamp(name);
    }
}
