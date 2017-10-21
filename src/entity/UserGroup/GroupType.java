package entity.UserGroup;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupType {
    int id;
    String name;
    boolean canRepeatJoin;
    List<Integer> roles;
    List<String> showInfo;
    List<String> order;

    public GroupType(JSONObject jo){
        this.id = jo.getInt("id");
        this.name = jo.getString("name");
        this.canRepeatJoin = jo.getBoolean("canRepeatJoin");

        JSONArray ja_roles = jo.getJSONArray("roles");
        this.roles = new ArrayList<>();
        for(int i =0 ;i<ja_roles.size();i++) this.roles.add(ja_roles.getInt(i));

        JSONArray ja_showInfo = jo.getJSONArray("showInfo");
        this.showInfo = new ArrayList<>();
        for(int i =0 ;i<ja_showInfo.size();i++) this.showInfo.add(ja_showInfo.getString(i));

        JSONArray ja_order = jo.getJSONArray("order");
        this.order = new ArrayList<>();
        for(int i =0 ;i<ja_order.size();i++) this.order.add(ja_order.getString(i));
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<GroupType> list;

    public static GroupType getByID(int id){
        for(GroupType t : list){
            if(id == t.id) return t;
        }
        return null;
    }

    public boolean isCanRepeatJoin() {
        return canRepeatJoin;
    }

    public List<String> getOrder() {
        return order;
    }

    public List<String> getShowInfo() {
        return showInfo;
    }

    public List<Integer> getRoles() {
        return roles;
    }
}
