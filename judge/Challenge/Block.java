package Challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 挑战模式下的一个模块
 * Created by Syiml on 2015/10/10 0010.
 */
public class Block {
    int id;
    String name;
    int group;//所属专题：如 基础、数据结构、数论
    int score;//题目的总积分
    public List<Condition> conditions;
    public Block(int id,String name,int group){
        this.id=id;
        this.name=name;
        this.group=group;
        this.score=0;
        conditions=new ArrayList<Condition>();
    }
    public String getName(){
        return name;
    }
    public String getText(){
        return ChallengeSQL.getText(id);
    }
    public int getScore(){return score;}
    public void addCondition(Condition c){
        conditions.add(c);
    }
    public void setScore(int sore){
        this.score=sore;
    }
    /**
     *
     * @param userScore 某user的每block积分
     * @return 条件列表conditions全部满足则返回true
     */
    public boolean isTrue(Map<Integer,Integer> userScore){
        for(Condition c:conditions){
            if(!c.isTrue1(userScore))return false;
        }
        return true;
    }
    public boolean pBlockOpen(Set<Integer> openBlocks){
        for(Condition c:conditions){
            int b=c.getPar();
            if(!openBlocks.contains(b))return false;
        }
        return true;
    }
}
