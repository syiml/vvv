package entity.Mall;

import entity.IBeanCanCach;
import entity.IBeanResultSetCreate;
import entity.User;
import util.Tool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/9/17.
 */
public class Goods implements IBeanResultSetCreate<Goods>,IBeanCanCach {
    private int id;
    private String title;
    private int acb;
    private int stock;
    private String des;//描述
    private boolean isHidden;
    private Timestamp time;//发布时间
    private String user;//挂出商品的人
    private int buyLimit;//每人限购多少份（小于0表示没限制）
    private Timestamp t;//发布时间


    private int buyVerifyLimit;//限制指定认证级别购买
    
    public static final int Buy_Verify_Limit_Team=4;//现役队员购买
    public static final int Buy_Verify_Limit_Association=3;//退役，协会购买
    public static final int Buy_Verify_Limit_School=2;//在校学生购买
    public static final int Buy_Verify_Limit_All=1;//所有人购买
    public Goods(){}

    public String showBuyLimit(){
        switch (buyVerifyLimit){
            case Buy_Verify_Limit_Team: return "集训队员";
            case Buy_Verify_Limit_Association: return "退役队员   协会成员";
            case Buy_Verify_Limit_School: return "校内学生";
            case Buy_Verify_Limit_All: return "所有用户";
            default: return "ERROR";
        }
    }
    public int getBuyVerifyLimit() {
        return buyVerifyLimit;
    }
    public boolean checkUserCanBuy(User u){
        switch (buyVerifyLimit) {
            case Buy_Verify_Limit_Team:
                return u.getInTeamStatus()==User.V_TEAM;
            case Buy_Verify_Limit_Association:
                return u.getInTeamStatus()==User.V_TEAM||u.getInTeamStatus()==User.V_ASSOCIATION||u.getInTeamStatus()==User.V_RETIRED;
            case Buy_Verify_Limit_School:
                return u.getInTeamStatus()!=User.V_NONE;
            case Buy_Verify_Limit_All:
                return true;
            default: return false;
        }
    }
    @Override
    public Goods init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        title = rs.getString("title");
        acb = rs.getInt("acb");
        stock = rs.getInt("stock");
        des = rs.getString("des");
        isHidden = rs.getBoolean("isHidden");
        user = rs.getString("user");
        buyLimit = rs.getInt("buyLimit");
        buyVerifyLimit = rs.getInt("buyVerifyLimit");
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAcb() {
        return acb;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDes() {
        return des;

    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getBuyLimit() {

        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public void setBuyVerifyLimit(int buyVerifyLimit) {
        this.buyVerifyLimit = buyVerifyLimit;
    }

    @Override
    public boolean isExpired() {
        return t.before(Tool.now());
    }

    @Override
    public void setExpired(Timestamp t) {
        this.t = t;
    }

    public void setT(Timestamp t) {
        this.t = t;
    }
}
