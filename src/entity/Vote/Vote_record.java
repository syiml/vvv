package entity.Vote;

import entity.IBeanCanCatch;
import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/3/26.
 */
public class Vote_record implements IBeanResultSetCreate {
    public int did;
    public int id;
    public boolean isHide;
    public boolean isDisable;
    public String des;

    public int number;

    @Override
    public void init(ResultSet rs) throws SQLException {
        did = rs.getInt("did");
        id = rs.getInt("id");
        isHide = rs.getBoolean("isHide");
        isDisable = rs.getBoolean("isDisable");
        des = rs.getString("des");
        number = 0;
    }

}
