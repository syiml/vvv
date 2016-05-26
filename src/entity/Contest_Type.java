package entity;

import util.HTML.HTML;

/**
 * Created by QAQ on 2016/5/9 0009.
 */
public enum Contest_Type{
    ERROR(-1),
    PUBLIC(0),
    PASSWORD(1),
    PRIVATE(2),
    REGISTER(3),
    REGISTER2(4),
    TEAM_OFFICIAL(5);

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