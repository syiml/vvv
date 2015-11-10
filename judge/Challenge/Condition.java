package Challenge;

import java.util.Map;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class Condition {
    int belongBlockId;
    int type;

    public int getPar() {
        return par;
    }

    int par;
    int num;

    int id;
    public Condition(int belongBlockId,int type,int par,int num,int id){
        this.belongBlockId=belongBlockId;
        this.type=type;
        this.par=par;
        this.num=num;
        this.id=id;
    }
    public int getId(){return id;}
    /**
     * 如果本条件的type==1则需要调用此函数来判断
     * @param userScore 某user的每block积分
     * @return 条件成立返回true
     */
    boolean isTrue1(Map<Integer,Integer> userScore){
        int v;
        if(userScore.containsKey(par)) v=userScore.get(par);
        else v=0;
        return v>=num;
    }

    public String toString(){
        Block b=ChallengeMain.blocks.get(par);
        return "在模块【("+b.id+")"+b.getName()+"】获得【"+num+"】积分";
    }
}
