package util.HTML;

import entity.TeamMemberAwardInfo;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/6/30 0030.
 */
public class TeamAwardList extends pageBean {

    List<TeamMemberAwardInfo> infos;
    int page = 1;
    int pageNum = 1;
    static int pageSize = 30;
    boolean admin=false;

    public TeamAwardList(int page){
        admin = Main.loginUserPermission().getTeamMemberAdmin();
        if(page>0) this.page = page;
        infos = Main.users.getTeamMemberAwardInfoList((page - 1) * pageSize, pageSize, admin);
        pageNum = getTotalPageNum(Main.users.getTeamMemberAwardInfoListNum(),pageSize);
        this.addTableHead("时间","队员1","队员2","队员3","级别","奖项","备注");
        if(admin) this.addTableHead("admin");
    }
    @Override
    public String getTitle() {
        return "校集训队荣誉榜"+(admin?HTML.floatRight(HTML.a("admin.jsp?page=TeamAward","新增")):"");
    }

    @Override
    public int getCurrPageSize() {
        return infos.size();
    }

    @Override
    public int getTotalPageNum() {
        return pageNum;
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        TeamMemberAwardInfo info = infos.get(i);
        if(colname.equals("时间")) {
            return info.getTime().toString();
        }else if(colname.equals("队员1")){
            return info.getUsername1().length()==0?info.getName1():HTML.a("UserInfo.jsp?user="+info.getUsername1(),info.getName1());
        }else if(colname.equals("队员2")){
            return info.getUsername2().length()==0?info.getName2():HTML.a("UserInfo.jsp?user="+info.getUsername2(),info.getName2());
        }else if(colname.equals("队员3")){
            return info.getUsername3().length()==0?info.getName3():HTML.a("UserInfo.jsp?user="+info.getUsername3(),info.getName3());
        }else if(colname.equals("级别")){
            return info.getContestLevel().toString();
        }else if(colname.equals("奖项")){
            return info.getAwardLevel().toString();
        }else if(colname.equals("备注")){
            return info.getText();
        }else if(colname.equals("admin")){
            return HTML.a("admin.jsp?page=TeamAward&id="+info.getId(),"修改")+HTML.a("delTeamAward.action?id="+info.getId(),"删除");
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        return "Awards.jsp?page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
