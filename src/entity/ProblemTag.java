package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Syiml on 2015/7/24 0024.
 */
public class ProblemTag implements IBeanResultSetCreate{
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    private int id;
    private String name;

    @Override
    public ProblemTag init(ResultSet rs) throws SQLException {
        id=rs.getInt("id");
        name=rs.getString("name");
        return this;
    }

    public ProblemTag(int id,String name){
        this.id=id;
        this.name=name;
    }
    public ProblemTag(){}

}
