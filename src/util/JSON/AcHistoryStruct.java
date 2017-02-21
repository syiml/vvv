package util.JSON;

import entity.IBeanResultSetCreate;
import entity.ICanToJSON;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by syimlzhu on 2017/2/7.
 */
public class AcHistoryStruct implements IBeanResultSetCreate, ICanToJSON {
    public int pid;
    public long time;

    public AcHistoryStruct(){}

    @Override
    public JSONObject toJSON() {
        return JSON.getJSONObject("pid",pid+"","time",time+"");
    }

    @Override
    public void init(ResultSet rs) throws SQLException {
        pid = rs.getInt("pid");
        time=rs.getTimestamp("submitTime").getTime();
    }
}