package util.HTML.problemListHTML;

import entity.IBeanResultSetCreate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/12 0012.
 */
public class problemView implements IBeanResultSetCreate {
    int pid;
    String title;
    int hide;
    int ac;
    int submit;

    public int getPid() {
        return pid;
    }

    public String getTitle() {
        return title;
    }

    public int getHide() {
        return hide;
    }

    public int getAc() {
        return ac;
    }

    public int getSubmit() {
        return submit;
    }

    public problemView init(ResultSet r) throws SQLException {
        //pid,title,visiable,ac,submit
        pid=r.getInt(1);
        title=r.getString(2);
        hide=r.getInt(3);
        ac=r.getInt(4);
        submit=r.getInt(5);
        return this;
    }
    public problemView(){}
}
