package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/2/21.
 */
public class Blog implements IBeanCanCatch,IBeanResultSetCreate{
    @Override
    public Timestamp getExpired() {
        return null;
    }

    @Override
    public void setExpired(Timestamp t) {

    }

    @Override
    public void init(ResultSet rs) throws SQLException {

    }
}
