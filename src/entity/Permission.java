package entity;

import util.HTML.HTML;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限类
 * 要增加权限，在 PermissionType 增加枚举类型
 * Created by Syiml on 2015/6/14 0014.
 */

public class Permission{
    private Set<Integer> permissions;
    private boolean showAdmin = false;

    /**
     * 判断是否有某权限
     * @param e 权限枚举类型
     * @return 有权限返回true，没有返回false
     */
    public boolean havePermissions(PermissionType e){
        return permissions.contains(e.getCode());
    }

    /**
     * 根据数据库结果集产生
     * @param rs 数据库结果集，userPer表的单个User记录
     */
    public Permission(ResultSet rs) {
        try {
            permissions = new HashSet<>();
            while(rs.next()){
                int z=rs.getInt(1);
                PermissionType p = PermissionType.getPerByCode(z);
                permissions.add(p.getCode());
                showAdmin |= p.isShowAdmin();
            }
        } catch (SQLException e) {
            Tool.log(e);
        }
    }
    public Permission(){}
    /**
     * 产生显示在个人页面的权限列表
     * @return 权限列表HTML代码
     */
    public String toHTML(){
        String s=" ";
        for(Integer code: permissions){
            PermissionType p =  PermissionType.getPerByCode(code);
            s+=HTML.span(p.getType()==0?"success":"error",p.getName())+" ";
        }
        return s;
    }
    public boolean haveAdmin(){return showAdmin;}

    public boolean getAddProblem(){return havePermissions(PermissionType.addProblem);}
    public boolean getAddLocalProblem(){return havePermissions(PermissionType.addLocalProblem);}
    public boolean getViewCode(){return havePermissions(PermissionType.viewCode);}
    public boolean getReJudge(){return havePermissions(PermissionType.reJudge);}
    public boolean getShowHideProblem(){return havePermissions(PermissionType.addContest);}
    public boolean getAddContest(){return havePermissions(PermissionType.addContest);}
    public boolean getComputrating() {return havePermissions(PermissionType.computeRating);}
    public boolean getAddDiscuss() {return havePermissions(PermissionType.addDiscuss);}
    public boolean getAddTag(){return havePermissions(PermissionType.addTag);}
    public boolean getClockIn(){return havePermissions(PermissionType.clockIn);}
    public boolean getPermissionAdmin(){return havePermissions(PermissionType.PermissionAdmin);}
    public boolean getAwardACB(){return havePermissions(PermissionType.awardACB);}
    public boolean getContestRegisterAdmin(){return havePermissions(PermissionType.contestRegisterAdmin);}
    public boolean getChallengeAdmin(){return havePermissions(PermissionType.challengeAdmin);}
    public boolean getResetPassword(){return havePermissions(PermissionType.resetPassword);}
    public boolean getUserAdmin(){return havePermissions(PermissionType.userAdmin);}
    public boolean getViewLog(){return havePermissions(PermissionType.viewLog);}
    public boolean getExamAdmin(){return havePermissions(PermissionType.examAdmin);}
    public boolean getTeamMemberAdmin() {return havePermissions(PermissionType.teamMemberAdmin);}
    public boolean getMallAdmin(){return havePermissions(PermissionType.mallAdmin);}
    public boolean getAppUpdate() { return havePermissions(PermissionType.appUpdate);}
}
