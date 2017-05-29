package action;

import entity.User;
import util.Main;

import java.util.List;

/**
 * Created by QAQ on 2017/5/28.
 */
public class titleConfig extends BaseAction {
    int type ;//1交换，2隐藏,3显示
    int id;
    int id2;

    public String go(){
        User u = Main.loginUser();
        if(u==null) return ERROR;
        if(type==1){
            u.titleSet.swap(id,id2);
        }else if(type==2){
            u.titleSet.hide(id);
        }else{
            u.titleSet.show(id);
        }
        Main.users.setTitleConfig(u.getUsername(),u.titleSet.isShow,u.titleSet.getOrder());
        return SUCCESS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }
}
