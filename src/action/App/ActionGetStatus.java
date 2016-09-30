package action.App;

import action.BaseAction;
import util.JSON.AppJsonGetStatus;

/**
 * Created by QAQ on 2016/9/30.
 */
public class ActionGetStatus extends BaseAction{
    private int page;

    public String getStatus(){
        out.print(new AppJsonGetStatus(page,20).toJSON().toString());
        return NONE;
    }

    public int getPage() {

        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
