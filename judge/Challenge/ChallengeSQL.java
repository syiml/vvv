package Challenge;

import Tool.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 挑战模式dao层
 * 涉及表：
 * t_challenge_block
 * t_challenge_condition
 * t_challenge_problem
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeSQL {
    /**
     * 初始化，获取Block列表
     * @return Blcok列表，key是id，方便通过id查找Block
     */
    static Map<Integer, Block> init(){
        Map<Integer,Block> ret=new HashMap<Integer, Block>();
        ResultSet rsBlock = SQL.query("SELECT id,name,gro FROM t_challenge_block");
        ResultSet rsCondition = SQL.query("SELECT * FROM t_challenge_condition");
        ResultSet rsScore = SQL.query("SELECT id,sum(score) as score FROM t_challenge_problem GROUP BY id");
        try {
            while(rsBlock.next()){
                ret.put(rsBlock.getInt("id"),new Block(rsBlock.getInt("id"),rsBlock.getString("name"),rsBlock.getInt("gro")));
            }
            while(rsCondition.next()){
                int belongBlockId= rsCondition.getInt("belongBlockId");
                ret.get(belongBlockId).addCondition(new Condition(belongBlockId,rsCondition.getInt("type"),rsCondition.getInt("par"),rsCondition.getInt("num")));
            }
            while(rsScore.next()){
                int id = rsScore.getInt("id");
                ret.get(id).setSore(rsScore.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获得某user每个Block的得分
     * @param user 用户名
     * @return key是BlockId，value是得分
     */
    static Map<Integer,Integer> getUserScore(String user){
        Map<Integer,Integer> ret=new HashMap<Integer, Integer>();
        ResultSet rsUserScore=SQL.query("" +
                "SELECT id,sum(score) as score " +
                "FROM t_challenge_problem " +
                "LEFT JOIN usersolve_view " +
                "ON t_challenge_problem.tpid = usersolve_view.pid AND username = ? AND solved=1 " +
                "GROUP BY id",user);
        try {
            while(rsUserScore.next()){
                ret.put(rsUserScore.getInt("id"),rsUserScore.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
