package entity;

import entity.Enmu.UserStarType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by QAQ on 2017/1/25.
 */
public class UserStar implements IBeanResultSetCreate{
    private int id;
    private String username;
    private UserStarType type;
    private int start_id;
    private String text;

    @Override
    public Object init(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.username = rs.getString("username");
        this.type = UserStarType.getUserStartTypeByID(rs.getInt("type"));
        this.start_id = rs.getInt("star_id");
        this.text = rs.getString("text");
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStarType getType() {
        return type;
    }

    public void setType(UserStarType type) {
        this.type = type;
    }

    public int getStart_id() {
        return start_id;
    }

    public void setStart_id(int start_id) {
        this.start_id = start_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
