package entity.UserGroup;

import net.sf.json.JSONObject;
import util.HTML.HTML;

import java.util.List;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupMemberStatus {

    int id;
    String name;
    boolean showB;
    String color;

    public GroupMemberStatus(JSONObject jo){
        this.id= jo.getInt("id");
        this.name = jo.getString("name");
        this.showB = jo.getBoolean("showB");
        this.color = jo.getString("color");
    }

    public int getId() {
        return id;
    }

    public static List<GroupMemberStatus> list;

    public static GroupMemberStatus getByID(int id){
        for(GroupMemberStatus t : list){
            if(id == t.id) return t;
        }
        return null;
    }

    public String HTML(){
        if(showB){
            return HTML.textb(name,color);
        }
        return HTML.text(name,color);
    }

    public String getName() {
        return name;
    }
}
