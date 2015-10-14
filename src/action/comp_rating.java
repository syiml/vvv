package action;

import Main.Main;
import Main.User.Permission;
import Main.User.User;
import Main.rating.Computer;

/**
 * Created by Syiml on 2015/7/3 0003.
 */
public class comp_rating {
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    String cid;
    public String save(){
        if(!Main.loginUserPermission().getComputrating()) return "error";
        int cidInt;
        try{
            cidInt=Integer.parseInt(cid);
            Computer c=new Computer(Main.contests.getContest(cidInt));
            if(Main.contests.getContest(cidInt).getEndTime().after(Main.now())) return "error";//比赛没结束不能计算rating
            c.comp();
            c.save();
            return "success";
        }catch(NumberFormatException e){
            return "error";
        }
    }
}
