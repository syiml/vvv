package util.HTML;

import dao.GroupDao;
import entity.PermissionType;
import entity.User;
import entity.UserGroup.Group;
import entity.UserGroup.GroupType;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.select.select;
import util.Main;
import util.MyTime;
import util.SimplePageBean;
import util.Tool;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by QAQ on 2017/9/24.
 */
public class GroupListHTML extends SimplePageBean<Group> {

    static class GroupListSortedData{
        public boolean reset = false;
        List<Integer> allGroupIDList = new ArrayList<>();
        Timestamp lastSortTime = null;
    }
    private static Map<Integer,GroupListSortedData> dataMap = new HashMap<>();
    private static Comparator<Integer> cmp = new Comparator<Integer>(){
        @Override
        public int compare(Integer o1, Integer o2) {
            Group g1 = GroupDao.getInstance().getBeanByKey(o1);
            Group g2 = GroupDao.getInstance().getBeanByKey(o2);
            List<String> orders = g1.getType().getOrder();
            for (String order : orders) {
                switch (order) {
                    case "id":{
                        if(g1.getId() == g2.getId())continue;
                        return g1.getId() - g2.getId();
                    }
                    case "总AC":{
                        int l = g1.getMemberTotalAC(true);
                        int r = g2.getMemberTotalAC(true);
                        if(l == r) continue;
                        return r - l;
                    }
                    case "总Rating":{
                        int l = g1.getMemberTotalRating(true);
                        int r = g2.getMemberTotalRating(true);
                        if(l == r) continue;
                        return r - l;
                    }
                    case "队员总AC":{
                        int l = g1.getMemberTotalAC(false);
                        int r = g2.getMemberTotalAC(false);
                        if(l == r) continue;
                        return r - l;
                    }
                    case "队员总Rating":{
                        int l = g1.getMemberTotalRating(false);
                        int r = g2.getMemberTotalRating(false);
                        if(l == r) continue;
                        return r - l;
                    }
                }
            }
            return 0;
        }
    };

    private int type;
    public GroupListHTML(int page, int type) {
        super(page);
        this.type = type;
        sort(type);
    }

    public static synchronized void sort(int type){
        if(!dataMap.containsKey(type)) dataMap.put(type,new GroupListSortedData());
        GroupListSortedData data = dataMap.get(type);

        if(data.lastSortTime==null || data.reset || MyTime.addTimestamp(data.lastSortTime , 2*MyTime.MINUTE).before(Tool.now())){
            data.allGroupIDList = GroupDao.getInstance().getAllGroupID(type);
            if(type != -1)
                Collections.sort(data.allGroupIDList, cmp);
            //data.lastSortTime = Tool.now();
        }
    }
    @Override
    public List<Group> getElement(int from, int num) {
        List<Group> list = new ArrayList<>();
        List<Integer> idList = dataMap.get(type).allGroupIDList;
        for(int i=from;i<from+num && i<idList.size();i++){
            list.add(GroupDao.getInstance().getBeanByKey(idList.get(i)));
        }
        return list;
    }

    @Override
    public int getTotalNumber() {
        return dataMap.get(type).allGroupIDList.size();
    }

    @Override
    public int getEveryPageNumber() {
        return 50;
    }

    @Override
    public String getCellByClass(int i, Group cla, String colname) {
        switch (colname) {
            case "id":
                return cla.getId() + "";
            case "类型":
                return HTML.textb(cla.getType().getName(),"black");
            case "队名":
                return HTML.a("javascript:member("+cla.getId()+")",cla.getGroupName());
            case "所有者":
                return Main.users.getUser(cla.getLeader().getUsername()).getNick();
            case "总AC":
                return cla.getMemberTotalAC(true) + "";
            case "总Rating":
                return cla.getMemberTotalRating(true) + "";
            case "队员总AC":
                return cla.getMemberTotalAC(false) + "";
            case "队员总Rating":
                return cla.getMemberTotalRating(false) + "";
            case "admin":
                return HTML.a("admin.jsp?page=GroupAdmin&id="+cla.getId(),"编辑");
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String[] getColNames() {
        addTableHead("id");
        if(type == -1) addTableHead("类型");
        addTableHead("队名","所有者");
        GroupType groupType = GroupType.getByID(type);
        if(groupType != null){
            for (String colName : groupType.getShowInfo()){
                addTableHead(colName);
            }
        }
        User u = Main.loginUser();
        if(u!=null && u.getPermission().havePermissions(PermissionType.groupAdmin)) addTableHead("admin");
        return new String[0];
    }

    @Override
    public String getTitle() {
        User u = Main.loginUser();
        return "队伍排行榜"+((u!=null && u.getPermission().havePermissions(PermissionType.groupAdmin))?HTML.floatRight(HTML.a("admin.jsp?page=GroupAdmin","新增队伍")):"");
    }

    @Override
    public String getLinkByPage(int page) {
        return "GroupRank.jsp?type="+type+"&page="+page;
    }

    @Override
    public String rightForm() {
        FormHTML formHTML = new FormHTML();
        formHTML.setType(1);
        select s = new select("type","类型");
        s.setType(1);
        s.add(-1,"全部");
        for(GroupType type : GroupType.list){
            s.add(type.getId(),type.getName());
        }
        s.setValue(type+"");
        formHTML.addForm(s);
        formHTML.setAction("GroupRank.jsp");
        return formHTML.toHTML();
    }
}
