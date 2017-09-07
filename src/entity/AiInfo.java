package entity;

import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 2017/8/6.
 */
public class AiInfo implements IBeanResultSetCreate, ICanToJSON {
    private int id;//编号,数据库中自增加
    private String username; //作者名字
    private int game_id; //游戏类型
    private String aiName; //ai名字
    private String code; //代码
    private String introduce; //说明

    public AiInfo(String username,int game_id,String aiName,String code,String introduce){
        this.username = username;
        this.game_id = game_id;
        this.aiName = aiName;
        this.code = code;
        this.introduce = introduce;
    }

    public AiInfo(int id,String username,int game_id,String aiName,String code,String introduce){
        this.id = id;
        this.username = username;
        this.game_id = game_id;
        this.aiName = aiName;
        this.code = code;
        this.introduce = introduce;
    }

    public AiInfo(){}//避免InstantiationException

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        username = rs.getString("username");
        aiName = rs.getString("ai_name");
        game_id = rs.getInt("game_id");
//        code = rs.getString("ai_code");
        introduce = rs.getString("introduce");
    }

    public int getId(){return this.id; }
    public int getGame_id(){return this.game_id; }
    public String getUsername(){return this.username; }
    public String getAiName(){return this.aiName; }
    public String getCode(){return this.code; }
    public String getIntroduce(){return this.introduce; }


    @Override
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("username",username);
        jo.put("aiName",aiName);
        jo.put("game_id",game_id);
//        jo.put("code",code);
        jo.put("introduce",introduce);
        return jo;
    }
}
