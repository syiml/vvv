package util.HTML.problemListHTML;

import entity.IBeanResultSetCreate;
import entity.ICanToJSON;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/12 0012.
 */
public class problemView implements IBeanResultSetCreate, ICanToJSON {
    int pid;
    String title;
    int hide;
    int ac;
    int submit;

    public problemView(){}

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

    public void init(ResultSet r) throws SQLException {
        //pid,title,visiable,ac,submit
        pid=r.getInt(1);
        title=r.getString(2);
        hide=r.getInt(3);
        ac=r.getInt(4);
        submit=r.getInt(5);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pid",pid);
        jsonObject.put("title",title);
        jsonObject.put("hide",hide);
        jsonObject.put("ac",ac);
        jsonObject.put("submit",submit);
        return jsonObject;
    }
}
