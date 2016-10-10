package entity;

import util.HTML.HTML;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/6/14 0014.
 */

public class Permission{
    boolean addProblem=false;
    boolean addLocalProblem=false;
    boolean viewCode=false;
    boolean reJudge=false;
    boolean showHideProblem=false;
    boolean addContest=false;
    boolean contestRegisterAdmin=false;

    boolean computrating=false;
    boolean addDiscuss=false;

    boolean addTag=false;
    boolean clockin=false;

    boolean PermissionAdmin=false;
    boolean awardACB=false;

    boolean challengeAdmin=false;
    boolean resetPassword=false;

    boolean userAdmin=false;//用户管理，可以查看修改用户详细信息，认证正式队员

    boolean viewLog=false;

    boolean examAdmin=false;
    boolean teamMemberAdmin=false;

    boolean mallAdmin=false;
    boolean appUpdate=false;
    /**
     * 根据数据库结果集产生
     * @param rs 数据库结果集，userPer表的单个User记录
     */
    public Permission(ResultSet rs) {
        try {
            while(rs.next()){
                int z=rs.getInt(1);
                switch(z){
                    case 1: addProblem=true;break;
                    case 2: viewCode=true;break;
                    case 3: reJudge=true;break;
                    case 4: addContest=true;
                            showHideProblem=true;
                            break;
                    case 5: computrating=true;break;
                    case 6: addDiscuss=true;break;
                    case 7: addTag=true;break;
                    case 8: clockin=true;break;
                    case 9: PermissionAdmin=true;break;
                    case 10:awardACB=true;break;
                    case 11:contestRegisterAdmin=true;break;
                    case 12:addLocalProblem=true;break;
                    case 13:challengeAdmin=true;break;
                    case 14:resetPassword=true;break;
                    case 15:userAdmin=true;break;
                    case 16:viewLog=true;break;
                    case 17:examAdmin=true;break;
                    case 18:teamMemberAdmin=true;break;
                    case 19:mallAdmin=true;break;
                    case 20:appUpdate=true;break;
                }
            }
        } catch (SQLException e) {
            Tool.log(e);
        }
    }
    public Permission(){}
    public boolean getAddProblem(){return addProblem;}
    public boolean getAddLocalProblem(){return addLocalProblem;}
    public boolean getViewCode(){return viewCode;}
    public boolean getReJudge(){return reJudge;}
    public boolean getShowHideProblem(){return showHideProblem;}
    public boolean getAddContest(){return addContest;}
    public boolean getComputrating() {return computrating;}
    public boolean getAddDiscuss() {return addDiscuss;}
    public boolean getAddTag(){return addTag;}
    public boolean getClockIn(){return clockin;}
    public boolean getPermissionAdmin(){return PermissionAdmin;}
    public boolean getAwardACB(){return awardACB;}
    public boolean getContestRegisterAdmin(){return contestRegisterAdmin;}
    public boolean getChallengeAdmin(){return challengeAdmin;}
    public boolean getResetPassword(){return resetPassword;}
    public boolean getUserAdmin(){return userAdmin;}
    public boolean getViewLog(){return viewLog;}
    public boolean getExamAdmin(){return examAdmin;}
    public boolean getTeamMemberAdmin() {return teamMemberAdmin;}
    public boolean getMallAdmin(){return mallAdmin;}
    public boolean getAppUpdate() {
        return appUpdate;
    }

    /**
     * 产生显示在个人页面的权限列表
     * @return 权限列表HTML代码
     */
    public String toHTML(){
        String s=" ";
        if(addProblem) s+=(HTML.span("success","添加题目"))+" ";
        if(addLocalProblem) s+=(HTML.span("success","添加本地题目"))+" ";
        if(viewCode) s+=(HTML.span("success","查看代码"))+" ";
        if(reJudge) s+= (HTML.span("success","重判"))+" ";
        if(showHideProblem) s+=(HTML.span("success","查看隐藏题目"))+" ";
        if(computrating) s+=(HTML.span("success","计算rating"))+" ";
        if(addContest) s+=(HTML.span("success","添加比赛"))+" ";
        if(addDiscuss) s+=(HTML.span("success","Discuss管理"))+" ";
        if(addTag) s+=(HTML.span("success","编辑标签"))+" ";
        if(clockin) s+=(HTML.span("success","签到管理"))+" ";
        if(PermissionAdmin) s+=(HTML.span("success","权限管理"))+" ";
        if(awardACB) s+=(HTML.span("success","奖惩ACB"))+" ";
        if(resetPassword) s+=(HTML.span("success","密码重置"))+" ";
        if(contestRegisterAdmin) s+=(HTML.span("success","审核比赛报名"))+" ";
        if(challengeAdmin) s+=(HTML.span("success","挑战模式管理"))+" ";
        if(userAdmin) s+=(HTML.span("success","用户管理"))+" ";
        if(viewLog) s+=(HTML.span("success","Log查看"))+" ";
        if(examAdmin) s+=(HTML.span("success","考试管理"))+" ";
        if(teamMemberAdmin) s+=(HTML.span("success","集训队员管理"))+" ";
        if(mallAdmin) s+=(HTML.span("success","商城管理"))+" ";
        if(appUpdate) s+=(HTML.span("success","APP更新"))+" ";
        return s;
    }
    public boolean haveAdmin(){
        return addProblem||reJudge||addContest||computrating||addDiscuss||addTag||PermissionAdmin||
                awardACB||addLocalProblem||challengeAdmin||resetPassword||userAdmin||viewLog||examAdmin||teamMemberAdmin||mallAdmin||appUpdate;
    }
}
