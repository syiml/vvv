package action.App;

import action.BaseAction;
import util.JSON.AppJsonProblemList;

/**
 * Created by QAQ on 2016/9/21.
 */
public class ActionGetProblemList extends BaseAction{
    private int page;
    private int index;
    private String search;

    public String getProblemList(){
        out.print(new AppJsonProblemList(page, index, search).toJSON());
        return NONE;
    }

    public int getPage() {

        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
