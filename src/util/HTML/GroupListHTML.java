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
            boolean isIncludeLeader = g1.getType() != GroupType.NEW_STU;
            int ac1 = g1.getMemberTotalAC(isIncludeLeader);
            int ac2 = g2.getMemberTotalAC(isIncludeLeader);
            if(ac1 != ac2){
                return ac2 - ac1;
            }
            return g2.getMemberTotalRating(isIncludeLeader) - g1.getMemberTotalRating(isIncludeLeader);
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
            case "队名":
                return HTML.a("javascript:member("+cla.getId()+")",cla.getGroupName());
            case "队长":
                return Main.users.getUser(cla.getLeader().getUsername()).getNick();
            case "总AC":
                return cla.getMemberTotalAC(type == GroupType.ICPC.getId()) + "";
            case "总Rating":
                return cla.getMemberTotalRating(type == GroupType.ICPC.getId()) + "";
            case "admin":
                return HTML.a("admin.jsp?page=GroupAdmin&id="+cla.getId(),"编辑");
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String[] getColNames() {
        addTableHead("id","队名","队长","总AC","总Rating");
        User u = Main.loginUser();
        if(u!=null && u.getPermission().havePermissions(PermissionType.groupAdmin)) addTableHead("admin");
        return new String[0];
    }

    @Override
    public String getTitle() {
        return "队伍排行榜"+HTML.floatRight(HTML.a("admin.jsp?page=GroupAdmin","新增队伍"));
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
        s.add(1,"集训队");
        s.add(2,"新生分组");
        s.setValue(type+"");
        formHTML.addForm(s);
        formHTML.setAction("GroupRank.jsp");
        return formHTML.toHTML();
    }
}
