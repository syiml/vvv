package servise;

import dao.VerifySQL;
import entity.User;
import entity.UserVerifyInfo;
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
    public static boolean editUser(User u) {
        return u != null && Main.users.update(u);
    }
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
        return MainResult.SUCCESS;
    }
    public static MainResult refuseVerify(int id,String reason){
        UserVerifyInfo userVerifyInfo = verifySQL.getUserVerifyInfo(id);
        if(userVerifyInfo==null) return MainResult.ARR_ERROR;
        if(userVerifyInfo.result != UserVerifyInfo.RESULT_PADDING) return MainResult.ARR_ERROR;
        verifySQL.updateResult(id,UserVerifyInfo.RESULT_REFUSE,reason);
        return MainResult.SUCCESS;
    }
}
