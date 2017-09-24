package entity.UserGroup;

import util.HTML.HTML;

/**
 * Created by QAQ on 2017/9/24.
 */
public enum GroupMemberStatus {
    LEADER(1),//队长
    MEMBER(2),//队员
    APPLICANT(3),//正在申请加入
    NOT(0);//非队员

    int id;
    GroupMemberStatus(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GroupMemberStatus getByID(int id){
        for(GroupMemberStatus t : GroupMemberStatus.values()){
            if(id == t.id) return t;
        }
        return null;
    }

    public String HTML(){
        switch (id){
            case 1: return HTML.textb("队长","green");
            case 2: return HTML.text("队员","green");
            case 3: return HTML.text("申请加入","gray");
        }
        return "-";
    }
}
