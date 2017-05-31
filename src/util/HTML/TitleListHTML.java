package util.HTML;

import entity.Title.BaseTitle;
import entity.User;
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
                int z = 1;
                if(showUser.titleSet.order.containsKey(title_id)) {
                    z = showUser.titleSet.order.get(title_id);
                }
                if(z==-1){
                    return HTML.a("titleConfig.action?id="+title_id+"&type=3", "显示");
                }else {
                    String ret = "";
                    if(i!=0) ret += HTML.a("titleConfig.action?type=1&id="+title_id+"&id2="+list.get(i-1), "上移");
                    if(list.get(i+1) != -1) ret += " "+HTML.a("titleConfig.action?type=1&id="+title_id+"&id2="+list.get(i+1), "下移");

                    return ret + " "+HTML.a("titleConfig.action?id="+title_id+"&type=2", "隐藏")+" "+
                            HTML.a("titleConfig.action?id="+title_id+"&type=4", "置顶");
                }
            }
            case "预览":{
                return "<img src='pic/Title/"+title_id+".png' style='height:30px'>";
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
        return "";
    }
}
