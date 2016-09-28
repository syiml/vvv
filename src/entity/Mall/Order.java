package entity.Mall;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/9/26.
 */
public class Order implements IBeanResultSetCreate<Goods> {
    int goodsId;
    String username;
    int acb;
    Timestamp time;
    boolean isCancel;//是否被取消

    @Override
    public Goods init(ResultSet rs) throws SQLException {
        goodsId = rs.getInt("goodsId");
        username = rs.getString("username");
        acb = rs.getInt("acb");
        time = rs.getTimestamp("time");
        isCancel = rs.getBoolean("isCancel");
        return null;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public String getUsername() {
        return username;
    }

    public int getAcb() {
        return acb;
    }

    public Timestamp getTime() {
        return time;
    }

    public boolean isCancel() {
        return isCancel;
    }
}
