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

    public boolean addProblemStar(String username, int pid, String text){
        new SQL("REPLACE INTO t_star(username,`type`,star_id,text) VALUES(?,?,?,?)",username, UserStarType.PROBLEM.getValue(),pid,text).update();
        UserStarSet userStarSet = getBeanFromCatch(username);
        if(userStarSet!=null){
            userStarSet.setProblemStar(username,pid,text);
        }
        return true;
    }
    public boolean addStatusStar(String username, int rid, String text){
        new SQL("REPLACE INTO t_star(username,`type`,star_id,text) VALUES(?,?,?,?)",username, UserStarType.STATUS.getValue(),rid,text).update();
        UserStarSet userStarSet = getBeanFromCatch(username);
        if(userStarSet!=null){
            userStarSet.setStatusStar(username,rid,text);
        }
        return true;
    }
    public boolean removeStar(String username, int starID, UserStarType type){
        UserStarSet userStarSet = getBeanFromCatch(username);
        if(userStarSet!=null) {
            if(UserStarType.PROBLEM == type){
                userStarSet.removeStarProblem(starID);
            }
            else{
                userStarSet.removeStarStatus(starID);
            }
        }
        return 1==new SQL("DELETE FROM t_star WHERE username=? AND `type`=? AND star_id=?",username,type.getValue(),starID).update();

    }
}
