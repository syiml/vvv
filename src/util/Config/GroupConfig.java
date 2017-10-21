package util.Config;

import entity.UserGroup.GroupMemberStatus;
import entity.UserGroup.GroupType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by QAQ on 2017/10/22.
 */
public class GroupConfig extends BaseConfig {
    @Override
    public void readConfig(JSONObject jo) {
        JSONArray ja_role = jo.getJSONArray("role");
        GroupMemberStatus.list = new ArrayList<>();
        for(int i=0;i<ja_role.size();i++){
            GroupMemberStatus.list.add(new GroupMemberStatus(ja_role.getJSONObject(i)));
        }

        GroupType.list = new ArrayList<>();
        JSONArray ja_groupType = jo.getJSONArray("type");
        for(int i=0;i<ja_groupType.size();i++){
            GroupType.list.add(new GroupType(ja_groupType.getJSONObject(i)));
        }
    }

    @Override
    public String getFileName() {
        return "GroupConfig.json";
    }
}
