package servise;

import action.addcontest;
import dao.ContestSQL;
import entity.*;
import entity.rank.RankSQL;
import util.Main;
import util.Tool;

import java.util.List;

/**
 * Created by QAQ on 2016/2/6.
 */
public class ContestMain {

    private static ContestSQL contests = new ContestSQL();

    public static String addContest(addcontest a) {
        int cid = contests.getNewId();
        if (contests.addContest(cid, a).equals("error")) return "error";
        if (RankSQL.addRank(cid, a).equals("error")) return "error";
        return "success";
    }
    public static int addContestReturnResult(addcontest a) {
        int cid = contests.getNewId();
        if (contests.addContest(cid, a).equals("error")) return -1;
        if (RankSQL.addRank(cid, a).equals("error")) return -1;
        return cid;
    }

    public static String editContest(addcontest a) {
        int cid = Integer.parseInt(a.cid);
        if (contests.editContest(cid, a).equals("error")) return "error";
        if (RankSQL.editRank(cid, a).equals("error")) return "error";
        return "success";
    }

    public static String registerContest(int cid) {
        User u = Main.loginUser();
        if (u == null) return "login";
        int status = 0;
        Contest c = contests.getContest(cid);
        if (c.getRegisterendtime().before(Tool.now()) || c.getRegisterstarttime().after(Tool.now())) {
            return "error";
        }
        if (c.getType() == Contest_Type.REGISTER) {
            status = RegisterUser.STATUS_ACCEPTED;
        }
        if (c.getType() == Contest_Type.REGISTER2) {
            status = RegisterUser.STATUS_PADDING;
        }
        if (c.getKind() == 3 && !u.canRegisterOfficalContest()) {
            return "info";
        }
        return contests.addUserContest(cid, u.getUsername(), status);
    }

    public static String contestPorblemPublc(int cid) {
        Main.problems.setContestProblemVisiable(cid,1);
        return "success";
    }

    public static boolean canInContest(int cid) {
        Contest c = contests.getContest(cid);
        //User u=(User)getSession().getAttribute("user");
        User u = Main.loginUser();
        if (Main.loginUserPermission().getAddContest()) return true;
        //if (u == null) return false;
        int z = c.canin(u);
        if (z == 1) return true;
        if (z == -1) {//need password
            Object pass = Main.getSession().getAttribute("contestpass" + cid);
            if (pass != null && pass.toString().equals(contests.getContest(cid).getPassword())) {//密码正确
                return true;
            }
        }
        return false;
    }

    public static boolean canShowProblem(int cid) {
        if (!canInContest(cid)) return false;
        if (Main.loginUserPermission().getShowHideProblem()) return true;
        Contest c = contests.getContest(cid);
        return c.isBegin();
    }

    public static Contest getContest(int cid) {
        return contests.getContest(cid);
    }

    public static List<Contest> getContests(int from, int num, int statu, String name, int type, int kind) {
        return contests.getContests(from, num, statu, name, type, kind);
    }

    public static int getContestsNum(int statu, String name, int type, int kind) {
        return contests.getContestsNum(statu, name, type, kind);
    }

    public static String problemContest(int pid) {
        return contests.problemContest(pid);
    } 
    public static String setUserContest(int cid,String username,int statu,String info){
        return contests.setUserContest(cid, username, statu, info);
    }
    public static List<Contest> getRecentlyContests(int num){
        return contests.getRecentlyContests(num);
    }
    public static List<Integer> getAcRidFromCidPid(int cid,int pid){
        return contests.getAcRidFromCidPid(cid, pid);
    }
    public static List<RegisterTeam> getRegisterTeamByCid(int cid){
        return contests.getRegisterTeamByCid(cid);
    }
    public static RegisterUser getRegisterStatu(String username, int cid){
        return contests.getRegisterStatu(username, cid);
    }
    public static void addRegisterTeam(int cid,RegisterTeam rt){
        User user = Main.loginUser();
        if(!rt.getUsername().equals(user.getUsername())){
            if(!user.getPermission().getContestRegisterAdmin()){
                rt.setUsername(user.getUsername());
            }
        }
        contests.delTeamContest(cid,rt.getUsername());
        rt.setTime(Tool.now());
        contests.addRegisterTeam(cid,rt);
    }
    public static RegisterTeam getRegisterTeam(int cid,String username){
        return contests.getRegisterTeam(cid,username);
    }
    public static void updateRegisterTeamPassword(int cid,RegisterTeam rt){
        contests.updatePassword(cid,rt.getUsername(),rt.teamUserName,rt.teamPassword);
    }
    public static void updateRegisterTeamPassword(int cid,String username,String teamUsername,String pass){
        contests.updatePassword(cid,username,teamUsername,pass);
    }
    public static int getCidByName(String name){
        List<Integer> cids = contests.getCidByName(name);
        if(cids.size()==0) return -1;
        return cids.get(0);
    }
    public static String getMaxTeamUsername(int cid){
        return contests.getMaxTeamUsername(cid);
    }

    public static void ResetContestProblmes(int cid,String pids){
        contests.ResetContestProblmes(cid,pids);
    }
}