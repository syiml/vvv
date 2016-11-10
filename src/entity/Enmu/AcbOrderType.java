package entity.Enmu;

/**
 * Created by QAQ on 2016/10/26.
 */
public enum AcbOrderType{
    ADMIN(1,"管理员操作"),
    CONTEST_AUTO_REGISTER(2,"集训队员周赛自动报名"),
    CONTEST_AUTO_REGISTER_END(3,"集训队员周赛自动报名返还"),
    ADD_PROBLEM_TAG(4,"给题目贴标签"),
    CHALLENGE_BLOCK_OPEN(5,"挑战模式模块开启"),
    MALL_BUY(6,"购买商品"),
    MALL_RETURN(7,"商城订单取消"),
    CONTEST_AUTO_LEAVE(8,"集训队员周赛自动报名取消"),
    BUY_CODE(9,"购买代码查看权"),
    ONE_PROBLEM_EVERYDAY(10,"每日一题奖励"),
    WEEK_RANK(11,"活跃奖励");

    String des;
    int id;
    AcbOrderType(int id,String des){
        this.id = id;
        this.des = des;
    }

    public static AcbOrderType getById(int id){
        for(AcbOrderType acbOrderType:AcbOrderType.values()){
            if(acbOrderType.id == id) return acbOrderType;
        }
        return null;
    }

    public String getDes(){return des;}

    public int getId(){return id;}
}
