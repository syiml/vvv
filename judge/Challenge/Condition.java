package Challenge;

import java.util.Map;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class Condition {
    int belongBlockId;
    int type;
    int par;
    int num;
    public Condition(int belongBlockId,int type,int par,int num){
        this.belongBlockId=belongBlockId;
        this.type=type;
        this.par=par;
        this.num=num;
    }

    /**
     * 如果本条件的type==1则需要调用此函数来判断
     * @param userScore 某user的每block积分
     * @return 条件成立返回true
     */
    boolean isTrue1(Map<Integer,Integer> userScore){
        return userScore.get(par)>=num;
    }
}
