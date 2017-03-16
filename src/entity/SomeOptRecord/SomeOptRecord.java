package entity.SomeOptRecord;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * Created by QAQ on 2017/3/16.
 */
public class SomeOptRecord implements IBeanResultSetCreate {
    private String username;
    private ESomeOptRecordType type;
    private Timestamp time;
    private int id;
    private String data;

    @Override
    public void init(ResultSet rs) throws SQLException {
        username = rs.getString("username");
        type = ESomeOptRecordType.getByValue(rs.getInt("type"));
        time = rs.getTimestamp("time");
        data = rs.getString("data");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ESomeOptRecordType getType() {
        return type;
    }

    public void setType(ESomeOptRecordType type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
