package action;

import entity.User;
import servise.UserService;
import util.Main;
import util.MainResult;

/**
 * 收藏相关action
 * Created by QAQ on 2017/1/27.
 */
public class Action_Star extends BaseAction {
    private int starID;
    private String text;

    public String starProblem(){
        if(text==null) text = "";
        MainResult mr = UserService.addProblemStar(starID,text);
        this.setPrompt(mr.getPrompt());
        return SUCCESS;
    }

    public String starStatus(){
        if(text==null) text = "";
        MainResult mr = UserService.addStatusStar(starID,text);
        this.setPrompt(mr.getPrompt());
        return SUCCESS;
    }
    public String cancelStarProblem(){
        MainResult mr = UserService.cancelStarProblem(starID);
        this.setPrompt(mr.getPrompt());
        return SUCCESS;
    }
    public String cancelStarStatus(){
        MainResult mr = UserService.cancelStarStatus(starID);
        this.setPrompt(mr.getPrompt());
        return SUCCESS;
    }

    public int getStarID() {
        return starID;
    }

    public void setStarID(int starID) {
        this.starID = starID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
