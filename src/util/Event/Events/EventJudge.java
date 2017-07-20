package util.Event.Events;

import entity.Result;
import entity.Status;
import entity.User;
import util.Event.BaseTitleEvent;
import util.Main;
import util.SQL.SQL;
import dao.ChallengeSQL;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by QAQ on 2017/5/28.
 */
public class EventJudge extends BaseTitleEvent {
    public Status s;
	public static Map<Integer,Integer> blockScore;
	public static List<Integer> groupList;
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

    public static void getGroupList(int gro){
        groupList = new SQL("SELECT id FROM t_challenge_block WHERE isEditing = 0 AND gro = ?",gro).queryList();
    }

    public static void getBlockList(String username){
        blockScore = ChallengeSQL.getUserScore(username);
    }

	//获取某个group的得分
	public static int getGroupScore(int gro){
		int score = 0;
		try{
        Iterator<Integer> iter = groupList.iterator();
        while(iter.hasNext()){
            score += getBlockScore(iter.next());
        }
		}catch(Exception msg){
            score = 0;
        }
		return score;
	}

	//获取某个模块的得分
	public static int getBlockScore(int blockId){
		int score = 0;
		if (blockId != -1){  //单个模块
			if (blockScore.get(blockId) != null){
			score = blockScore.get(blockId);
			}
		}
		else{  //所有模块
			for (Integer value : blockScore.values()){
			    System.out.println(value);
				score += value;
			}
		}
		return score;
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
