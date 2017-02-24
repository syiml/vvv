package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/2/21.
 */
public class Blog implements IBeanCanCatch,IBeanResultSetCreate{

    private int id;
    private String username;
    private int pid;
    private String title;
    private String text;
    private boolean isPublic;

    private Timestamp createTime;

    private Timestamp expired;
    @Override
    public Timestamp getExpired() {
        return expired;
    }
    @Override
    public void setExpired(Timestamp t) {
        expired = t;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {

    }
}
