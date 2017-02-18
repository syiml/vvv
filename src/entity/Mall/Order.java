package entity.Mall;

import entity.IBeanCanCatch;
import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/9/26.
 */
public class Order implements IBeanResultSetCreate<Order>, IBeanCanCatch {
    int id = -1;
    int goodsId;
    String username;
    int acb;
    Timestamp time;
    boolean isCancel;//是否被取消
    private Timestamp t;
    public Order(){}

    @Override
    public Order init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        username = rs.getString("username");
        goodsId = rs.getInt("goodsId");
        acb = rs.getInt("acb");
        time = rs.getTimestamp("time");
        isCancel = rs.getBoolean("isCancel");
        return this;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAcb() {
        return acb;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public int getId() {

        return id;
    }


    @Override
    public Timestamp getExpired() {
        return t;
    }

    @Override
    public void setExpired(Timestamp t) {
        this.t = t;
    }
}
