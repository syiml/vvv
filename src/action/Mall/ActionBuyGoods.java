package action.Mall;

import action.BaseAction;
import servise.MallMain;
import util.HTML.MallHTML;
import util.MainResult;
import util.Tool;

/**
 * Created by QAQ on 2016/10/4.
 */
public class ActionBuyGoods extends BaseAction {
    private int id;

    public String buy(){
        if(isGet()) return ERROR;
        MainResult mr = MallMain.buy(id);
        Tool.debug("购买结果："+mr+" "+mr.getPrompt());
        switch(mr){
            case SUCCESS:return SUCCESS;
            case NO_LOGIN:setPrompt(mr.getPrompt());return LOGIN;
            default: setPrompt(mr.getPrompt()); return ERROR;
        }
    }

    public String cancelOrder(){
        MallMain.cancelOrder(id);
        return SUCCESS;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
