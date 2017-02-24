package action.App;

import action.BaseAction;
import util.JSON.AppJsonUserList;

/**
 * Created by QAQ on 2016/10/2.
 */
public class ActionUserList extends BaseAction {
    private String search;
    private String order = "rank";
    private boolean desc = false;
    private int page = 1;

    public String getUserList(){
        AppJsonUserList appJsonUserList = new AppJsonUserList(page,20);
        appJsonUserList.setDesc(desc);
        appJsonUserList.setOrder(order);
        appJsonUserList.setSearch(search);
        out.print(appJsonUserList.toJSON());
        return NONE;
    }

    public int getPage() {

        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {

        this.search = search;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc!=null;
    }
}
