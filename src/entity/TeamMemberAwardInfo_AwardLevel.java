package entity;

/**
 * Created by QAQ on 2016/6/30 0030.
 */
public enum TeamMemberAwardInfo_AwardLevel {
    NONE(-2),//无奖项
    TENACIOUSLY(-1),//顽强拼搏奖
    ENCOURAGING(0),//优秀奖/鼓励奖
    BRANZE(1),//铜奖
    SILVER(2),//银奖
    GOLD(3),//金奖
    LV1(4),//一等奖
    LV2(5),//二等奖
    LV3(6);//三等奖

    TeamMemberAwardInfo_AwardLevel(int code){
        this.code = code;
    }
    private int code;

    public static TeamMemberAwardInfo_AwardLevel getByCode(int code){
        for(TeamMemberAwardInfo_AwardLevel it : TeamMemberAwardInfo_AwardLevel.values()){
            if(it.code == code){
                return it;
            }
        }
        return TeamMemberAwardInfo_AwardLevel.NONE;
    }

    public String toString(){
        if(this == TeamMemberAwardInfo_AwardLevel.TENACIOUSLY) return "顽强拼搏奖";
        if(this == TeamMemberAwardInfo_AwardLevel.ENCOURAGING) return "鼓励奖";
        if(this == TeamMemberAwardInfo_AwardLevel.BRANZE) return "铜奖";
        if(this == TeamMemberAwardInfo_AwardLevel.SILVER) return "银奖";
        if(this == TeamMemberAwardInfo_AwardLevel.GOLD) return "金奖";
        if(this == TeamMemberAwardInfo_AwardLevel.LV1) return "一等奖";
        if(this == TeamMemberAwardInfo_AwardLevel.LV2) return "二等奖";
        if(this == TeamMemberAwardInfo_AwardLevel.LV3) return "三等奖";
        return "无奖项";
    }

    public int getCode() {
        return code;
    }
}
