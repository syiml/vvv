package servise;

import dao.UserSolvedSQL;
import dao.UserStarSQL;
import dao.VerifySQL;
import entity.Enmu.UserStarType;
import entity.Status;
import entity.User;
import entity.UserSolvedListBean;
import entity.UserVerifyInfo;
import util.Event.EventMain;
import util.Event.Events.EventVerify;
import util.Main;
import util.MainResult;
import util.Tool;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/12/11 0011.
 */
public class UserService {
    public static VerifySQL verifySQL = new VerifySQL();
    public static UserSolvedSQL userSolvedSQL = new UserSolvedSQL();
    public static boolean editUser(User u) {
        return u != null && Main.users.update(u);
    }
    public static UserStarSQL userStarSQL = new UserStarSQL();
    public static MainResult submitVerify(action.edit e){
        User u = Main.loginUser();
        if(u==null) return MainResult.NO_LOGIN;
        UserVerifyInfo userVerifyInfo = verifySQL.getUserVerifyInfo(u.getUsername());
        if(userVerifyInfo!=null) return MainResult.VERIFY_EXIST;
        userVerifyInfo = new UserVerifyInfo();
        userVerifyInfo.VerifyType = e.getVerifyType();
        userVerifyInfo.username = u.getUsername();
        userVerifyInfo.name = e.getName();
        userVerifyInfo.school = e.getSchool();
        userVerifyInfo.gender = e.getGender();
        userVerifyInfo.faculty = e.getFaculty_text();
        userVerifyInfo.major = e.getMajor_text();
        userVerifyInfo.cla = e.getCla();
        userVerifyInfo.no = e.getNo();
        userVerifyInfo.phone = e.getPhone();
        userVerifyInfo.email = e.getEmail();
        userVerifyInfo.result = UserVerifyInfo.RESULT_PADDING;
        userVerifyInfo.time = Tool.now();
        userVerifyInfo.graduationTime = Tool.getTimestamp(e.getGraduationTime()+"-07-01","00","00");
        int id = verifySQL.insert(userVerifyInfo);
        if(u.getInTeamStatus()==User.V_NONE && e.getVerifyType()==User.V_NONE){
            //未认证用户直接修改资料
            acceptedVerify(id);
        }
        try {
            if(e.getPic()!=null)
                Main.uploadFile(e.getPic(), Main.getRealPath("/")+Main.config.verifyPicPath + id +".jpg");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return MainResult.SUCCESS;
    }
    public static MainResult acceptedVerify(int id){
        UserVerifyInfo userVerifyInfo = verifySQL.getUserVerifyInfo(id);
        if(userVerifyInfo==null) return MainResult.ARR_ERROR;
        if(userVerifyInfo.result != UserVerifyInfo.RESULT_PADDING) return MainResult.ARR_ERROR;
        Main.users.updateByVerify(userVerifyInfo);
        verifySQL.updateResult(id, UserVerifyInfo.RESULT_ACCEPTED,"");
        MessageMain.addMessageVerify(verifySQL.getUserVerifyInfo(id));
        EventMain.triggerEvent(new EventVerify(Main.users.getUser(userVerifyInfo.username)));
        return MainResult.SUCCESS;
    }
    public static MainResult refuseVerify(int id,String reason){
        UserVerifyInfo userVerifyInfo = verifySQL.getUserVerifyInfo(id);
        if(userVerifyInfo==null) return MainResult.ARR_ERROR;
        if(userVerifyInfo.result != UserVerifyInfo.RESULT_PADDING) return MainResult.ARR_ERROR;
        verifySQL.updateResult(id,UserVerifyInfo.RESULT_REFUSE,reason);
        MessageMain.addMessageVerify(verifySQL.getUserVerifyInfo(id));
        return MainResult.SUCCESS;
    }
    public static MainResult resetPassword(String username){
        User u = Main.users.getUser(username);
        u.setPassword("123456");
        editUser(u);
        return MainResult.SUCCESS;
    }
    public static UserSolvedListBean getUserSolved(String username){
        return userSolvedSQL.getBeanByKey(username);
    }

    public static boolean isStarProblem(String username,int pid){
        return userStarSQL.getBeanByKey(username).isProblemStar(pid) != null;
    }
    public static boolean isStatusProblem(String username,int rid){
        return userStarSQL.getBeanByKey(username).isStatusStar(rid) != null;
    }

    public static MainResult addProblemStar(int pid,String text){
        User user = Main.loginUser();
        if(user==null) return MainResult.NO_LOGIN;
        if(Main.problems.getProblem(pid) == null) return MainResult.ARR_ERROR;
        userStarSQL.addProblemStar(user.getUsername(),pid,text);
        return MainResult.SUCCESS;
    }
    public static MainResult addStatusStar(int rid,String text){
        User user = Main.loginUser();
        if(user==null) return MainResult.NO_LOGIN;
        Status s = Main.status.getStatu(rid);
        if(s == null) return MainResult.ARR_ERROR;
        if(!Main.canViewCode(s,user)) return MainResult.NO_PERMISSION;
        userStarSQL.addStatusStar(user.getUsername(),rid,text);
        return MainResult.SUCCESS;
    }
    public static MainResult cancelStarProblem(int pid){
        User user = Main.loginUser();
        if(user == null) return MainResult.NO_LOGIN;
        if(Main.problems.getProblem(pid) == null) return MainResult.ARR_ERROR;
        userStarSQL.removeStar(user.getUsername(),pid, UserStarType.PROBLEM);
        return MainResult.SUCCESS;
    }
    public static MainResult cancelStarStatus(int rid){
        User user = Main.loginUser();
        if(user == null) return MainResult.NO_LOGIN;
        if(Main.status.getStatu(rid) == null) return MainResult.ARR_ERROR;
        userStarSQL.removeStar(user.getUsername(),rid, UserStarType.STATUS);
        return MainResult.SUCCESS;
    }

    public static void addTitle(User u , int id, int jd,Timestamp endTime){
        u.titleSet.addTitle(id,jd,endTime);
        Main.users.addTitle(u.getUsername(),id,jd,endTime);
    }
}
