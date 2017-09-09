package dao;

import entity.AiInfo;
import util.JSON.JSON;
import util.SQL.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import net.sf.json.JSONObject;

import javax.xml.crypto.Data;

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

    public List<AiInfo> getAiListByUsername(String username){
        return new SQL("SELECT * FROM t_ai_info WHERE username= ? "+
                " ORDER BY ",username).queryBeanList(AiInfo.class);
    }

    public List<AiInfo> getAiListRank(int game_id,int from,int num){
        return new SQL("SELECT id,ai_name,username,game_id,introduce,"+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE (whiteId = t.id OR blackId = t.id) and win!=-1) AS num,"+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE win = t.id and win!=-1) AS win"+
                " FROM t_ai_info t "+
                " WHERE game_id = ? "+
                " ORDER BY win*win/num DESC LIMIT ?,?",game_id,from,num).queryBeanList(AiInfo.class);
    }
    public float getMaxScore(int game_id){
        return new SQL("SELECT MAX(win*win/num) FROM (SELECT "+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE (whiteId = t.id OR blackId = t.id) and win!=-1) AS num,"+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE win = t.id and win!=-1) AS win"+
                " FROM t_ai_info t "+
                " WHERE game_id = ? ) a",game_id).queryFloat();
    }
    public List<AiInfo> getAiListUser(String username,String aiName,int game_id,int from,int num){
        String sql = "SELECT id,ai_name,game_id,introduce,username"+
                " FROM t_ai_info"+
                " WHERE username = ? ";
        if (aiName != null && aiName.length()>0 ){ sql += " AND ai_name like '%" + aiName +"%'";}
        if (game_id > 0) {sql += " AND game_id = " + game_id;}
        sql += " ORDER BY id LIMIT ?,?";
        return new SQL(sql,username,from,num).queryBeanList(AiInfo.class);
    }

    public int getAiTotalNumOfRank(int game_id){
        return new SQL("SELECT COUNT(*) FROM t_ai_info WHERE game_id = ?",game_id).queryNum();
    }

    public int getAiTotalNumByUser(String username,String aiName,int game_id){
        String sql = "SELECT COUNT(*) FROM t_ai_info"+
                " WHERE username = ? ";
        if (aiName != null && aiName.length()>0 ){ sql += " AND ai_name = '" + aiName +"'";}
        if (game_id > 0) {sql += " AND game_id = " + game_id;}
        return new SQL(sql,username).queryNum();
    }

    public String addAiInfo(String username,int game_id,String aiName,String code,String introduce){
        if (new SQL("INSERT INTO t_ai_info VALUES(?,?,?,?,?,?)",0,username,game_id,aiName,code,introduce).update() !=0){
            return "success";
        }
        return "error";
    }

    public String updateAiInfo(int id,String aiName,String code,String introduce){
        if (new SQL("UPDATE t_ai_info SET ai_name = ?,ai_code = ?,introduce = ? WHERE id = ?",aiName,code,introduce,id).update() !=0){
            return "success";
        }
        return "error";
    }

    public String getAiInfoById(int id){
        AiInfo t = getByKeyFromSQL(id);
        if (t == null){
            return JSON.getJSONObject(
                    "ret","error"
            ).toString();
        }
        return JSON.getJSONObject(
                "ret","success",
                "id",id+"",
                "username",t.getUsername(),
                "aiName",t.getAiName(),
                "introduce",t.getIntroduce(),
                "win",getAiNumOfWin(id)+"",
                "total",getAiNumOfTotal(id)+""
        ).toString();
    }

    public  String getEditAiView(int id){
        AiInfo t = getByKeyFromSQL(id);
        if (t == null){
            return JSON.getJSONObject(
                    "ret","error"
            ).toString();
        }
        return JSON.getJSONObject(
                "ret","success",
                "id",id+"",
                "aiName",t.getAiName(),
                "code",t.getCode(),
                "introduce",t.getIntroduce()
        ).toString();
    }


    public int getAiNumOfWin(int id){//获取赢的局数
        return new SQL("SELECT COUNT(*) FROM t_game_repetition WHERE win = ?",id).queryNum();
    }

    public int getAiNumOfTotal(int id){//获取总的局数
        return new SQL("SELECT COUNT(*) FROM t_game_repetition WHERE whiteId = ? OR blackId = ?",id,id).queryNum();
    }

    public void addAiGameRepetition(String blackName,int blackAuthor,String whiteName,String whiteAuthor,String processes,String win){
        new SQL("INSERT INTO t_game_repetition VALUES(?,?,?,?,?,?,?)",0,blackName,blackAuthor,whiteName,whiteAuthor,processes,win).update();
    }



    @Override
    public AiInfo getByKeyFromSQL(Integer key) {
        SQL sql = new SQL("SELECT username,game_id,ai_name,ai_code,introduce " +
                "FROM t_ai_info WHERE id = ? ", key);
        try {
            ResultSet rs= sql.query();
            if (rs.next()){
                return new AiInfo(key,rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
            }
        }catch(SQLException e){
            e.getErrorCode();
        }finally {
            sql.close();
        }
        return null;
    }
}
