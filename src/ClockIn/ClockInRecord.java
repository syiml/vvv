package ClockIn;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Syiml on 2015/7/27 0027.
 */
public class ClockInRecord implements IBeanResultSetCreate{
    int id;
    public String username;
    public Timestamp time;
    public String sign;
    public String ip;
    public int todytimes;
    @Override
    public ClockInRecord init(ResultSet rs) throws SQLException {
        id=rs.getInt("id");
        username=rs.getString("username");
        time=rs.getTimestamp("time");
        sign=rs.getString("sign");
        ip=rs.getString("ip");
        todytimes=rs.getShort("todytimes");
        return this;
    }
    public ClockInRecord(){

    }
    public Long getDay(){
        return (time.getTime()+(1000*60*60*8))/(1000*60*60*24);
    }

}
