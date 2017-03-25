package action;

import dao.ProblemSQL;
import entity.Enmu.AcbOrderType;
import entity.Problem;
import util.Main;
import entity.User;

/**
 * Created by Syiml on 2015/8/18 0018.
 */
public class buyviewcode extends BaseAction{
    public int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public static int getBuyDataCostACB(int pid){
        User u = Main.loginUser();
        if(u == null) return -1;//未登录不能买
        int cost = 1000;
        if(u.getInTeamStatus() == User.V_NONE) return -1;//没认证不能买
        else if(u.getInTeamStatus() == User.V_SCHOOL) cost = 1000;//校内人员
        else if(u.getInTeamStatus() == User.V_ASSOCIATION) cost = 900;//协会成员
        else if(u.getInTeamStatus() == User.V_RETIRED){//退役队员
            switch (u.getInTeamLv()){
                case 0:
                case 1:cost = 800;break;
                case 2:cost = 700;break;
                case 3:cost = 600;break;
                case 4:cost = 500;break;
                case 5:cost = 400;break;
                case 6:cost = 300;break;
                default: cost = -1;break;
            }
        }else if(u.getInTeamStatus() == User.V_TEAM){//现役队员
            switch (u.getInTeamLv()){
                case 0:cost = 800;break;
                case 1:cost = 700;break;
                case 2:cost = 600;break;
                case 3:cost = 500;break;
                case 4:cost = 400;break;
                case 5:cost = 300;break;
                case 6:cost = 200;break;
                default: cost = -1;break;
            }
        }
        if(Main.users.haveViewCode(u.getUsername(),pid)) cost -= 100;//贴过标签或者买过代码
        return cost;
    }
    public String buy(){
        User u=Main.loginUser();
        if(u==null) return ERROR;
        if(Main.users.subACB(u.getUsername(),100, AcbOrderType.BUY_CODE,"题目"+pid)==1){
            if(Main.users.addViewCode(u.getUsername(),pid)==1){
                return SUCCESS;
            }
        }
        return ERROR;
    }
    public String buyData(){
        User u=Main.loginUser();
        if(u==null) return ERROR;
        Problem p = Main.problems.getProblem(pid);
        if(!p.isLocal() && p.getOjid() != 7 ) return ERROR;
        if(p.visiable==0) return ERROR;
        int cost = getBuyDataCostACB(pid);
        if(cost == -1) return ERROR;
        if(Main.users.subACB(u.getUsername(), cost, AcbOrderType.BUY_DATA,"题目"+pid)==1){
            if(Main.users.addDownloadData(u.getUsername(),pid)==1){
                return SUCCESS;
            }
        }
        return SUCCESS;
    }
}
