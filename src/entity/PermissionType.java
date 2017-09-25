package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syimlzhu on 2016/10/11.
 */
public enum PermissionType {
    addProblem(1, "题目总管"),
    addLocalProblem(12, "增加本地题目"),
    viewCode(2, "查看代码", false),
    reJudge(3, "重判"),
    addContest(4, "新增比赛"),
    contestRegisterAdmin(11, "审核比赛报名", false),
    computeRating(5, "计算rating", false),
    addDiscuss(6, "新增讨论"),
    addTag(7, "新增标签"),
    clockIn(8, "签到管理", false),
    PermissionAdmin(9, "权限管理"),
    awardACB(10, "奖励ACB"),
    challengeAdmin(13, "挑战模式管理"),
    resetPassword(14, "密码重置"),
    userAdmin(15, "用户管理"),//用户管理，可以查看修改用户详细信息，认证正式队员
    viewLog(16, "查看log"),
    examAdmin(17, "考试管理"),
    teamMemberAdmin(18, "集训队员管理"),
    mallAdmin(19, "商城管理"),
    appUpdate(20, "APP更新"),
    verify_all(21, "认证管理（全部）"),
    verify_school(22, "认证管理（校内人员）"),
    verify_association(23, "认证管理（协会成员）"),
    verify_retired(24, "认证管理（退役队员）"),
    verify_team(25, "认证管理（集训队员）"),
    teamAutoRegister(26, "集训队员自动报名"),
    partAddProblem(27,"添加题目"),
    titleAdmin(28,"称号管理",false),
    groupAdmin(29,"组队管理");

    private static Map<Integer, PermissionType> allPermission = null;
    private int code;
    private String name;
    /**
     * type = 0 表示是正向的权限，1表示负面，显示时有所区别
     */
    private int type;
    /**
     * 拥有这个权限时是否在个人界面显示admin链接
     */
    private boolean showAdmin = true;

    PermissionType(int code, String name, boolean showAdmin, int type) {
        this.code = code;
        this.name = name;
        this.showAdmin = showAdmin;
        this.type = type;
    }

    PermissionType(int code, String name, boolean showAdmin) {
        this.code = code;
        this.name = name;
        this.showAdmin = showAdmin;
    }

    PermissionType(int code, String name) {
        this.code = code;
        this.name = name;
        this.type = 0;
    }

    public static PermissionType getPerByCode(int code) {
        if (allPermission == null) {
            allPermission = new HashMap<>();
            for (PermissionType permissionEnum : PermissionType.values()) {
                allPermission.put(permissionEnum.code, permissionEnum);
            }
        }
        return allPermission.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public boolean isShowAdmin() {
        return showAdmin;
    }
}
