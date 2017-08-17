package dao;

import entity.AiInfo;
import util.SQL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on 2017/8/6.
 */
public class AiSQL extends BaseCacheLRU<Integer,AiInfo> {

    private static AiSQL sql = new AiSQL();
    /**AiSQL对象
     * 饿汉式单例
     * @return
     */
    public static AiSQL getInstance(){
        return sql;
    }
    /**
     * 为private不允许直接new，必须通过getInstance()返回单例
     */
    private AiSQL() {
        super(10);
    }

    public List<AiInfo> getAiInfoList(String username){
        return new SQL("SELECT * FROM t_ai_info WHERE username= ?",username).queryBeanList(AiInfo.class);
    }

    public String addAiInfo(String username,int game_id,String aiName,String code,String introduce){
        if (new SQL("INSERT INTO t_ai_info VALUES(?,?,?,?,?,?)",0,username,game_id,aiName,code,introduce).update() !=0){
            return "success";
        }
        return "error";
    }

    public void addAiGameRepetition(String blackName,int blackAuthor,String whiteName,String whiteAuthor,String processes){
        new SQL("INSERT INTO t_game_repetition VALUES(?,?,?,?,?,?)",0,blackName,blackAuthor,whiteName,whiteAuthor,processes).update();
    }


    @Override
    public AiInfo getByKeyFromSQL(Integer key) {
        ResultSet rs= new SQL("SELECT username,game_id,ai_name,ai_code,introduce " +
                "FROM t_ai_info WHERE id = ? ", key).query();
        try {
            if (rs.next()){
                return new AiInfo(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
            }else{
                return null;
            }
        }catch(SQLException e){
            e.getErrorCode();
        }
        return null;
    }
}
