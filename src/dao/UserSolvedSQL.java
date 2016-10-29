package dao;

import entity.UserSolvedListBean;
import util.SQL.SQL;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by QAQ on 2016/10/27.
 */
public class UserSolvedSQL extends BaseCacheLRU<String, UserSolvedListBean> {

    public UserSolvedSQL() {
        super(30);
    }

    @Override
    protected UserSolvedListBean getByKeyFromSQL(String key) {
        Map<Integer,Integer> map = new SQL("" +
            "SELECT id,sum(score) as score " +
            "FROM t_challenge_problem " +
            "JOIN t_usersolve " +
            "ON t_challenge_problem.tpid = t_usersolve.pid AND username = ? AND status=1 " +
            "GROUP BY id",key){
            protected Integer getObject(int i) throws SQLException {
                return rs.getInt(i);
            }
        }.queryMap();
        return new UserSolvedListBean(map);
    }
}
