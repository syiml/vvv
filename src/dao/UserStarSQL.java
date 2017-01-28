package dao;


import entity.Enmu.UserStarType;
import entity.UserStar;
import entity.UserStarSet;
import util.SQL.SQL;


/**
 * Created by QAQ on 2017/1/25.
 */
public class UserStarSQL extends BaseCacheLRU<String, UserStarSet> {

    public UserStarSQL() {
        super(200);
    }

    @Override
    protected UserStarSet getByKeyFromSQL(String key) {
        UserStarSet userStarSet = new SQL("SELECT * FROM t_star WHERE username=?",key).queryBean(UserStarSet.class);
        if(userStarSet == null){
            userStarSet = new UserStarSet();
        }
        return userStarSet;
    }

    public boolean addProblemStart(String username,int pid,String text){
        new SQL("REPLACE INTO t_star(username,`type`,star_id,text) VALUES(?,?,?,?)",username, UserStarType.PROBLEM.getValue(),pid,text).update();
        UserStarSet userStarSet = getBeanFromCatch(username);
        if(userStarSet!=null){
            userStarSet.setProblemStar(username,pid,text);
        }
        return true;
    }
    public boolean addStatusStart(String username,int rid,String text){
        new SQL("REPLACE INTO t_star(username,`type`,star_id,text) VALUES(?,?,?,?)",username, UserStarType.STATUS.getValue(),rid,text).update();
        UserStarSet userStarSet = getBeanFromCatch(username);
        if(userStarSet!=null){
            userStarSet.setStatusStar(username,rid,text);
        }
        return true;
    }
}
