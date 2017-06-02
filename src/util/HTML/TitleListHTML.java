package util.HTML;

import entity.PermissionType;
import entity.Title.BaseTitle;
import entity.User;
import util.HTML.FromHTML.FormHTML;
import util.HTML.FromHTML.check.check;
import util.HTML.FromHTML.date.date;
import util.HTML.FromHTML.hidden.hidden;
import util.HTML.FromHTML.select.select;
import util.HTML.FromHTML.text.text;
import util.Main;
import util.SimplePageBean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by QAQ on 2017/5/27.
 */
public class TitleListHTML extends SimplePageBean<Integer> {

    private User showUser;
    private User loginUser;
    public TitleListHTML(String username) {
        super(1);
        showUser = Main.users.getUser(username);
        loginUser = Main.loginUser();
        if(showUser == null) showUser = loginUser;
    }

    @Override
    public List<Integer> getElement(int from, int num) {
        return showUser.titleSet.getShowTitles();
    }

    @Override
    public int getTotalNumber() {
        return 100;
    }

    @Override
    public String getTableClass() {
        return "table table-hover";
    }

    @Override
    public int getEveryPageNumber() {
        return 100;
    }

    @Override
    public String getCellByClass(int i, Integer title_id, String colname) {
        if(title_id == -1) return "";
        BaseTitle t = BaseTitle.getTitleByID(title_id);
        switch (colname){
            case "id":{
                if(showUser.titleSet.haveTitle(title_id)){
                    addClass(i+1,-1,"success");
                }
                return title_id+"";
            }
            case "名称": return t.getName();
            case "描述": return t.getDes();
            case "进度": {
                BaseTitle title = BaseTitle.getTitleByID(title_id);
                int total_jd = title.getTotal_jd();
                if(!showUser.titleSet.titles.containsKey(title_id)) return "0/"+total_jd;
                int jd = showUser.titleSet.titles.get(title_id).jd;
                return jd+"/"+total_jd;
            }
            case "过期时间":{
                if(!showUser.titleSet.titles.containsKey(title_id)) return "";
                Timestamp endTime = showUser.titleSet.titles.get(title_id).clear_time;
                int jd = showUser.titleSet.titles.get(title_id).jd;
                if(jd == 0) return "";
                if(endTime == null) return "永久";
                return endTime.toString();
            }
            case "排序":{
                if(showUser.titleSet.order.containsKey(title_id)){
                    int z =  showUser.titleSet.order.get(title_id);
                    if(z==-1) return "不展示";
                    else return z+"";
                }
                return i+1+"";
            }
            case "操作":{
                String _ret = "";
                if(showUser.getUsername().equals(loginUser.getUsername())) {
                    int z = 1;
                    if (showUser.titleSet.order.containsKey(title_id)) {
                        z = showUser.titleSet.order.get(title_id);
                    }
                    if (z == -1) {
                        _ret += HTML.a("titleConfig.action?id=" + title_id + "&type=3", "显示");
                    } else {
                        String ret = "";
                        if (i != 0)
                            ret += HTML.a("titleConfig.action?type=1&id=" + title_id + "&id2=" + list.get(i - 1), "上移");
                        if (list.get(i + 1) != -1)
                            ret += " " + HTML.a("titleConfig.action?type=1&id=" + title_id + "&id2=" + list.get(i + 1), "下移");

                        _ret += ret + " " + HTML.a("titleConfig.action?id=" + title_id + "&type=2", "隐藏") + " " +
                                HTML.a("titleConfig.action?id=" + title_id + "&type=4", "置顶");
                    }
                }
                if(loginUser.getPermission().havePermissions(PermissionType.titleAdmin)){
                    if(showUser.titleSet.haveTitle(title_id)){
                        _ret += " "+HTML.a("titleConfig.action?type=5&username="+showUser.getUsername()+"&id="+title_id,"删除");
                    }
                }
                return _ret;
            }
            case "预览":{
                return "<img src='TitlePic/"+title_id+".png' style='height:30px'>";
            }
        }
        return ERROR_CELL_TEXT;
    }

    @Override
    public String[] getColNames() {
        return new String[]{"id","预览","描述","进度","过期时间","排序","操作"};
    }

    @Override
    public String getTitle() {
        return showUser.getUsernameAndNickHTML()+" 的称号列表";
    }

    @Override
    public String getLinkByPage(int page) {
        return "";
    }

    @Override
    public String rightForm() {
        String res = "";
        if(loginUser.getUsername().equals(showUser.getUsername())) {
            FormHTML ff = new FormHTML();
            ff.setType(1);
            select adj_se = new select("adj", "昵称前的称号显示");
            adj_se.setId("adj");
            adj_se.add(-1, "无");
            for (BaseTitle title : BaseTitle.titles.values()) {
                if (title.getPart() == 0 && showUser.titleSet.haveTitle(title.getID())) {
                    adj_se.add(title.getID(), title.getName());
                }
            }
            adj_se.setValue(showUser.titleSet.adj + "");
            select n_se = new select("n", "的");
            adj_se.setType(1);
            n_se.setId("n");
            n_se.add(-1, "无");
            n_se.setType(1);
            for (BaseTitle title : BaseTitle.titles.values()) {
                if (title.getPart() == 1 && showUser.titleSet.haveTitle(title.getID())) {
                    n_se.add(title.getID(), title.getName());
                }
            }
            n_se.setValue(showUser.titleSet.n + "");
            ff.addForm(new hidden("type","7"));
            ff.setAction("titleConfig.action");
            ff.addForm(adj_se, n_se);
            ff.setType(1);
            ff.setSubmitText("确定");
            res += ff.toHTML();
        }

        return res;
    }

    @Override
    public String head() {
        if(loginUser.getPermission().havePermissions(PermissionType.titleAdmin)){
            FormHTML f = new FormHTML();
            f.setType(1);

            select f3=new select("id","称号");
            f3.setId("id");
            f3.setType(1);
            for(Integer title_id:BaseTitle.titles.keySet()){
                BaseTitle title = BaseTitle.getTitleByID(title_id);
                f3.add(title_id, title_id+" - "+title.getName(),"");
            }
            f.addForm(f3);

            text ki=new text("jd","进度");
            ki.setType(1);
            f.addForm(ki);

            date d = new date("time","过期时间");

            f.addForm(d);

            check c = new check("isForever","是否永久");
            f.addForm(c);

            f.setSubmitText("新增");
            f.addForm(new hidden("type","6"));
            f.addForm(new hidden("username",showUser.getUsername()));
            f.setAction("titleConfig.action");
            return "<div class='panel-body' style=\"padding:5px\">"+HTML.floatRight(f.toHTML())+"</div>" + super.head();
        }
        return super.head();
    }
}
