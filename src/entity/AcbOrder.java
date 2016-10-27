package entity;

import entity.Enmu.AcbOrderType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/10/26.
 */

public class AcbOrder implements IBeanResultSetCreate<AcbOrder> {
    public int id;
    public String username;
    public int change;
    public AcbOrderType reason;
    public String mark;
    public Timestamp time;
    @Override
    public AcbOrder init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        username = rs.getString("username");
        change = rs.getInt("acbchange");
        reason = AcbOrderType.getById(rs.getInt("reason"));
        mark = rs.getString("mark");
        time = rs.getTimestamp("time");
        return this;
    }
}
