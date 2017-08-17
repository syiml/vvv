package util.Event.Events;

import entity.Block;
import entity.Result;
import entity.Status;
import entity.User;
import servise.ChallengeMain;
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
    Set<Integer> openBlocks;
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

        blockScore = ChallengeSQL.getUserScore(s.getUser());
        openBlocks = ChallengeSQL.getOpenBlocks(s.getUser());
    }

	//获取某个group的得分
    private int getGroupScore(int gro){
		int score = 0;
		try{
		    for(Integer id: openBlocks){
                Block block = ChallengeMain.blocks.get(id);
                if(block!=null && block.getGroup() == gro){
                    score += getBlockScore(id);
                }
            }
        }catch(Exception msg){
            score = 0;
            msg.printStackTrace();
        }
		return score;
	}

	//获取某个模块的得分
    private int getBlockScore(int blockId){
		int score = 0;
		if (blockId != -1){  //单个模块
			if (openBlocks.contains(blockId) && blockScore.containsKey(blockId)){
			    score = blockScore.get(blockId);
			}
		}
		else{  //所有模块
            for(Integer id: openBlocks){
                score += getBlockScore(id);
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
