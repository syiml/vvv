package util.JSON;

import entity.User;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2016/10/7.
 */
public class AppJsonUserList  extends BaseJsonPageBean<User> {
    private String search;
    private String order = "rank";
    private boolean desc = false;
    public AppJsonUserList(int nowPage, int everyPageNum) {
        super(nowPage, everyPageNum);
    }

    @Override
    protected List<User> getList(int from, int num) {
        return Main.users.getUsers(from,num,search,order,desc);
    }

    @Override
    protected int getTotalNum() {
        return Main.users.getUsersNum(search);
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
