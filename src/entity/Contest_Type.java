package entity;

import util.HTML.HTML;

/**
 * 比赛权限类型
 * Created by QAQ on 2016/5/9 0009.
 */
public enum Contest_Type{
    ERROR(-1),
    PUBLIC(0),//公开权限，所有人都可以进入
    PASSWORD(1),//密码权限，设定一个密码，进入时需要输入密码
    PRIVATE(2),//私有权限，创建者可以添加指定的人进入
    REGISTER(3),//注册权限，指定时间内点击注册的人在比赛开始后可以进入
    REGISTER2(4),//正式权限，指定时间内点击注册，并通过审核后可以进入
    TEAM_OFFICIAL(5),//组队权限，报名时填写组队信息，通过审核后使用预先分配的账号进入
    SIZE(6);//无实际用处，用于获取大小，添加时把它放在最后

    Contest_Type(int code){
        this.code = code;
    }
    private int code;

    public int getCode() {
        return code;
    }

    public static Contest_Type getByCode(int code){
        for(Contest_Type it : Contest_Type.values()){
            if(it.code == code){
                return it;
            }
        }
        return Contest_Type.ERROR;
    }

    public String getHTML(int cid){
        if(this==Contest_Type.PUBLIC){
            return "<b style='color:green'>公开</b>";
        }else if(this==Contest_Type.PASSWORD){
            return "<b style='color:red'>密码</b>";
        }else if(this==Contest_Type.PRIVATE){
            return HTML.a("User.jsp?cid=" + cid, "<b style='color:#AE57A4'>私有</b>");
        }else if(this==Contest_Type.REGISTER){
            return HTML.a("User.jsp?cid="+cid,"<b style='color:blue'>注册</b>");
        }else if(this==Contest_Type.REGISTER2){
            return HTML.a("User.jsp?cid="+cid,"<b style='color:orange'>正式</b>");
        }else if(this==Contest_Type.TEAM_OFFICIAL){
            return HTML.a("User.jsp?cid="+cid,HTML.textb("组队","#FF00FF"));
        }else{
            return HTML.textb("错误","red");
        }
    }
}