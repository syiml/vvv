package entity;

import util.HTML.HTML;

/**
 * Created by QAQ on 2016/6/30 0030.
 */
public enum TeamMemberAwardInfo_ContestLevel {
    NONE(-1),//其他、不显示在奖项列表中
    PROVINCE(0),//ACM省赛
    REGIONAL(1),//ACM/ICPC区域赛
    EC_FINAL(2),//EC-Final
    WORLD_FINAL(3),//世界总决赛
    LANQIAOBEI(4),//全国蓝桥杯大赛
    YAOQINGSAI(5),//ACM全国邀请赛
    CCPC(6);//全国大学生程序设计竞赛

    TeamMemberAwardInfo_ContestLevel(int code){
        this.code = code;
    }
    private int code;

    public static TeamMemberAwardInfo_ContestLevel getByCode(int code){
        for(TeamMemberAwardInfo_ContestLevel it : TeamMemberAwardInfo_ContestLevel.values()){
            if(it.code == code){
                return it;
            }
        }
        return TeamMemberAwardInfo_ContestLevel.NONE;
    }

    public String toString(){
        if(this == TeamMemberAwardInfo_ContestLevel.PROVINCE) return "ACM福建省省赛";
        if(this == TeamMemberAwardInfo_ContestLevel.REGIONAL) return "ACM/ICPC亚洲区域赛";
        if(this == TeamMemberAwardInfo_ContestLevel.EC_FINAL) return "ACM/ICPC东亚赛区总决赛(EC-Final)";
        if(this == TeamMemberAwardInfo_ContestLevel.LANQIAOBEI) return "全国蓝桥杯软件设计大赛";
        if(this == TeamMemberAwardInfo_ContestLevel.WORLD_FINAL) return "ACM/ICPC世界总决赛";
        if(this == TeamMemberAwardInfo_ContestLevel.YAOQINGSAI) return "ACM/ICPC全国邀请赛";
        if(this == TeamMemberAwardInfo_ContestLevel.CCPC) return "中国大学生程序设计竞赛";
        return "其他";
    }

    public int getCode() {
        return code;
    }
}
