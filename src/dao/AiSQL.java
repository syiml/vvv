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
        return new SQL("SELECT id,ai_name,username,"+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE whiteId = t.id OR blackId = t.id) AS num,"+
                " (SELECT COUNT(*) FROM t_game_repetition WHERE win = t.id) AS win"+
                " FROM t_ai_info t "+
                " WHERE game_id = ? "+" "+
                " ORDER BY (win/num) DESC LIMIT ?,?",game_id,from,num).queryBeanList(AiInfo.class);
    }

    public int getTotalNumOfRank(int game_id){
        return new SQL("SELECT COUNT(*) FROM t_ai_info WHERE game_id = ?",game_id).queryNum();
    }

    public String addAiInfo(String username,int game_id,String aiName,String code,String introduce){
        if (new SQL("INSERT INTO t_ai_info VALUES(?,?,?,?,?,?)",0,username,game_id,aiName,code,introduce).update() !=0){
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

//    public String getAiRankList(int game_id){
//            ResultSet rs= new SQL("SELECT id,ai_name,username,"+
//                    " (SELECT COUNT(*) FROM t_game_repetition WHERE whiteId = t.id OR blackId = t.id) AS num,"+
//            " (SELECT COUNT(*) FROM t_game_repetition WHERE win = t.id) AS win"+
//            " FROM t_ai_info t "+
//            " WHERE game_id = ? "+" "+
//            " ORDER BY (win/num) DESC LIMIT 9",game_id).query();
//            int num = 0;
//            JSONObject date = new JSONObject();
//            try {
//            while (rs.next()){
//                ++num;
//                JSONObject jo = new JSONObject();
//                jo.put("id",rs.getInt(1));
//                jo.put("aiName",rs.getString(2));
//                jo.put("username",rs.getString(3));
//                jo.put("num",rs.getInt(4));
//                jo.put("win",rs.getInt(5));
//                date.put(Integer.toString(num),jo);
//            }
//        }catch(SQLException e){
//            e.getErrorCode();
//        }
//        date.put("num",num);
//        return date.toString();
//    }


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
