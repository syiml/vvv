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
	public Map<Integer,Integer> blockScore;
	public Map<Integer,Integer> groupList;
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

        groupList = new SQL("SELECT id,gro FROM t_challenge_block WHERE isEditing = 0 ORDER BY gro").queryMap();
        blockScore = ChallengeSQL.getUserScore(s.getUser());
    }

	//获取某个group的得分
	public int getGroupScore(int gro){
		int score = 0;
		try{
            Iterator<Map.Entry<Integer, Integer>> entries = groupList.entrySet().iterator();
            Map.Entry<Integer, Integer> entry;
            while (entries.hasNext()) {
                entry = entries.next();
                if (entry.getValue() == gro){
                    score += getBlockScore(entry.getKey());
                }
                else if (entry.getValue() > gro){ break;}
            }
        }catch(Exception msg){
            score = 0;
        }
		return score;
	}

	//获取某个模块的得分
	public int getBlockScore(int blockId){
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

        if (name.startsWith("[Block")){ //[BlockScore,1]
            int num = name.length();
            String buffer = name.substring(12,num-1); //获取模块id
            num = Integer.parseInt(buffer);
            return getBlockScore(num);
        }

        if (name.startsWith("[Group")){ //[GroupScore,1]
            int num = name.length();
            String buffer = name.substring(12,num-1);  //获取模块分组id
            num = Integer.parseInt(buffer);
            return getGroupScore(num);
        }

        return super.getInt(name);
    }
}
