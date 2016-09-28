package entity.Mall;

import entity.IBeanCanCach;
import entity.IBeanResultSetCreate;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/9/17.
 */
public class Goods implements IBeanResultSetCreate<Goods>,IBeanCanCach {
    private int id;
    private String title;
    private String cover;//封面
    private int acb;
    private int stock;
    private String des;//描述
    private boolean isHidden;
    private Timestamp t;//发布时间

    public Goods(){}

    @Override
    public Goods init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        title = rs.getString("title");
        cover = rs.getString("cover");
        acb = rs.getInt("acb");
        stock = rs.getInt("stock");
        des = rs.getString("des");
        isHidden = rs.getBoolean("isHidden");
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public int getAcb() {
        return acb;
    }

    public int getStock() {
        return stock;
    }

    public String getDes() {
        return des;
    }

    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public boolean isExpired() {
        return t.before(Tool.now());
    }
    @Override
    public void setExpired(Timestamp t) {
        this.t = t;
    }

}
