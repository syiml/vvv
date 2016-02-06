package action;

import servise.ContestMain;
import util.Main;
import util.Tool;
import util.rating.Computer;

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
            Computer c=new Computer(ContestMain.getContest(cidInt));
            if(ContestMain.getContest(cidInt).getEndTime().after(Tool.now())) return "error";//比赛没结束不能计算rating
            c.comp();
            c.save();
            return "success";
        }catch(NumberFormatException e){
            return "error";
        }
    }
}
