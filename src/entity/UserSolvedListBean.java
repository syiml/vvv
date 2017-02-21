package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by QAQ on 2016/10/27.
 */
public class UserSolvedListBean implements IBeanResultSetCreate {

    public Map<Integer,Integer> solved;

    public UserSolvedListBean(Map<Integer,Integer> map){
        solved = map;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {

    }
}
