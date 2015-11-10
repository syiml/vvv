package Challenge;

import Tool.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        Map<Integer,Block> ret=new TreeMap<Integer, Block>();
        ResultSet rsBlock = SQL.query("SELECT id,name,gro FROM t_challenge_block order by id");
        ResultSet rsCondition = SQL.query("SELECT * FROM t_challenge_condition");
        ResultSet rsScore = SQL.query("SELECT id,sum(score) as score FROM t_challenge_problem GROUP BY id");
        try {
            while(rsBlock.next()){
                ret.put(rsBlock.getInt("id"),new Block(rsBlock.getInt("id"),rsBlock.getString("name"),rsBlock.getInt("gro")));
            }
            while(rsCondition.next()){
                int belongBlockId= rsCondition.getInt("belongBlockId");
                ret.get(belongBlockId).addCondition(new Condition(belongBlockId,rsCondition.getInt("type"),rsCondition.getInt("par"),rsCondition.getInt("num"),rsCondition.getInt("id")));
            }
            while(rsScore.next()){
                int id = rsScore.getInt("id");
                ret.get(id).setScore(rsScore.getInt("score"));
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
                "JOIN usersolve_view " +
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
    static Set<Integer> getOpenBlocks(String user){
        Set<Integer> ret=new HashSet<Integer>();
        ResultSet rs=SQL.query("Select block from t_challenge_openblock where username=?",user);
        try {
            while(rs.next()){
                ret.add(rs.getInt("block"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    static void addOpenBlock(String user,int block){
        SQL.update("insert into t_challenge_openblock values(?,?)",user,block);
    }
    static boolean isOpen(String user,int block){
        ResultSet rs=SQL.query("SELECT * from t_challenge_openblock where username=? and block=?",user,block);
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    static ResultSet getProblems(String user,int id){
        return  SQL.query("select solved+1 as solved,t_challenge_problem.pid,tpid,(select title from problem where pid=t_challenge_problem.tpid) as title,score " +
                "from t_challenge_problem left join usersolve_view on usersolve_view.pid=t_challenge_problem.tpid and username=? " +
                "where t_challenge_problem.id=?",user,id);
    }
    public static ResultSet getProblems(int id){
        return SQL.query("select pid,tpid,(select title from problem where pid=t_challenge_problem.tpid) as title,score " +
                "from t_challenge_problem " +
                "where id=?",id);
    }
    static int getUserScore(String user,int id){
        ResultSet rsUserScore=SQL.query("" +
                "SELECT id,sum(score) as score " +
                "FROM t_challenge_problem " +
                "JOIN usersolve_view " +
                "ON t_challenge_problem.tpid = usersolve_view.pid AND username = ? AND solved=1 " +
                "AND id=? " +
                "GROUP BY id",user,id);
        try {
            if(rsUserScore.next()){
                return rsUserScore.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    static String getText(int id){
        ResultSet rs=SQL.query("select text from t_challenge_block where id=?", id);
        try {
            if(rs.next()){
                return rs.getString("text");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String editBlockText(int id,String text){
        if(SQL.update("update t_challenge_block set text=? where id=? ",text,id)!=0){
            return "success";
        }else{
            return "error";
        }
    }
    public static String addCondition(int block,int type,int par,int num){
        if(SQL.update("insert into t_challenge_condition values(?,?,?,?,?)",block,type,par,num,null)==1){
            ChallengeMain.init();
            return "success";
        }
        return "error";
    }
    public static String delCondition(int id){
        if(SQL.update("delete from t_challenge_condition where id=?",id)==1){
            ChallengeMain.init();
            return "success";
        }
        return "error";
    }
    public static String addProblem(int pos,int bid,int pid,int score){
        SQL.update("update t_challenge_problem set pid=pid+1 where pid>=? and id=? ORDER BY pid DESC",pos,bid);
        SQL.update("insert into t_challenge_problem values(?,?,?,?)",bid,pos,pid,score);
        ChallengeMain.init();
        return "success";
    }
    public static String delProblem(int pos,int bid){
        SQL.update("delete from t_challenge_problem where id=? and pid=?",bid,pos);
        SQL.update("update t_challenge_problem set pid=pid-1 where pid>? and id=?",pos,bid);
        ChallengeMain.init();
        return "success";
    }
}
