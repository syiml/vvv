package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 2017/8/6.
 */
public class AiInfo implements IBeanResultSetCreate{
    private int id;//编号
    private String username;
    private int game_id;
    private String aiName;
    private String code;
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

    @Override
    public void init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        username = rs.getString("username");
        game_id = rs.getInt("game_id");
        aiName = rs.getString(rs.getInt("aiName"));
        code = rs.getString("code");
        introduce = rs.getString("introduce");
    }

    public int getId(){return this.id; }
    public int getGame_id(){return this.game_id; }
    public String getUsername(){return this.username; }
    public String getAiName(){return this.aiName; }
    public String getCode(){return this.code; }
    public String getIntroduce(){return this.introduce; }


}
